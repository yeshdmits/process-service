package com.yeshenko.process.service;

import static com.yeshenko.process.config.WorkflowConfig.WORKFLOW_VARIABLE_FORM_DATA;
import static java.lang.String.valueOf;

import com.yeshenko.process.controller.mapper.ProcessMapper;
import com.yeshenko.process.domain.entity.Document;
import com.yeshenko.process.domain.entity.ProcessEntity;
import com.yeshenko.process.domain.enumeration.DocumentStatusEnum;
import com.yeshenko.process.domain.enumeration.ProcessStatusEnum;
import com.yeshenko.process.domain.enumeration.TaskStatusEnum;
import com.yeshenko.process.domain.repository.DocumentRepository;
import com.yeshenko.process.domain.repository.ProcessDefinitionRepository;
import com.yeshenko.process.domain.repository.ProcessEntityRepository;
import com.yeshenko.process.domain.repository.TaskEntityRepository;
import com.yeshenko.process.domain.util.MapUtil;
import com.yeshenko.process.domain.util.SpecificationUtil;
import com.yeshenko.process.models.v1.MetadataDto;
import com.yeshenko.process.models.v1.ProcessEntityDto;
import com.yeshenko.process.models.v1.TaskCompleteDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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
    return workflowService.startProcess(processDefinitionId);
  }

  public ProcessEntityDto getProcess(UUID processEntityId, UUID processDefinitionId) {
    var processEntity = processEntityRepository.findOne(
            SpecificationUtil.getSpecification(processEntityId, processDefinitionId))
        .orElseThrow();

    var activeTaskId = workflowService.getActiveTaskId(
        valueOf(processEntity.getProcessInstanceId()));

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
    var toReturn = new MetadataDto()
        .taskId(taskEntity.getId())
        .taskName(taskEntity.getTaskDefinition().getName());

    if (taskEntity.getTaskDefinition().getCustomTaskName() == null) {
//      log
      return toReturn
          .schema(taskEntity.getTaskDefinition().getSchema());
    }
    var documents = documentRepository.findAllByProcessEntityId(
            taskEntity.getProcessEntity().getId())
        .stream()
        .filter(i -> DocumentStatusEnum.CREATED.equals(i.getDocumentStatus()))
        .toList();
    var documentList = processMapper.toDto(documents);
//    log
    return toReturn
        .customComponentName(taskEntity.getTaskDefinition().getCustomTaskName())
        .componentProps(MapUtil.serializeObjectToString(documentList));
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
}
