package com.yeshenko.process.domain.entity;

import static com.yeshenko.process.domain.entity.TaskEntity.TaskEntityColumn.COLUMN_STATUS;
import static com.yeshenko.process.domain.entity.TaskEntity.TaskEntityColumn.FLOWABLE_TASK_ID;
import static com.yeshenko.process.domain.entity.TaskEntity.TaskEntityColumn.FORM_DATA;
import static com.yeshenko.process.domain.entity.TaskEntity.TaskEntityColumn.PROCESS_ID_FK;
import static com.yeshenko.process.domain.entity.TaskEntity.TaskEntityColumn.TABLE_NAME;
import static com.yeshenko.process.domain.entity.TaskEntity.TaskEntityColumn.TASK_DEFINITION_ID_FK;

import com.yeshenko.process.domain.enumeration.TaskStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {

  public static class TaskEntityColumn {
    public static final String TABLE_NAME = "task";
    public static final String TASK_DEFINITION_ID_FK = "task_definition_id_fk";
    public static final String COLUMN_STATUS = "status";
    public static final String PROCESS_ID_FK = "process_id_fk";
    public static final String FLOWABLE_TASK_ID = "flowable_task_id";
    public static final String FORM_DATA = "form_data";

    private TaskEntityColumn() {}
  }

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = TASK_DEFINITION_ID_FK, nullable = false)
  private TaskDefinition taskDefinition;

  @Column(name = COLUMN_STATUS)
  @Enumerated(EnumType.STRING)
  private TaskStatusEnum taskStatus;

  @ManyToOne
  @JoinColumn(name = PROCESS_ID_FK, nullable = false)
  private ProcessEntity processEntity;

  @Column(name = FLOWABLE_TASK_ID)
  private UUID flowableTaskId;

  @Column(name = FORM_DATA, columnDefinition = "json")
  private String formData;

  @Column(name = "custom_task_name") //?
  private String customTaskName;
}
