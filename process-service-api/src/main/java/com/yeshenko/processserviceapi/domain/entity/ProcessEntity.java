package com.yeshenko.processserviceapi.domain.entity;

import com.yeshenko.processserviceapi.domain.enumeration.ProcessStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.util.List;
import java.util.UUID;

import static com.yeshenko.processserviceapi.domain.entity.ProcessEntity.ProcessEntityColumn.*;

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
public class ProcessEntity extends UpdateAuditableEntity {

  public static class ProcessEntityColumn {
    public static final String TABLE_NAME = "process";
    public static final String PROCESS_DEFINITION_ID_FK = "process_definition_id_fk";
    public static final String PROCESS_INSTANCE_ID = "process_instance_id";
    public static final String COLUMN_STATUS = "status";
    public static final String PROCESS_ENTITY = "processEntity";

    private ProcessEntityColumn() {}
  }

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = PROCESS_DEFINITION_ID_FK, nullable = false)
  private ProcessDefinition processDefinition;

  @Column(name = PROCESS_INSTANCE_ID)
  private UUID processInstanceId;

  @Enumerated(EnumType.STRING)
  @Column(name = COLUMN_STATUS)
  private ProcessStatusEnum status;

  @OneToMany(mappedBy = PROCESS_ENTITY, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<TaskEntity> tasks;

  @OneToMany(mappedBy = PROCESS_ENTITY, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<Document> documents;

}
