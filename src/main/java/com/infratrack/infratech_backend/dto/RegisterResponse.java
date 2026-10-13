package com.infratrack.infratech_backend.dto;

import java.time.Instant;

public class RegisterResponse {
    private final String id;
    private final String fullName;
    private final String email;
    private final String role;
    private final Instant createdAt;

    public RegisterResponse(String id, String fullName, String email, String role, Instant createdAt) {
        this.id = id; this.fullName = fullName; this.email = email; this.role = role; this.createdAt = createdAt;
    }
    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public Instant getCreatedAt() { return createdAt; }
}
