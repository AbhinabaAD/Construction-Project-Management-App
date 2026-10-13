
package com.infratrack.infratech_backend.dto;

import java.util.Map;

public class LoginResponse {
    private final String token;
    private final Map<String, Object> user;

    public LoginResponse(String token, Map<String, Object> user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public Map<String, Object> getUser() {
        return user;
    }
}

