package com.khai.admin.repository.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    Page<Object[]> getProducts(
        Pageable pageable,
       String search,
       byte allowSale,
       String stockOutDate,
       String stockoutStartDate,
       String stockoutEndDate,
       byte onHandFilter,
       String onHandFilterStr,
       int[] brandIds,
       byte directSell,
       byte relateToChanel
    );
}
