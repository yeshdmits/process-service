package com.yeshenko.processserviceapi.controller;

import com.yeshenko.processserviceapi.service.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JwtController {

    private final SecurityService securityService;

    @GetMapping("/userInfo")
    public ResponseEntity<Object> getUserInfo() {
        return ResponseEntity.ok(securityService.getUserClaims());
    }

    @GetMapping("/account")
    public ResponseEntity<Object> getAccountName() {
        return ResponseEntity.ok(securityService.getAccountFullName());
    }
}
