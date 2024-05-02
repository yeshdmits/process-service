package com.yeshenko.processserviceapi.domain.audit;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class AuditListener {

  public static final String DEFAULT_ACCOUNT_ID = "admin";

  @PrePersist
  void onCreate(Object sa) {
    Auditable auditable = ((Auditable) sa);

    if (auditable.getAudit() == null) {
      auditable.setAudit(new Audit());
    }

    if (auditable.getAudit().getCreatedBy() == null) {

      LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
      auditable.getAudit().setCreatedAt(now);
      auditable.getAudit().setModifiedAt(now);
      auditable.getAudit().setCreatedBy(DEFAULT_ACCOUNT_ID);
      auditable.getAudit().setModifiedBy(DEFAULT_ACCOUNT_ID);
    }
  }

  @PreUpdate
  void onPersist(Object sa) {
    Auditable simpleAuditable = ((Auditable) sa);
    if (simpleAuditable.getAudit().getCreatedBy() != null) {

      simpleAuditable.getAudit().setModifiedAt(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
      simpleAuditable.getAudit().setModifiedBy(DEFAULT_ACCOUNT_ID);
    }
  }
}
