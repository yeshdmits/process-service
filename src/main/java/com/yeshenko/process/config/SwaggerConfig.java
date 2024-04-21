package com.yeshenko.process.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("Process Service Management")
            .description("<h2>Service REST APIs</h2>"
                + "<p>This component is responsible for Process Services</p>"
                + "<p>Architectural name: <i>PSM</i></p>")
            .version("1.0"))
        .components(new Components()
            .addSecuritySchemes("bearer-jwt",
                new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .description("Keycloak Auth")
            ))
        .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"));
  }
}
