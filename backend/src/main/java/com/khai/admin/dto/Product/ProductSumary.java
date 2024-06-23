package com.khai.admin.dto.Product;

//import com.khai.admin.dto.category.CategorySumary;

import java.util.UUID;

public interface ProductSumary {
    UUID getId();
    String getName();
    CategorySumary getCategory();

    interface CategorySumary {
        String getId();
        String getName();
    }
}
