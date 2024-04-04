package com.khai.admin.dto.Product;

//import com.khai.admin.dto.category.CategorySumary;

public interface ProductSumary {
    int getId();
    String getName();
    CategorySumary getCategory();

    interface CategorySumary {
        String getId();
        String getName();
    }
}
