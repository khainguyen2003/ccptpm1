package com.khai.admin.exception;

import org.springframework.web.client.RestClientException;

public class AlreadyExist extends RestClientException {
    public AlreadyExist(String objectName, String value) {
    super(String.format("%s có %s đã tồn tại", objectName, value));
    }
    public AlreadyExist(String className) {
        super(String.format("%s đã tồn tại", className));
    }
}
