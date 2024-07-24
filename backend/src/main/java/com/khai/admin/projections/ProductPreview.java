package com.khai.admin.projections;

import com.khai.admin.entity.User;

import java.util.UUID;

/**
 * Projection for {@link com.khai.admin.entity.Product}
 */
public interface ProductPreview {
    UUID getId();

    String getName();

    String getProduct_thumb();

    double getSell_price();

    boolean isDeleted();

    String getCode();

    boolean isIsDraft();

    boolean isIsPublish();

    String getSlug();

    String getProduct_type();

    User getShop();

    CategoryPreview getCategory();
}