package com.khai.admin.constants;

public enum UserRole {
    ROLE_VISITOR("ROLE_VISITOR"), ROLE_USER("ROLE_USER"), ROLE_ADMIN("ROLE_ADMIN");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public UserRole getRole(int permission) {
        switch (permission) {
            case 1:
                return ROLE_USER;
            case 2:
                return ROLE_ADMIN;
            default:
                return ROLE_VISITOR;
        }
    }
}
