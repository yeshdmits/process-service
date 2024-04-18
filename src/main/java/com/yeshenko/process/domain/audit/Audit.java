package com.yeshenko.process.domain.audit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Audit {
  public static final String CREATED_BY = "created_by";
  public static final String MODIFIED_BY = "modified_by";
  public static final String CREATED_AT = "created_at";
  public static final String MODIFIED_AT = "modified_at";

  @Column(name = CREATED_BY, nullable = false)
  private String createdBy;
  @Column(name = MODIFIED_BY, nullable = false)
  private String modifiedBy;

  @Column(name = CREATED_AT, nullable = false)
  private LocalDateTime createdAt;

  @Column(name = MODIFIED_AT, nullable = false)
  private LocalDateTime modifiedAt;

}
