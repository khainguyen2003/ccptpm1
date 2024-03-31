package com.khai.admin.dto.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khai.admin.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class CategoryDto {
    private int id;
    private String name;

    public CategoryDto() {
    }

    public CategoryDto(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
