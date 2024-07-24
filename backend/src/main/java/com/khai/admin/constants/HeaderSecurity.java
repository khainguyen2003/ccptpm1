package com.khai.admin.constants;

public enum HeaderSecurity {
    API_KEY("x-api-key"), AUTHORIZATION("Authorization"), CLIENT_ID("x-client-id"), REFRESH_TOKEN("x-rfresh-token");
    private final String value;

    HeaderSecurity(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
