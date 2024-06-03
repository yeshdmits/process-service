package com.yeshenko.processserviceapi.service.security.impl;

import com.yeshenko.processserviceapi.service.security.SecurityService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
@ConditionalOnProperty(name = {"spring.security.type"}, havingValue = "none")
public class NoneSecurityService implements SecurityService {
    @Override
    public String getAccountId() {
        return "noAuth";
    }

    @Override
    public String getAccountFullName() {
        return "No Auth";
    }

    @Override
    public List<String> getCustomRole() {
        return List.of("client", "manager");
    }

    @Override
    public Object getUserClaims() {
        HashMap<String, Object> data = new HashMap<>();

        data.put("at_hash", "string");
        data.put("sub", "632ab51a-543f-40cc-9f24-0945cc9885d3");
        data.put("email_verified", false);
        data.put("roles", Arrays.asList("manager", "offline_access", "client", "uma_authorization", "default-roles-process-org-local"));
        data.put("iss", "http://localhost:8088/realms/process-org-local");
        data.put("typ", "ID");
        data.put("preferred_username", "admin");
        data.put("given_name", "admin");
        data.put("nonce", "string");
        data.put("sid", "4600c66a-6131-492a-b169-4fbc78d7fbfd");
        data.put("aud", List.of("process-app"));
        data.put("acr", "1");
        data.put("azp", "process-app");
        data.put("auth_time", 1717324781);
        data.put("name", "admin admin");
        data.put("exp", 1717325081.000000000);
        data.put("session_state", "4600c66a-6131-492a-b169-4fbc78d7fbfd");
        data.put("iat", 1717324781.000000000);
        data.put("family_name", "admin");
        data.put("jti", "6642cb36-692c-44a0-b90e-136e199cafac");
        data.put("email", "admin@admin.admin");
        return data;
    }
}
