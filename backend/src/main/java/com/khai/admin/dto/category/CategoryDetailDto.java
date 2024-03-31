package com.khai.admin.dto.category;

import com.khai.admin.entity.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class CategoryDetailDto {
    private int id;
    private String name;
    private String notes;
    private boolean deleted;
    private boolean enabled;
    private Date deleted_date;
    private Date last_modified;
    private Date created_date;
    private String images;
    private List<Product> products;
}
