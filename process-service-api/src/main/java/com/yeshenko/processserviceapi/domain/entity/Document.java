package com.yeshenko.processserviceapi.domain.entity;

import static com.yeshenko.processserviceapi.domain.entity.Document.DocumentColumn.COLUMN_NAME;
import static com.yeshenko.processserviceapi.domain.entity.Document.DocumentColumn.COLUMN_STATUS;
import static com.yeshenko.processserviceapi.domain.entity.Document.DocumentColumn.PROCESS_ID_FK;
import static com.yeshenko.processserviceapi.domain.entity.Document.DocumentColumn.TABLE_NAME;

import com.yeshenko.processserviceapi.domain.audit.Audit;
import com.yeshenko.processserviceapi.domain.audit.AuditListener;
import com.yeshenko.processserviceapi.domain.audit.Auditable;
import com.yeshenko.processserviceapi.domain.enumeration.DocumentStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@EntityListeners(AuditListener.class)
public class Document implements Auditable {

  public static class DocumentColumn {

    public static final String TABLE_NAME = "document";
    public static final String PROCESS_ID_FK = "process_id_fk";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_STATUS = "status";

    private DocumentColumn() {
    }
  }

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = COLUMN_NAME)
  private String name;

  @Column(name = COLUMN_STATUS)
  @Enumerated(EnumType.STRING)
  private DocumentStatusEnum documentStatus;

  @Column(name = "content", columnDefinition = "bytea")
  private byte[] content;

  @ManyToOne
  @JoinColumn(name = PROCESS_ID_FK, nullable = false)
  private ProcessEntity processEntity;

  @Embedded
  private Audit audit;
}