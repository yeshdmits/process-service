package com.yeshenko.processserviceapi.domain.util;

import com.yeshenko.processserviceapi.domain.entity.ProcessEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Sort;
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

  public static Sort getDefaultSort() {
    return Sort.by("createdAt").descending();
  }

  public static Sort getSort(String field, String order) {
    if ("asc".equals(order)) {
      return Sort.by(field).ascending();
    }
    return Sort.by(field).descending();
  }

  public static Specification<ProcessEntity> addCriteria(String field, String value, String criteria) {
      return switch (criteria.toLowerCase()) {
          case "eq" -> (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(field), value);
          case "like" -> (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(field), '%' + value + '%');
          case "gt" -> (root, query, criteriaBuilder) -> {
              var validatedValue = handleNonString(root.get(field).getModel().getBindableJavaType(), value);
              return criteriaBuilder.greaterThanOrEqualTo(root.get(field), (Comparable<Object>) validatedValue);
          };
          case "lt" -> (root, query, criteriaBuilder) -> {
              var validatedValue = handleNonString(root.get(field).getModel().getBindableJavaType(), value);
              return criteriaBuilder.lessThanOrEqualTo(root.get(field), (Comparable<Object>) validatedValue);
          };
          case "in" -> (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(field).in(value));
          default -> throw new IllegalArgumentException("Invalid criteria: " + criteria);
      };
  }

  private static Object handleNonString(Object object, String value) {
    if (object.equals(LocalDateTime.class)) {
      return LocalDate.parse(value, DateTimeFormatter.ofPattern("dd.MM.yyy")).atStartOfDay();
    }
    return value;
  }
}
