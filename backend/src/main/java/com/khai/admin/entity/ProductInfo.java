package com.khai.admin.entity;

import java.util.UUID;

/**
 * Projection for {@link Product}
 */
public interface ProductInfo {
    UUID getId();

    String getName();

    String getProduct_thumb();

    double getSell_price();

    boolean isDeleted();

    boolean isIsStopCell();

    boolean isIsDirectCell();

    String getCode();

    float getRate();

    boolean isIsDraft();

    boolean isIsPublish();

    String getProduct_type();

    CategoryPreview getCategory();
}