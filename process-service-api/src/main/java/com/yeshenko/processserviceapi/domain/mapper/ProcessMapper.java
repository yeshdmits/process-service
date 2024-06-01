package com.yeshenko.processserviceapi.domain.mapper;

import com.yeshenko.processserviceapi.domain.entity.Document;
import com.yeshenko.processserviceapi.domain.entity.ProcessDefinition;
import com.yeshenko.processserviceapi.domain.entity.ProcessEntity;
import com.yeshenko.processserviceapi.domain.entity.TaskEntity;
import com.yeshenko.processserviceapi.models.v1.*;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProcessMapper {

  @Mapping(target = "processDefinitionId", source = "id")
  @Mapping(target = "displayName", source = "displayName")
  ProcessDefinitionResponseInnerDto toDto(ProcessDefinition entity);

  List<ProcessDefinitionResponseInnerDto> toDtoDefinitionList(List<ProcessDefinition> definitions);

  @Mapping(target = "processEntityId", source = "id")
  @Mapping(target = "displayName", source = "entity.processDefinition.displayName")
  @Mapping(target = "processStatus", source = "entity.status.value")
  @Mapping(target = "taskList", source = "tasks")
  @Mapping(target = "documentList", source = "documents")
  @Mapping(target = "createdAt", source = "createdAt")
  @Mapping(target = "modifiedAt", source = "updatedAt")
  @Mapping(target = "createdBy", source = "createdBy")
  @Mapping(target = "modifiedBy", source = "updatedAt")
  ProcessEntityDto toDto(ProcessEntity entity);

  @Mapping(target = "taskId", source = "id")
  @Mapping(target = "taskName", source = "entity.taskDefinition.name")
  @Mapping(target = "taskStatus", source = "entity.taskStatus.value")
  @Mapping(target = "content", source = "formData")
  @Mapping(target = "customTaskName", source = "entity.taskDefinition.customTaskName")
  @Mapping(target = "schema", source = "entity.taskDefinition.schema")
  @Mapping(target = "createdAt", source = "createdAt")
  @Mapping(target = "modifiedAt", source = "updatedAt")
  @Mapping(target = "createdBy", source = "createdBy")
  @Mapping(target = "modifiedBy", source = "updatedBy")
  @Mapping(target = "processId", source = "entity.processEntity.id")
  TaskDto toDto(TaskEntity entity);

  @Mapping(target = "documentId", source = "id")
  @Mapping(target = "documentName", source = "name")
  @Mapping(target = "documentStatus", source = "entity.documentStatus.value")
  @Mapping(target = "createdAt", source = "createdAt")
  @Mapping(target = "modifiedAt", source = "updatedAt")
  @Mapping(target = "createdBy", source = "createdBy")
  @Mapping(target = "modifiedBy", source = "updatedBy")
  DocumentDto toDto(Document entity);

  List<DocumentDto> toDto(List<Document> entity);

  @Mapping(target = "processEntityId", source = "id")
  @Mapping(target = "displayName", source = "entity.processDefinition.displayName")
  @Mapping(target = "processStatus", source = "entity.status.value")
  @Mapping(target = "modifiedAt", source = "updatedAt")
  @Mapping(target = "createdAt", source = "createdAt")
  @Mapping(target = "createdBy", source = "createdBy")
  @Mapping(target = "modifiedBy", source = "updatedBy")
  ProcessEntityListResponseInnerDto toDtoList(ProcessEntity entity);

  List<ProcessEntityListResponseInnerDto> toDtoList(List<ProcessEntity> entity);

}
