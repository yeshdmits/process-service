package com.yeshenko.processserviceapi.controller.mapper.dto;

import lombok.Data;

@Data
public class LoginRequest {

  private String username;

  private String password;
}
