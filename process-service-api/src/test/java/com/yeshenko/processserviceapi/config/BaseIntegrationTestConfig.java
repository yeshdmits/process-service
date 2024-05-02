package com.yeshenko.processserviceapi.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"com.yeshenko.processserviceapi"})
@EnableJpaRepositories(basePackages = "com.yeshenko.processserviceapi")
public class BaseIntegrationTestConfig {

}
