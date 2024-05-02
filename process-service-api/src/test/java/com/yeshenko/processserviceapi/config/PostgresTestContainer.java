package com.yeshenko.processserviceapi.config;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresTestContainer extends PostgreSQLContainer<PostgresTestContainer> {

  /**
   * Test database connection url
   */
  public static final String DB_CONNECTION_URL = "DB_URL";
  /**
   * Database system user
   */
  public static final String DB_USERNAME = "DB_USERNAME";
  /**
   * Database system user password
   */
  public static final String DB_PASSWORD = "DB_PASSWORD";
  private static final String IMAGE_VERSION = "postgres:alpine3.19";
  private static PostgresTestContainer container;

  private PostgresTestContainer() {
    super(IMAGE_VERSION);
  }

  public static PostgresTestContainer getInstance() {
    if (container == null) {
      container = new PostgresTestContainer();
    }
    return container;
  }

  @Override
  public void start() {
    super.start();
    System.setProperty(DB_CONNECTION_URL, container.getJdbcUrl());
    System.setProperty(DB_USERNAME, container.getUsername());
    System.setProperty(DB_PASSWORD, container.getPassword());
  }
}
