package com.yeshenko.processserviceapi.controller.v1;

import com.yeshenko.processserviceapi.api.v1.ProcessApiDelegate;
import com.yeshenko.processserviceapi.models.v1.*;
import com.yeshenko.processserviceapi.service.process.ProcessEntityService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessServiceApiDelegateImpl implements ProcessApiDelegate {

  private final ProcessEntityService processEntityService;

  @Override
  public ResponseEntity<Void> buildProcess(BuildProcessRequestDto buildProcessRequestDto) {
    return ProcessApiDelegate.super.buildProcess(buildProcessRequestDto);
  }

  @Override
  public ResponseEntity<UUID> createProcess(CreateProcessRequestDto createProcessRequestDto) {
    return ResponseEntity.ok(processEntityService
            .createProcess(createProcessRequestDto.getProcessDefinitionId()));
  }

  @Override
  public ResponseEntity<ProcessEntityDto> getProcess(UUID processId, UUID processInstanceId) {
    return ResponseEntity.ok(processEntityService.getProcess(processId, processInstanceId));
  }

  @Override
  public ResponseEntity<Void> completeTask(TaskCompleteDto taskCompleteDto) {
    processEntityService.completeTask(taskCompleteDto);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<byte[]> getBinaryData(UUID documentId) {
    return ResponseEntity.ok(processEntityService.getDocumentBinaryData(documentId));
  }

  @Override
  public ResponseEntity<List<ProcessEntityListResponseInnerDto>> fetchProcessList() {
    return ResponseEntity.ok(processEntityService.fetchProcessEntityList());
  }

  @Override
  public ResponseEntity<List<ProcessDefinitionResponseInnerDto>> fetchDefinitions() {
    return ResponseEntity.ok(processEntityService.getProcessDefinitionList());
  }

  @Override
  public ResponseEntity<TaskDto> getTask(UUID taskId) {
    return ResponseEntity.ok(processEntityService.getTask(taskId));
  }
}
