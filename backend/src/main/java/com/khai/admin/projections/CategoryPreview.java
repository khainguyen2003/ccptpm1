package com.khai.admin.projections;

import java.util.UUID;

/**
 * Projection for {@link com.khai.admin.entity.Category}
 */
public interface CategoryPreview {
    UUID getId();

    String getName();

    boolean isDeleted();

    boolean isEnabled();

    String getImages();
}