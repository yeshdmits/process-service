package com.yeshenko.processserviceapi.domain.util;

import com.yeshenko.processserviceapi.domain.entity.ProcessEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationUtil {

  private SpecificationUtil() {}

  public static Specification<ProcessEntity> getSpecification(UUID processEntityId,
      UUID processInstanceId) {
    return Specification
        .where(
            Optional
                .ofNullable(processEntityId)
                .map(SpecificationUtil::processEntityIdIs)
                .orElse(null)
        )
        .and(
            Optional
                .ofNullable(processInstanceId)
                .map(SpecificationUtil::processInstanceIdIs)
                .orElse(null)
        );
  }

  public static Specification<ProcessEntity> processEntityIdIs(final UUID processEntityId) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.equal(root.get("id"), processEntityId);
  }

  public static Specification<ProcessEntity> processInstanceIdIs(final UUID processInstanceId) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.equal(root.get("processInstanceId"), processInstanceId);
  }
}
