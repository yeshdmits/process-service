package com.yeshenko.process.service.flowable.delegate;

import static com.yeshenko.process.config.WorkflowConfig.WORKFLOW_DEFAULT_PROCESS_COMPONENT;
import static com.yeshenko.process.config.WorkflowConfig.WORKFLOW_VARIABLE_FORM_DATA;
import static com.yeshenko.process.config.WorkflowConfig.WORKFLOW_VARIABLE_PROCESS_DEFINITION_ID;
import static com.yeshenko.process.config.WorkflowConfig.WORKFLOW_VARIABLE_PROCESS_ID;
import static java.util.Optional.ofNullable;

import com.yeshenko.process.domain.enumeration.ProcessStatusEnum;
import com.yeshenko.process.service.process.PdfReportService;
import com.yeshenko.process.service.process.ProcessEntityService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.springframework.stereotype.Component;

@Component(WORKFLOW_DEFAULT_PROCESS_COMPONENT)
@RequiredArgsConstructor
public class DefaultProcessDelegate {


  private final ProcessEntityService processEntityService;
  private final PdfReportService pdfReportService;

  public void createProcessEntity(DelegateExecution execution) {
    var rootProcessInstance = ((ExecutionEntityImpl) execution).getRootProcessInstance();
    var processDefinitionId = (UUID) ofNullable(
        rootProcessInstance.getVariable(WORKFLOW_VARIABLE_PROCESS_DEFINITION_ID))
        .orElseThrow();
//    log

    var processId = processEntityService.callSaveEntityFromWorkflow(
        UUID.fromString(rootProcessInstance.getRootProcessInstanceId()),
        processDefinitionId);
//    log

    rootProcessInstance.setVariable(WORKFLOW_VARIABLE_PROCESS_ID, processId);
  }

  public void prepareDocuments(DelegateExecution execution) {
    var rootProcessInstance = ((ExecutionEntityImpl) execution).getRootProcessInstance();

    var processEntityId = (UUID) ofNullable(
        rootProcessInstance.getVariable(WORKFLOW_VARIABLE_PROCESS_ID))
        .orElseThrow();

    processEntityService.updateProcessStatus(processEntityId, ProcessStatusEnum.IN_PROGRESS);

    var formData = (String) ofNullable(
        rootProcessInstance.getVariable(WORKFLOW_VARIABLE_FORM_DATA))
        .orElseThrow();

    byte[] content = pdfReportService.generatePdfDocument(formData);

    processEntityService.createDocument(
        UUID.fromString(rootProcessInstance.getProcessInstanceId()),
        content);
  }

  public void sendDocuments(DelegateExecution execution) {
    var processEntityId = (UUID) ofNullable(
        ((ExecutionEntityImpl) execution).getRootProcessInstance()
            .getVariable(WORKFLOW_VARIABLE_PROCESS_ID))
        .orElseThrow();
    processEntityService.sendDocumentDistributionRequest(processEntityId);
  }

  public void activate(DelegateExecution execution) {
    var processEntityId = (UUID) ofNullable(
        ((ExecutionEntityImpl) execution).getRootProcessInstance()
            .getVariable(WORKFLOW_VARIABLE_PROCESS_ID))
        .orElseThrow();

    processEntityService.updateProcessStatus(processEntityId, ProcessStatusEnum.ACTIVE);

    processEntityService.validateDocuments(processEntityId);
  }

  public void reject(DelegateExecution execution) {
    var processEntityId = (UUID) ofNullable(
        ((ExecutionEntityImpl) execution).getRootProcessInstance()
            .getVariable(WORKFLOW_VARIABLE_PROCESS_ID))
        .orElseThrow();

    processEntityService.updateProcessStatus(processEntityId, ProcessStatusEnum.CREATED);

    processEntityService.rejectDocuments(processEntityId);
  }
}
