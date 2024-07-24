package com.khai.admin.entity;

import java.util.UUID;

/**
 * Projection for {@link Category}
 */
public interface CategoryPreview {
    UUID getId();

    String getName();

    boolean isEnabled();

    String getImages();
}