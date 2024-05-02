package com.yeshenko.processserviceapi.domain.audit;

public interface Auditable {

  Audit getAudit();

  void setAudit(Audit simpleAudit);
}
