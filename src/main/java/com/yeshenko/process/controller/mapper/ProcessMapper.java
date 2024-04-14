package com.yeshenko.process.controller.mapper;

import com.yeshenko.process.domain.entity.ProcessEntity;
import com.yeshenko.process.domain.entity.TaskEntity;
import com.yeshenko.process.models.v1.ProcessEntityDto;
import com.yeshenko.process.models.v1.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProcessMapper {

  @Mapping(target = "processEntityId", source = "id")
  @Mapping(target = "displayName", source = "entity.processDefinition.displayName")
  @Mapping(target = "taskList", source = "tasks")
  ProcessEntityDto toDto(ProcessEntity entity);

  @Mapping(target = "taskId", source = "id")
  @Mapping(target = "taskName", source = "entity.taskDefinition.name")
  @Mapping(target = "taskStatus", source = "entity.taskStatus.value")
  @Mapping(target = "content", source = "formData")
  @Mapping(target = "customTaskName", source = "customTaskName")
  @Mapping(target = "schema", source = "entity.taskDefinition.schema")
  TaskDto toDto(TaskEntity entity);
}
