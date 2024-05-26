package com.yeshenko.processserviceapi.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.sf.jasperreports.util.Base64Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
    name = {"spring.security.type"},
    havingValue = "jwt"
)
public class KeycloakAuthClient {

  @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
  private String clientId;

  @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
  private String clientSecret;

  @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
  private String issuerUri;

  private final RestTemplate restTemplate = new RestTemplate();

  @SneakyThrows
  public String loginUser(String username, String password) {
    var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    var requestBody = new LinkedMultiValueMap<>();
    requestBody.add("grant_type", "password");
    requestBody.add("client_id", clientId);
    requestBody.add("client_secret", clientSecret);
    requestBody.add("username", username);
    requestBody.add("password", password);
//    requestBody.add("password", new String(Base64Util.decode(password)));


    var requestEntity = new HttpEntity<>(requestBody, headers);

    var url = issuerUri + "/protocol/openid-connect/token";
    ResponseEntity<String> responseEntity = restTemplate
        .exchange(url, HttpMethod.POST, requestEntity, String.class);
    return responseEntity.getBody();
  }

  @SneakyThrows
  public String refreshToken(String refreshToken) {
    var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    var requestBody = new LinkedMultiValueMap<>();
    requestBody.add("grant_type", "refresh_token");
    requestBody.add("client_id", clientId);
    requestBody.add("client_secret", clientSecret);
    requestBody.add("refresh_token", refreshToken);

    var requestEntity = new HttpEntity<>(requestBody, headers);

    var url = issuerUri + "/protocol/openid-connect/token";
    ResponseEntity<String> responseEntity = restTemplate
        .exchange(url, HttpMethod.POST, requestEntity, String.class);
    return responseEntity.getBody();
  }
}
