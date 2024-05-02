package com.yeshenko.process.controller;

import com.yeshenko.process.controller.mapper.dto.LoginRequest;
import com.yeshenko.process.service.auth.KeycloakAuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@ConditionalOnProperty(
    name = {"spring.security.type"},
    havingValue = "jwt"
)
public class LoginController {

  private final KeycloakAuthClient keycloakAuthClient;

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
    return ResponseEntity.ok(keycloakAuthClient.loginUser(loginRequest.getUsername(), loginRequest.getPassword()));
  }

  @PostMapping("/refresh")
  public ResponseEntity<String> refresh(@RequestBody String refreshToken) {
    return ResponseEntity.ok(keycloakAuthClient.refreshToken(refreshToken));
  }
}
