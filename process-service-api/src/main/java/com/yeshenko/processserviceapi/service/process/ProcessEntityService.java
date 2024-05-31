package com.yeshenko.processserviceapi.service.process;

import static com.yeshenko.processserviceapi.config.WorkflowConfig.WORKFLOW_VARIABLE_FORM_DATA;
import static com.yeshenko.processserviceapi.domain.util.SpecificationUtil.*;
import static java.lang.String.valueOf;

import com.yeshenko.processserviceapi.controller.mapper.ProcessMapper;
import com.yeshenko.processserviceapi.domain.entity.Document;
import com.yeshenko.processserviceapi.domain.entity.ProcessEntity;
import com.yeshenko.processserviceapi.domain.enumeration.DocumentStatusEnum;
import com.yeshenko.processserviceapi.domain.enumeration.ProcessStatusEnum;
import com.yeshenko.processserviceapi.domain.enumeration.TaskStatusEnum;
import com.yeshenko.processserviceapi.domain.repository.DocumentRepository;
import com.yeshenko.processserviceapi.domain.repository.ProcessDefinitionRepository;
import com.yeshenko.processserviceapi.domain.repository.custom.ProcessEntityRepository;
import com.yeshenko.processserviceapi.domain.repository.TaskEntityRepository;
import com.yeshenko.processserviceapi.domain.util.MapUtil;
import com.yeshenko.processserviceapi.domain.util.SpecificationUtil;
import com.yeshenko.processserviceapi.models.v1.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProcessEntityService {

    private final WorkflowService workflowService;
    private final ProcessEntityRepository processEntityRepository;
    private final ProcessDefinitionRepository processDefinitionRepository;
    private final TaskEntityRepository taskEntityRepository;
    private final DocumentRepository documentRepository;
    private final ProcessMapper processMapper;

    public UUID createProcess(UUID processDefinitionId) {
//    log
        UUID processInstanceId = workflowService.startProcess(processDefinitionId);
        return processEntityRepository.findOne(SpecificationUtil.getSpecification(null, processInstanceId)).orElseThrow().getId();
    }

    public ProcessEntityDto getProcess(UUID processEntityId, UUID processInstanceId) {
        var processEntity = processEntityRepository.findOne(
                        SpecificationUtil.getSpecification(processEntityId, processInstanceId))
                .orElseThrow();

        var activeTaskId = workflowService.getActiveTaskId(
                valueOf(processEntity.getProcessInstanceId()));

        processEntity.setTasks(processEntity.getTasks().stream()
                .sorted((o1, o2) -> o2.getUpdatedAt().compareTo(o1.getUpdatedAt()))
                .toList());

        processEntity.setDocuments(processEntity.getDocuments().stream()
                .sorted((o1, o2) -> o2.getUpdatedAt().compareTo(o1.getUpdatedAt()))
                .toList());

        var toReturn = processMapper.toDto(processEntity);
//    log

        if (activeTaskId != null) {
            toReturn.setMetadata(this.enrichTaskMetadata(activeTaskId));
        }
//    log

        return toReturn;
    }

    @Transactional
    public void completeTask(TaskCompleteDto request) {
        var taskEntity = taskEntityRepository.findById(request.getTaskId())
                .orElseThrow();

        taskEntity.setTaskStatus(TaskStatusEnum.fromValue(request.getDecision().getValue()));
//    log

        if (taskEntity.getTaskDefinition().getCustomTaskName() == null) {
            taskEntity.setFormData(request.getFormData());
            workflowService.setVariable(
                    valueOf(taskEntity.getProcessEntity().getProcessInstanceId()),
                    WORKFLOW_VARIABLE_FORM_DATA,
                    request.getFormData()
            );
//    log
        }

        workflowService.completeTask(valueOf(taskEntity.getFlowableTaskId()));
    }

    public MetadataDto enrichTaskMetadata(UUID flowableTaskId) {
        var taskEntity = taskEntityRepository.findByFlowableTaskId(flowableTaskId)
                .orElseThrow();
//    log
        return new MetadataDto()
                .taskId(taskEntity.getId())
                .taskName(taskEntity.getTaskDefinition().getName());
    }

    public UUID callSaveEntityFromWorkflow(UUID processInstanceId, UUID processDefinitionId) {
        var entityToSave = ProcessEntity.builder()
                .processInstanceId(processInstanceId)
                .processDefinition(processDefinitionRepository.findById(processDefinitionId).orElseThrow())
                .status(ProcessStatusEnum.CREATED)
                .build();
//    log

        return processEntityRepository.save(entityToSave).getId();
    }

    public void createDocument(UUID processInstanceId, byte[] content) {
        var processEntity = processEntityRepository.findOne(
                        SpecificationUtil.getSpecification(null, processInstanceId))
                .orElseThrow();

        var toSave = Document.builder()
                .documentStatus(DocumentStatusEnum.CREATED)
                .content(content)
                .processEntity(processEntity)
                .name("Report.pdf")
                .build();
//    log

        documentRepository.save(toSave);
    }

    public byte[] getDocumentBinaryData(UUID documentId) {
        Document document = documentRepository.findById(documentId).orElseThrow();
//    log
        return document.getContent();
    }

    @Transactional
    public void updateProcessStatus(UUID processEntityId, ProcessStatusEnum status) {
        var processEntity = processEntityRepository.findOne(
                        SpecificationUtil.getSpecification(processEntityId, null))
                .orElseThrow();
//    log

        processEntity.setStatus(status);
    }

    @Transactional
    public void sendDocumentDistributionRequest(UUID processEntityId) {
        var processEntity = processEntityRepository.findOne(
                        SpecificationUtil.getSpecification(processEntityId, null))
                .orElseThrow();

        processEntity.getDocuments()
                .stream().filter(i -> DocumentStatusEnum.CREATED.equals(i.getDocumentStatus()))
                .forEach(i -> i.setDocumentStatus(DocumentStatusEnum.SENT));
    }

    @Transactional
    public void validateDocuments(UUID processEntityId) {
        var processEntity = processEntityRepository.findOne(
                        SpecificationUtil.getSpecification(processEntityId, null))
                .orElseThrow();

        processEntity.getDocuments().stream()
                .filter(i -> DocumentStatusEnum.SENT.equals(i.getDocumentStatus()))
                .forEach(i -> i.setDocumentStatus(DocumentStatusEnum.COMPLETED));
    }

    @Transactional
    public void rejectDocuments(UUID processEntityId) {
        var processEntity = processEntityRepository.findOne(
                        SpecificationUtil.getSpecification(processEntityId, null))
                .orElseThrow();

        processEntity.getDocuments().stream()
                .filter(i -> DocumentStatusEnum.SENT.equals(i.getDocumentStatus()))
                .forEach(i -> i.setDocumentStatus(DocumentStatusEnum.WITHDRAWN));
    }

    public List<ProcessEntityListResponseInnerDto> fetchProcessEntityList() {
        var processEntityList = processEntityRepository.findAll().stream()
                .sorted((o1, o2) -> o2.getUpdatedAt().compareTo(o1.getUpdatedAt()))
                .toList();
        return processMapper.toDtoList(processEntityList);
    }

    public List<ProcessDefinitionResponseInnerDto> getProcessDefinitionList() {
        var processDefinitionList = processDefinitionRepository.findAll();

        return processMapper.toDtoDefinitionList(processDefinitionList);
    }

    public TaskDto getTask(UUID taskId) {
        var taskEntity = taskEntityRepository.findById(taskId).orElseThrow();
        var toReturn = processMapper.toDto(taskEntity);
        if (taskEntity.getTaskDefinition().getCustomTaskName() == null) {
//      log
            return toReturn;
        }

        var documents = documentRepository.findAllByProcessEntityId(
                        taskEntity.getProcessEntity().getId());
        var documentList = processMapper.toDto(documents);
//    log
        return toReturn
                .componentProps(MapUtil.serializeObjectToString(documentList));
    }

    public PageableDto getFilteredProcesses(ProcessFilterDto filterDto) {
        PageableDto pageableDto = new PageableDto();

        var sorting = Optional.ofNullable(filterDto.getPage().getSortBy())
                .map(sortField -> getSort(sortField, filterDto.getPage().getOrder().getValue()))
                .orElse(getDefaultSort());
        Pageable pageable = PageRequest.of(filterDto.getPage().getPage(), filterDto.getPage().getSize(), sorting);
        Specification<ProcessEntity> spec = Specification.where((root, query, criteriaBuilder) ->
                criteriaBuilder.conjunction());
        if (filterDto.getFilters() != null) {
            for (var filter: filterDto.getFilters()) {
                spec.and(addCriteria(filter.getField(), filter.getValue(), filter.getCriteria().getValue()));
            }
        }

        Page<ProcessEntity> entityPage = processEntityRepository.findAll(spec, pageable);

        pageableDto.setTotalItems((int) entityPage.getTotalElements());
        pageableDto.setTotalPages(entityPage.getTotalPages());
        pageableDto.setPage(entityPage.getNumber());
        pageableDto.setSize(entityPage.getNumberOfElements());

        sorting.get().findFirst().ifPresent(i -> pageableDto.setSortBy(i.getProperty()));
        sorting.get().findFirst().ifPresent(i -> pageableDto.setOrder(i.getDirection().name()));

        processEntityRepository.findColumns(spec).forEach((column, values) -> {
            var grid = new GridColumnDto();
            grid.setColumnName(column);
            var criteriaIn = new GridColumnCriteriaListInnerDto();
            criteriaIn.setCriteriaType(GridColumnCriteriaListInnerDto.CriteriaTypeEnum.EQ);
            criteriaIn.getPossibleValues().addAll(
                    values.stream()
                            .filter(v -> !"null".equals(v))
                            .map(v -> new InColumnFilterAllOfPossibleValuesDto(String.valueOf(v), String.valueOf(v)))
                            .collect(Collectors.toSet()));
            grid.addCriteriaListItem(criteriaIn);
            pageableDto.addGridColumnsItem(grid);
        });

        entityPage.getContent().forEach(i -> pageableDto.addDataItem(processMapper.toDto(i)));

        return pageableDto;
    }

}
