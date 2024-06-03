package com.yeshenko.processserviceapi.exception;

import com.yeshenko.processserviceapi.models.v1.HttpProblemDto;
import com.yeshenko.processserviceapi.service.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.LocalDateTime;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {
    private final SecurityService securityService;

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<HttpProblemDto> badRequest(BadRequestException e) {
        return ResponseEntity.badRequest().body(
                new HttpProblemDto()
                        .detail(e.getLocalizedMessage())
                        .status(400)
                        .timestamp(LocalDateTime.now())
                        .title("Bad Request")
                        .userId(securityService.getAccountId())
                        .spanId("")
                        .instance(URI.create("http://localhost:8080"))
        );
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<HttpProblemDto> forbidden(ForbiddenException e) {
        return ResponseEntity.status(403).body(
                new HttpProblemDto()
                        .detail(e.getLocalizedMessage())
                        .status(403)
                        .timestamp(LocalDateTime.now())
                        .title("Forbidden")
                        .userId(securityService.getAccountId())
                        .spanId("")
                        .instance(URI.create("http://localhost:8080"))
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<HttpProblemDto> notFound(NotFoundException e) {
        return ResponseEntity.status(404).body(
                new HttpProblemDto()
                        .detail(e.getLocalizedMessage())
                        .status(404)
                        .timestamp(LocalDateTime.now())
                        .title("Not Found")
                        .userId(securityService.getAccountId())
                        .spanId("")
                        .instance(URI.create("http://localhost:8080"))
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<HttpProblemDto> unexpected(RuntimeException e) {
        return ResponseEntity.status(500).body(
                new HttpProblemDto()
                        .detail(e.getLocalizedMessage())
                        .status(500)
                        .timestamp(LocalDateTime.now())
                        .title("Unexpected Error")
                        .userId(securityService.getAccountId())
                        .spanId("")
                        .instance(URI.create("http://localhost:8080"))
        );
    }
}
