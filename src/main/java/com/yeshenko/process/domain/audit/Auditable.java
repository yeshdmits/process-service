package com.yeshenko.process.domain.audit;

public interface Auditable {

  Audit getAudit();

  void setAudit(Audit simpleAudit);
}
