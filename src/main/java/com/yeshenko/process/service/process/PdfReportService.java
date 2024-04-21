package com.yeshenko.process.service.process;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PdfReportService {

  private final JasperReport jasperReport;

  @SneakyThrows
  public byte[] generatePdfDocument(String jsonData) {
    try (InputStream jsonDataStream = new ByteArrayInputStream(jsonData.getBytes())) {

      JsonDataSource dataSource = new JsonDataSource(jsonDataStream);

      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

      return JasperExportManager.exportReportToPdf(jasperPrint);
    }
  }
}
