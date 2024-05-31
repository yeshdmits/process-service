package com.yeshenko.processserviceapi.domain.entity;

import com.yeshenko.processserviceapi.domain.enumeration.DocumentStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.util.UUID;

import static com.yeshenko.processserviceapi.domain.entity.Document.DocumentColumn.*;

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
public class Document extends UpdateAuditableEntity {

  public static class DocumentColumn {

    public static final String TABLE_NAME = "document";
    public static final String PROCESS_ID_FK = "process_id_fk";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_CONTENT = "content";

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

  @Column(name = COLUMN_CONTENT, columnDefinition = "bytea")
  private byte[] content;

  @ManyToOne
  @JoinColumn(name = PROCESS_ID_FK, nullable = false)
  private ProcessEntity processEntity;

}
