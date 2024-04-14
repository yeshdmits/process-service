package com.yeshenko.process.service;

import static com.yeshenko.process.config.WorkflowConfig.WORKFLOW_VARIABLE_DECISION;
import static com.yeshenko.process.config.WorkflowConfig.WORKFLOW_VARIABLE_PROCESS_ID;

import com.yeshenko.process.domain.entity.TaskEntity;
import com.yeshenko.process.domain.enumeration.TaskStatusEnum;
import com.yeshenko.process.domain.repository.ProcessEntityRepository;
import com.yeshenko.process.domain.repository.TaskDefinitionRepository;
import com.yeshenko.process.domain.repository.TaskEntityRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskEntityService {

  private final WorkflowService workflowService;

  private final TaskDefinitionRepository taskDefinitionRepository;
  private final TaskEntityRepository taskEntityRepository;
  private final ProcessEntityRepository processEntityRepository;

  @Transactional
  public void createTask(Task task) {
    String taskDefinitionKey = workflowService.getTaskFormProperty(task.getId()).getValue();

    var taskDefinition = taskDefinitionRepository.findByDefinitionKey(taskDefinitionKey)
        .orElseThrow();

    var processEntityId = (UUID) workflowService.getVariable(task.getProcessInstanceId(),
        WORKFLOW_VARIABLE_PROCESS_ID);

    var processEntity = processEntityRepository.findById(processEntityId)
        .orElseThrow();

    var entityToSave = TaskEntity.builder()
        .taskStatus(TaskStatusEnum.IN_PROGRESS)
        .taskDefinition(taskDefinition)
        .processEntity(processEntity)
        .formData("{}")
        .flowableTaskId(UUID.fromString(task.getId()))
        .build();

    taskEntityRepository.save(entityToSave);
  }

  @Transactional
  public void completeTask(Task task) {
    var taskEntity = taskEntityRepository.findByFlowableTaskId(UUID.fromString(task.getId()))
        .orElseThrow();

    workflowService.setVariable(task.getProcessInstanceId(), WORKFLOW_VARIABLE_DECISION,
        taskEntity.getTaskStatus().getValue().toLowerCase());

  }
}
