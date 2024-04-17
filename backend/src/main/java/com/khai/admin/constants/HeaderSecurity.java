package com.khai.admin.dto;

public enum HeaderSecurity {
    API_KEY("x-api-key"), AUTHORIZATION("Authorization");
    private final String value;

    HeaderSecurity(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
