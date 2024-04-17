package com.khai.admin.dto.category;

import com.khai.admin.entity.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class CategoryViewDto {
    private UUID id;
    private String name;

    public CategoryViewDto() {
    }

    public CategoryViewDto(UUID id, String name) {
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
