package com.yeshenko.processserviceapi.domain.entity;

import com.yeshenko.processserviceapi.domain.enumeration.TaskStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.util.UUID;

import static com.yeshenko.processserviceapi.domain.entity.TaskEntity.TaskEntityColumn.*;

@Entity
@Table(name = TABLE_NAME)
@Audited
@Builder(toBuilder = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class TaskEntity extends UpdateAuditableEntity {

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

}
