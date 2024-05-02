package com.yeshenko.processserviceapi.domain.entity;

import static com.yeshenko.processserviceapi.domain.entity.ProcessDefinition.ProcessDefinitionColumn.DISPLAY_NAME;
import static com.yeshenko.processserviceapi.domain.entity.ProcessDefinition.ProcessDefinitionColumn.PROCESS_NAME;
import static com.yeshenko.processserviceapi.domain.entity.ProcessDefinition.ProcessDefinitionColumn.TABLE_NAME;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class ProcessDefinition {

  public static class ProcessDefinitionColumn {
    public static final String TABLE_NAME = "process_definition";
    public static final String DISPLAY_NAME = "display_name";
    public static final String PROCESS_NAME = "process_name";

    private ProcessDefinitionColumn() {}
  }

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = DISPLAY_NAME)
  private String displayName;

  @Column(name = PROCESS_NAME)
  private String processName;
}
