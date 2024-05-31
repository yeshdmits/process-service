package com.yeshenko.processserviceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableEnversRepositories
public class ProcessExampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProcessExampleApplication.class, args);
  }

}
