package com.yeshenko.process.config;

import lombok.SneakyThrows;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;

public class MqTestContainer {

  private static final String IMAGE_VERSION = "icr.io/ibm-messaging/mq:latest";
  private static final String MQ_PORT = "MQ_PORT";

  @Container
  static GenericContainer<?> mqContainer;
  private static MqTestContainer instance;

  private MqTestContainer() {
    mqContainer = new GenericContainer<>(IMAGE_VERSION);
    mqContainer
        .withExposedPorts(1414, 1414)
        .withEnv("LICENSE", "accept")
        .withEnv("MQ_APP_PASSWORD", "notsecret")
        .withEnv("MQ_ADMIN_PASSWORD", "notsecret")
        .withEnv("MQ_QMGR_NAME", "QM1");
  }

  public static MqTestContainer getInstance() {
    if (instance == null) {
      instance = new MqTestContainer();
    }
    return instance;
  }

  @SneakyThrows
  public void start() {
    if (!mqContainer.isCreated()) {
      mqContainer.start();
    }
    System.setProperty(MQ_PORT, String.valueOf(mqContainer.getMappedPort(1414)));
  }
}

