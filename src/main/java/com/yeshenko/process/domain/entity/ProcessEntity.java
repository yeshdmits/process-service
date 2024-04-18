package com.yeshenko.process.domain.entity;

import static com.yeshenko.process.domain.entity.ProcessEntity.ProcessEntityColumn.COLUMN_STATUS;
import static com.yeshenko.process.domain.entity.ProcessEntity.ProcessEntityColumn.PROCESS_DEFINITION_ID_FK;
import static com.yeshenko.process.domain.entity.ProcessEntity.ProcessEntityColumn.PROCESS_ENTITY;
import static com.yeshenko.process.domain.entity.ProcessEntity.ProcessEntityColumn.PROCESS_INSTANCE_ID;
import static com.yeshenko.process.domain.entity.ProcessEntity.ProcessEntityColumn.TABLE_NAME;

import com.yeshenko.process.domain.audit.Audit;
import com.yeshenko.process.domain.audit.AuditListener;
import com.yeshenko.process.domain.audit.Auditable;
import com.yeshenko.process.domain.enumeration.ProcessStatusEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
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
@EntityListeners(AuditListener.class)
public class ProcessEntity implements Auditable {

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

  @Embedded
  private Audit audit;
}
