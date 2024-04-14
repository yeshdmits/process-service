package com.yeshenko.process.service;

import static java.lang.String.valueOf;

import com.yeshenko.process.controller.mapper.ProcessMapper;
import com.yeshenko.process.domain.entity.ProcessEntity;
import com.yeshenko.process.domain.enumeration.TaskStatusEnum;
import com.yeshenko.process.domain.repository.ProcessDefinitionRepository;
import com.yeshenko.process.domain.repository.ProcessEntityRepository;
import com.yeshenko.process.domain.repository.TaskEntityRepository;
import com.yeshenko.process.domain.util.SpecificationUtil;
import com.yeshenko.process.models.v1.MetadataDto;
import com.yeshenko.process.models.v1.ProcessEntityDto;
import com.yeshenko.process.models.v1.TaskCompleteDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessEntityService {

  private final WorkflowService workflowService;
  private final ProcessEntityRepository processEntityRepository;
  private final ProcessDefinitionRepository processDefinitionRepository;
  private final TaskEntityRepository taskEntityRepository;
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

    toReturn.setMetadata(this.enrichTaskMetadata(activeTaskId));
//    log

    return toReturn;
  }

  public void completeTask(TaskCompleteDto request) {
    var taskEntity = taskEntityRepository.findById(request.getTaskId())
        .orElseThrow();

    taskEntity.setTaskStatus(TaskStatusEnum.fromValue(request.getDecision().getValue()));
    taskEntity.setFormData(request.getFormData());
//    log
    workflowService.completeTask(valueOf(taskEntity.getFlowableTaskId()));
  }

  public MetadataDto enrichTaskMetadata(UUID flowableTaskId) {
    var taskEntity = taskEntityRepository.findByFlowableTaskId(flowableTaskId)
        .orElseThrow();
//    log

    return new MetadataDto()
        .taskId(taskEntity.getId())
        .schema(taskEntity.getTaskDefinition().getSchema())
        .taskName(taskEntity.getTaskDefinition().getName());
  }

  public UUID callSaveEntityFromWorkflow(UUID processInstanceId, UUID processDefinitionId) {
    var entityToSave = ProcessEntity.builder()
        .processInstanceId(processInstanceId)
        .processDefinition(processDefinitionRepository.findById(processDefinitionId).orElseThrow())
        .build();

    return processEntityRepository.save(entityToSave).getId();
  }

}
