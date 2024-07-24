package com.khai.admin.dto.category;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record CategoryRequest(
        String name,
        @RequestParam(required = false) String notes,
        @RequestParam(required = false) Boolean enabled,
        @RequestParam(required = false) String images,
        @RequestParam(required = false) UUID parentID,
        @RequestParam(required = false) MultipartFile new_image
) {
}
