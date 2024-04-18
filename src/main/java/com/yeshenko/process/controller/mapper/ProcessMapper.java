package com.yeshenko.process.controller.mapper;

import com.yeshenko.process.domain.entity.Document;
import com.yeshenko.process.domain.entity.ProcessEntity;
import com.yeshenko.process.domain.entity.TaskEntity;
import com.yeshenko.process.models.v1.DocumentDto;
import com.yeshenko.process.models.v1.ProcessEntityDto;
import com.yeshenko.process.models.v1.ProcessEntityListResponseInnerDto;
import com.yeshenko.process.models.v1.TaskDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProcessMapper {

  @Mapping(target = "processEntityId", source = "id")
  @Mapping(target = "displayName", source = "entity.processDefinition.displayName")
  @Mapping(target = "processStatus", source = "entity.status.value")
  @Mapping(target = "taskList", source = "tasks")
  @Mapping(target = "documentList", source = "documents")
  @Mapping(target = "createdAt", source = "audit.createdAt")
  @Mapping(target = "modifiedAt", source = "audit.modifiedAt")
  @Mapping(target = "createdBy", source = "audit.createdBy")
  @Mapping(target = "modifiedBy", source = "audit.modifiedBy")
  ProcessEntityDto toDto(ProcessEntity entity);

  @Mapping(target = "taskId", source = "id")
  @Mapping(target = "taskName", source = "entity.taskDefinition.name")
  @Mapping(target = "taskStatus", source = "entity.taskStatus.value")
  @Mapping(target = "content", source = "formData")
  @Mapping(target = "customTaskName", source = "entity.taskDefinition.customTaskName")
  @Mapping(target = "schema", source = "entity.taskDefinition.schema")
  @Mapping(target = "createdAt", source = "audit.createdAt")
  @Mapping(target = "modifiedAt", source = "audit.modifiedAt")
  @Mapping(target = "createdBy", source = "audit.createdBy")
  @Mapping(target = "modifiedBy", source = "audit.modifiedBy")
  TaskDto toDto(TaskEntity entity);

  @Mapping(target = "documentId", source = "id")
  @Mapping(target = "documentName", source = "name")
  @Mapping(target = "documentStatus", source = "entity.documentStatus.value")
  @Mapping(target = "createdAt", source = "audit.createdAt")
  @Mapping(target = "modifiedAt", source = "audit.modifiedAt")
  @Mapping(target = "createdBy", source = "audit.createdBy")
  @Mapping(target = "modifiedBy", source = "audit.modifiedBy")
  DocumentDto toDto(Document entity);

  List<DocumentDto> toDto(List<Document> entity);

  @Mapping(target = "processEntityId", source = "id")
  @Mapping(target = "displayName", source = "entity.processDefinition.displayName")
  @Mapping(target = "processStatus", source = "entity.status.value")
  @Mapping(target = "modifiedAt", source = "audit.modifiedAt")
  @Mapping(target = "modifiedBy", source = "audit.modifiedBy")
  ProcessEntityListResponseInnerDto toDtoList(ProcessEntity entity);

  List<ProcessEntityListResponseInnerDto> toDtoList(List<ProcessEntity> entity);
}
