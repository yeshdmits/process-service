package com.yeshenko.processserviceapi.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

import lombok.*;
import org.hibernate.envers.Audited;

import static com.yeshenko.processserviceapi.domain.entity.ProcessDefinition.ProcessDefinitionColumn.*;
import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

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
public class ProcessDefinition {

  public static class ProcessDefinitionColumn {
    public static final String TABLE_NAME = "process_definition";
    public static final String DISPLAY_NAME = "display_name";
    public static final String PROCESS_NAME = "process_name";
    public static final String DESCRIPTION = "description";

    private ProcessDefinitionColumn() {}
  }

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = DISPLAY_NAME)
  private String displayName;

  @Column(name = PROCESS_NAME)
  private String processName;

  @Column(name = DESCRIPTION)
  private String description;
}
