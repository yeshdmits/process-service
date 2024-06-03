package com.yeshenko.processserviceapi.service.security;

import java.util.List;

public interface SecurityService {
    String getAccountId();

    String getAccountFullName();

    List<String> getCustomRole();

    Object getUserClaims();
}
