package com.khai.admin.exception;

import org.springframework.web.client.RestClientException;

public class AlreadyExist extends RestClientException {
    public AlreadyExist(String className, String value) {
        super(String.format("%s có %s đã tồn tại", className, value));
    }
}
