package com.yeshenko.process.domain.enumeration;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum DocumentStatusEnum {
  CREATED("Created"),
  SENT("Sent"),
  COMPLETED("Completed"),
  WITHDRAWN("Withdrawn");

  private final String value;

  DocumentStatusEnum(String value) {
    this.value = value;
  }

  public static DocumentStatusEnum fromValue(String taskStatus) {
    return Arrays.stream(DocumentStatusEnum.values())
        .filter(i -> i.value.equalsIgnoreCase(taskStatus))
        .findFirst()
        .orElseThrow();
  }
}
