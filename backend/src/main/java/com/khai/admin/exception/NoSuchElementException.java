package com.khai.admin.exception;

import org.springframework.web.client.RestClientException;

public class NoSuchElementException extends RestClientException {
    public NoSuchElementException(String className, String value) {
        super(String.format("không tìm thấy %s có %s", className, value));
    }
    public NoSuchElementException(String message) {
        super(message);
    }
}
