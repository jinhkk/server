package com.tableorder.server.entity;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("admin"), // 점장
    STAFF("staff");   // 직원

    private final String role;

    UserRole(String role) {
        this.role = role;
    }
}