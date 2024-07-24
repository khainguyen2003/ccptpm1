package com.khai.admin.dto.category;

import com.khai.admin.entity.Category;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDto implements Serializable {
    private String name;
    private String notes;
    private boolean enabled;
    private String images;
    private int sort;
    private UUID parentID;
    private Date createdDate;
    private Date deletedDate;
    private Date lastModified;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategorySaveRequest that)) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public CategorySaveRequest(Category category) {
        this.enabled = category.isEnabled();
        this.images = category.getImages();
        this.name = category.getName();
        this.parentID = category.getParent().getId();
        this.sort = category.getSort();
        this.notes = category.getNotes();
        this.createdDate =category.getCreated_date();
        this.deletedDate = category.getDeleted_date();
        this.lastModified = category.getLast_modified();
    }

    public void appyToEntity(Category entity) {
        entity.setEnabled(this.enabled);
        entity.setImages(this.images);
        entity.setName(this.name);
        entity.setSort(this.sort);
        entity.setNotes(this.notes);
        entity.setCreated_date(this.createdDate);
        entity.setDeleted_date(this.deletedDate);
        entity.setLast_modified(this.lastModified);
    }
}
