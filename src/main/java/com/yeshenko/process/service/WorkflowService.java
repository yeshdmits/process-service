package com.yeshenko.process.service;

import static com.yeshenko.process.config.WorkflowConfig.WORKFLOW_TASK_DEFINITION_KEY;
import static com.yeshenko.process.config.WorkflowConfig.WORKFLOW_VARIABLE_PROCESS_DEFINITION_ID;

import com.yeshenko.process.domain.repository.ProcessDefinitionRepository;
import java.util.HashMap;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.flowable.engine.FormService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.form.FormProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class WorkflowService {

  private final RuntimeService runtimeService;
  private final ProcessDefinitionRepository processDefinitionRepository;
  private final TaskService taskService;
  private final FormService formService;

  public UUID startProcess(UUID processDefinitionId) {
    var processDefinition = processDefinitionRepository.findById(processDefinitionId)
        .orElseThrow();

    HashMap<String, Object> processVariables = new HashMap<>();
    processVariables.put(WORKFLOW_VARIABLE_PROCESS_DEFINITION_ID, processDefinition.getId());

    var processInstance = runtimeService.startProcessInstanceByKey(processDefinition.getProcessName(), processVariables);

//    log
    return UUID.fromString(processInstance.getProcessInstanceId());
  }


  public FormProperty getTaskFormProperty(String taskId) {
//    log
    return formService.getTaskFormData(taskId).getFormProperties()
        .stream().filter(i -> WORKFLOW_TASK_DEFINITION_KEY.equals(i.getId()))
        .findFirst().orElseThrow();
  }

  public void setVariable(String processInstanceId, String key, Object value) {
//    log
    runtimeService.setVariable(processInstanceId, key, value);
  }

  public UUID getActiveTaskId(String processInstanceId) {
    if (!StringUtils.hasLength(processInstanceId)) {
//      log
      return null;
    }

    var task = taskService.createTaskQuery()
        .processInstanceId(processInstanceId)
        .singleResult();

    if (task == null) {
//      log
      return null;
    }
    return UUID.fromString(task.getId());
  }

  public void completeTask(String taskId) {
//    log
    taskService.complete(taskId);
  }

  public Object getVariable(String processInstanceId, String key) {
    return runtimeService.getVariable(processInstanceId, key);
  }
}
