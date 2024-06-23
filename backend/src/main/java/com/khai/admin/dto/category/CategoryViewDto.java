package com.khai.admin.dto.category;

import com.khai.admin.entity.Category;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryViewDto {
    private int id;
    private String name;

    public CategoryViewDto() {
    }

    public CategoryViewDto(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public CategoryViewDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
    public Category convertToCategory(){
        Category category = new Category();
        category.setId(this.id);
        category.setName(this.name);

        return category;
    }
}
