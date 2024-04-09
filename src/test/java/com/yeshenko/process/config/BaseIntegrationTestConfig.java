package com.yeshenko.process.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"com.yeshenko.process"})
@EnableJpaRepositories(basePackages = "com.yeshenko.process")
public class BaseIntegrationTestConfig {

}
