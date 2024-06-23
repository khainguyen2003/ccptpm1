package com.khai.admin.dto.category;

import com.khai.admin.entity.Product;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
public class CategoryDetailDto implements Serializable {
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

    public CategoryDetailDto() {
    }

    public CategoryDetailDto(int id, String name, String notes, boolean deleted, boolean enabled, Date deleted_date, Date last_modified, Date created_date, String images, List<Product> products) {
        this.id = id;
        this.name = name;
        this.notes = notes;
        this.deleted = deleted;
        this.enabled = enabled;
        this.deleted_date = deleted_date;
        this.last_modified = last_modified;
        this.created_date = created_date;
        this.images = images;
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryDetailDto that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
