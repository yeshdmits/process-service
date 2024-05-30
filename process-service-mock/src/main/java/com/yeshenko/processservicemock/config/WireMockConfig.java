package com.yeshenko.processservicemock.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "wiremock.enabled", havingValue = "true")
public class WireMockConfig {
  @Bean
  public WireMockServer wireMockServer() {
    return new WireMockServer(WireMockConfiguration.options()
        .usingFilesUnderClasspath("wiremock")
        .port(8080));
  }
}
