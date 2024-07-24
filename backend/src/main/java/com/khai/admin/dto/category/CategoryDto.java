package com.khai.admin.dto.category;

import com.khai.admin.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDto implements Serializable {
    private UUID id;
    private String name;
    private String notes;
    private Boolean enabled;
    private String images;
    private Integer sort;
    private CategoryDto parent;
    private UUID parentID;
    private Date createdDate;
    private Date deletedDate;
    private Date lastModified;
    private MultipartFile new_image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryDto that)) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.enabled = category.isEnabled();
        this.images = category.getImages();
        this.name = category.getName();
        if(category.getParent() != null) {
            this.parent = new CategoryDto(category.getParent());
        }
        this.sort = category.getSort();
        this.notes = category.getNotes();
        this.createdDate =category.getCreated_date();
        this.deletedDate = category.getDeleted_date();
        this.lastModified = category.getLast_modified();

    }

    public CategoryDto(String name, String notes, boolean enabled, Integer sort) {
        this.name = name;
        this.notes = notes;
        this.enabled = enabled;
        if(sort != null) {
            this.sort = sort;

        }
    }

    public void appyToEntity(Category entity) {
        Date now = new Date();
        if(this.enabled != null) {
            entity.setEnabled(this.enabled);
        }
        entity.setImages(this.images);
        if(this.name != null && !this.name.isBlank()) {
            entity.setName(this.name);
        }
        if(this.sort != null) {
            entity.setSort(this.sort);
        }
        entity.setNotes(this.notes);
        entity.setLast_modified(now);
    }

    @Override
    public String toString() {
        return "CategoryDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", notes='" + notes + '\'' +
                ", enabled=" + enabled +
                ", images='" + images + '\'' +
                ", sort=" + sort +
                ", parentID=" + parentID +
                ", createdDate=" + createdDate +
                ", deletedDate=" + deletedDate +
                ", lastModified=" + lastModified +
                ", new_image=" + new_image +
                '}';
    }

    public Integer getSort() {
        return sort != null ? sort : 0;
    }
}
