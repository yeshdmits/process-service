package com.yeshenko.processserviceapi.domain.entity;

import static com.yeshenko.processserviceapi.domain.entity.TaskDefinition.TaskDefinitionColumn.COLUMN_NAME;
import static com.yeshenko.processserviceapi.domain.entity.TaskDefinition.TaskDefinitionColumn.COLUMN_SCHEMA;
import static com.yeshenko.processserviceapi.domain.entity.TaskDefinition.TaskDefinitionColumn.CUSTOM_TASK_NAME;
import static com.yeshenko.processserviceapi.domain.entity.TaskDefinition.TaskDefinitionColumn.DEFINITION_KEY;
import static com.yeshenko.processserviceapi.domain.entity.TaskDefinition.TaskDefinitionColumn.TABLE_NAME;
import static com.yeshenko.processserviceapi.domain.entity.TaskDefinition.TaskDefinitionColumn.ASSIGN_ROLE;
import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

import lombok.*;
import org.hibernate.envers.Audited;

@Entity
@Table(name = TABLE_NAME)
@Builder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Audited(targetAuditMode = NOT_AUDITED)
public class TaskDefinition {

  public static class TaskDefinitionColumn {
    public static final String TABLE_NAME = "task_definition";
    public static final String COLUMN_NAME = "name";
    public static final String DEFINITION_KEY = "definition_key";
    public static final String COLUMN_SCHEMA = "schema";
    public static final String CUSTOM_TASK_NAME = "custom_task_name";
    public static final String ASSIGN_ROLE = "assign_role";

    private TaskDefinitionColumn() {}
  }

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = COLUMN_NAME)
  private String name;

  @Column(name = DEFINITION_KEY)
  private String definitionKey;

  @Column(name = COLUMN_SCHEMA)
  private String schema;

  @Column(name = CUSTOM_TASK_NAME)
  private String customTaskName;

  @Column(name = ASSIGN_ROLE)
  private String assignRole;
}
