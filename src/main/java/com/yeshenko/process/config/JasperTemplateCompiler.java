package com.yeshenko.process.config;

import java.io.InputStream;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasperTemplateCompiler {

  @Bean
  @SneakyThrows
  public JasperReport compiledReport(
      @Value("${jasper.report.path:/reports/ExampleReport.jrxml}") String jasperTemplatePath) {
    try (InputStream reportIo = getClass().getResourceAsStream(jasperTemplatePath)) {
      return JasperCompileManager.compileReport(reportIo);
    }
  }
}
