package com.yeshenko.process.controller.v1;

import com.yeshenko.process.api.v1.ProcessApiDelegate;
import com.yeshenko.process.models.v1.BuildProcessRequestDto;
import com.yeshenko.process.models.v1.CreateProcessRequestDto;
import com.yeshenko.process.models.v1.ProcessEntityDto;
import com.yeshenko.process.models.v1.TaskCompleteDto;
import com.yeshenko.process.service.ProcessEntityService;
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
  public ResponseEntity<ProcessEntityDto> getProcess(UUID processId, UUID processDefinitionId) {
    return ResponseEntity.ok(processEntityService.getProcess(processId, processDefinitionId));
  }

  @Override
  public ResponseEntity<Void> completeTask(TaskCompleteDto taskCompleteDto) {
    processEntityService.completeTask(taskCompleteDto);
    return ResponseEntity.ok().build();
  }
}
