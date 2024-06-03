package com.yeshenko.processserviceapi.service.process;

import static com.yeshenko.processserviceapi.config.WorkflowConfig.WORKFLOW_VARIABLE_FORM_DATA;
import static com.yeshenko.processserviceapi.domain.util.SpecificationUtil.*;
import static java.lang.String.format;
import static java.lang.String.valueOf;

import com.yeshenko.processserviceapi.domain.entity.TaskEntity;
import com.yeshenko.processserviceapi.domain.mapper.ProcessMapper;
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
import com.yeshenko.processserviceapi.exception.BadRequestException;
import com.yeshenko.processserviceapi.exception.NotFoundException;
import com.yeshenko.processserviceapi.exception.ForbiddenException;
import com.yeshenko.processserviceapi.models.v1.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.yeshenko.processserviceapi.service.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProcessEntityService {
    private static final Logger log = LoggerFactory.getLogger(ProcessEntityService.class);

    private final WorkflowService workflowService;
    private final ProcessEntityRepository processEntityRepository;
    private final ProcessDefinitionRepository processDefinitionRepository;
    private final TaskEntityRepository taskEntityRepository;
    private final DocumentRepository documentRepository;
    private final ProcessMapper processMapper;
    private final SecurityService securityService;

    public UUID createProcess(UUID processDefinitionId) {
        log.info("Starting a new process with process definition uuid: {}", processDefinitionId);

        UUID processInstanceId = workflowService.startProcess(processDefinitionId);
        return processEntityRepository.findOne(SpecificationUtil.getSpecification(null, processInstanceId))
                .orElseThrow(() -> new NotFoundException(format("Could not find process entity with process instance id: %s", processInstanceId)))
                .getId();
    }

    public ProcessEntityDto getProcess(UUID processEntityId, UUID processInstanceId) {
        var processEntity = processEntityRepository.findOne(
                        SpecificationUtil.getSpecification(processEntityId, processInstanceId))
                .orElseThrow(() -> new NotFoundException(format("Could not find process entity with id: %s", processEntityId)));
        var activeTaskId = workflowService.getActiveTaskId(
                valueOf(processEntity.getProcessInstanceId()));

        processEntity.setTasks(processEntity.getTasks().stream()
                .sorted((o1, o2) -> o2.getUpdatedAt().compareTo(o1.getUpdatedAt()))
                .toList());

        processEntity.setDocuments(processEntity.getDocuments().stream()
                .sorted((o1, o2) -> o2.getUpdatedAt().compareTo(o1.getUpdatedAt()))
                .toList());

        var toReturn = processMapper.toDto(processEntity);
        log.info("The process with uuid: {} was successfully found", processEntityId);

        if (activeTaskId != null) {
            log.info("Active task with id: {} was found, trying to enrich metadata", activeTaskId);
            toReturn.setMetadata(this.enrichTaskMetadata(activeTaskId));
        }
        return toReturn;
    }

    @Transactional
    public void completeTask(TaskCompleteDto request) {
        var taskEntity = taskEntityRepository.findById(request.getTaskId())
                .orElseThrow(() -> new NotFoundException(format("Could not find task entity with id: %s", request.getTaskId())));

        try {
            var taskStatusEnum = TaskStatusEnum.fromValue(request.getDecision().getValue());
            taskEntity.setTaskStatus(taskStatusEnum);
            log.info("Task Status was successfully updated, new value: {}", taskStatusEnum);
        } catch (IllegalArgumentException e) {
            log.info("Error while parsing request data, task status {} is not found", request.getDecision().getValue());
            throw new BadRequestException(format("Invalid task status: %s", request.getDecision().getValue()));
        }

        if (taskEntity.getTaskDefinition().getCustomTaskName() == null) {
            taskEntity.setFormData(request.getFormData());
            workflowService.setVariable(
                    valueOf(taskEntity.getProcessEntity().getProcessInstanceId()),
                    WORKFLOW_VARIABLE_FORM_DATA,
                    request.getFormData()
            );
            log.info("Task Payload was successfully updated, task id: {}", taskEntity.getId());
        }

        workflowService.completeTask(valueOf(taskEntity.getFlowableTaskId()));
    }

    public MetadataDto enrichTaskMetadata(UUID flowableTaskId) {
        var taskEntity = taskEntityRepository.findByFlowableTaskId(flowableTaskId)
                .orElseThrow(() -> new NotFoundException(format("Could not find task entity with flowable id: %s", flowableTaskId)));
        log.info("Task Entity with flowable id: {} was successfully found", flowableTaskId);
        if (noUserAccessToTask(taskEntity)) {
            return null;
        }
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
        log.info("Saving Process Entity with process instance id: {} for process definition id: {}", processInstanceId, processDefinitionId);
        return processEntityRepository.save(entityToSave).getId();
    }

    public void createDocument(UUID processInstanceId, byte[] content) {
        var processEntity = processEntityRepository.findOne(
                        SpecificationUtil.getSpecification(null, processInstanceId))
                .orElseThrow(() -> new NotFoundException(format("Could not find process entity with process instance id: %s", processInstanceId)));

        var toSave = Document.builder()
                .documentStatus(DocumentStatusEnum.CREATED)
                .content(content)
                .processEntity(processEntity)
                .name("Report.pdf")
                .build();

        log.info("Saving new document for process id: {}", processInstanceId);

        documentRepository.save(toSave);
    }

    public byte[] getDocumentBinaryData(UUID documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new NotFoundException(format("Could not find document with id: %s", documentId)));
        log.info("The Document with id: {} was successfully found", documentId);
        return document.getContent();
    }

    @Transactional
    public void updateProcessStatus(UUID processEntityId, ProcessStatusEnum status) {
        var processEntity = processEntityRepository.findOne(
                        SpecificationUtil.getSpecification(processEntityId, null))
                .orElseThrow(() -> new NotFoundException(format("Could not find process entity with id: %s", processEntityId)));
        log.info("The Process with id: {} was successfully found, updating status: {}", processEntityId, status);

        processEntity.setStatus(status);
    }

    @Transactional
    public void sendDocumentDistributionRequest(UUID processEntityId) {
        var processEntity = processEntityRepository.findOne(
                        SpecificationUtil.getSpecification(processEntityId, null))
                .orElseThrow(() -> new NotFoundException(format("Could not find process entity with id: %s", processEntityId)));

        processEntity.getDocuments()
                .stream().filter(i -> DocumentStatusEnum.CREATED.equals(i.getDocumentStatus()))
                .forEach(i -> i.setDocumentStatus(DocumentStatusEnum.SENT));
        log.info("The Documents for process with id: {} was successfully sent", processEntityId);
    }

    @Transactional
    public void validateDocuments(UUID processEntityId) {
        var processEntity = processEntityRepository.findOne(
                        SpecificationUtil.getSpecification(processEntityId, null))
                .orElseThrow(() -> new NotFoundException(format("Could not find process entity with id: %s", processEntityId)));

        processEntity.getDocuments().stream()
                .filter(i -> DocumentStatusEnum.SENT.equals(i.getDocumentStatus()))
                .forEach(i -> i.setDocumentStatus(DocumentStatusEnum.COMPLETED));
        log.info("The Documents for process with id: {} was successfully validated", processEntityId);
    }

    @Transactional
    public void rejectDocuments(UUID processEntityId) {
        var processEntity = processEntityRepository.findOne(
                        SpecificationUtil.getSpecification(processEntityId, null))
                .orElseThrow(() -> new NotFoundException(format("Could not find process entity with id: %s", processEntityId)));

        processEntity.getDocuments().stream()
                .filter(i -> DocumentStatusEnum.SENT.equals(i.getDocumentStatus()))
                .forEach(i -> i.setDocumentStatus(DocumentStatusEnum.WITHDRAWN));
        log.info("The Documents for process with id: {} was rejected", processEntityId);
    }

    public List<ProcessEntityListResponseInnerDto> fetchProcessEntityList() {
        var processEntityList = processEntityRepository.findAll().stream()
                .sorted((o1, o2) -> o2.getUpdatedAt().compareTo(o1.getUpdatedAt()))
                .toList();
        log.info("The Process List was successfully fetched, size: {}", processEntityList.size());
        return processMapper.toDtoList(processEntityList);
    }

    public List<ProcessDefinitionResponseInnerDto> getProcessDefinitionList() {
        var processDefinitionList = processDefinitionRepository.findAll();
        log.info("The Process Definition List was successfully fetched, size: {}", processDefinitionList.size());

        return processMapper.toDtoDefinitionList(processDefinitionList);
    }

    public TaskDto getTask(UUID taskId) {
        var taskEntity = taskEntityRepository.findById(taskId).orElseThrow(() -> new NotFoundException("No task was found"));
        if (noUserAccessToTask(taskEntity) && TaskStatusEnum.IN_PROGRESS.equals(taskEntity.getTaskStatus())) {
            throw new ForbiddenException(format("User has not access to task with id: %s", taskId));
        }
        var toReturn = processMapper.toDto(taskEntity);
        if (taskEntity.getTaskDefinition().getCustomTaskName() == null) {
            log.info("The Json Based task was detected");
            return toReturn;
        }

        log.info("The Custom Component Based task was detected");
        var documents = documentRepository.findAllByProcessEntityId(
                taskEntity.getProcessEntity().getId());
        var documentList = processMapper.toDto(documents);
        log.info("The documents for Custom Component were found, size: {}", documents.size());
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
            for (var filter : filterDto.getFilters()) {
                spec.and(addCriteria(filter.getField(), filter.getValue(), filter.getCriteria().getValue()));
            }
        }

        Page<ProcessEntity> entityPage = processEntityRepository.findAll(spec, pageable);

        pageableDto.setTotalItems((int) entityPage.getTotalElements());
        pageableDto.setTotalPages(entityPage.getTotalPages());
        pageableDto.setPage(entityPage.getNumber());
        pageableDto.setSize(entityPage.getNumberOfElements());

        sorting.get().findFirst().ifPresent(i -> pageableDto.setSortBy(i.getProperty()));
        sorting.get().findFirst().ifPresent(i -> pageableDto.setOrder(i.getDirection().name().toLowerCase()));

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

        pageableDto.setData(processMapper.toDtoList(entityPage.getContent()));
        return pageableDto;
    }


    public List<TaskDto> fetchTasksByUserRole() {
        List<String> customRole = securityService.getCustomRole();
        List<TaskEntity> tasks = taskEntityRepository.findAllByTaskDefinition_assignRoleIn(customRole);
        log.info("Successfully fetched tasks for given user role: {}, size: {}", customRole, tasks.size());
        if (CollectionUtils.isEmpty(tasks)) {
            throw new NotFoundException("User has no assigned tasks");
        }

        return processMapper.toDtoTaskList(tasks);
    }


    private boolean noUserAccessToTask(TaskEntity task) {
        return !securityService.getCustomRole().contains(task.getTaskDefinition().getAssignRole());
    }
}
