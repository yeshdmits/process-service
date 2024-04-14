package com.yeshenko.process.service.listener;

import static com.yeshenko.process.config.WorkflowConfig.WORKFLOW_CREATE_PROCESS_COMPONENT;
import static com.yeshenko.process.config.WorkflowConfig.WORKFLOW_VARIABLE_PROCESS_DEFINITION_ID;
import static com.yeshenko.process.config.WorkflowConfig.WORKFLOW_VARIABLE_PROCESS_ID;
import static java.util.Optional.ofNullable;

import com.yeshenko.process.service.ProcessEntityService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.springframework.stereotype.Component;

@Component(WORKFLOW_CREATE_PROCESS_COMPONENT)
@RequiredArgsConstructor
public class CreateProcessListener implements ExecutionListener {


  private final ProcessEntityService processEntityService;

  @Override
  public void notify(DelegateExecution execution) {
    var rootProcessInstance = ((ExecutionEntityImpl) execution).getRootProcessInstance();
    UUID processDefinitionId = (UUID) ofNullable(
        rootProcessInstance.getVariable(WORKFLOW_VARIABLE_PROCESS_DEFINITION_ID)).orElseThrow();
//    log

    UUID processId = processEntityService.callSaveEntityFromWorkflow(
        UUID.fromString(rootProcessInstance.getRootProcessInstanceId()),
        processDefinitionId);
//    log

    rootProcessInstance.setVariable(WORKFLOW_VARIABLE_PROCESS_ID, processId);
  }
}
