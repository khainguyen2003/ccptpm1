package com.khai.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tblpc")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pc_id")
    private UUID id;
    @Column(name = "pc_name")
    private String name;
    @Column(name = "pc_notes")
    private String notes;
    @Column(name = "pc_deleted", columnDefinition = "BIT(1) DEFAULT b'0'")
    private boolean deleted;
    @Column(name = "pc_enabled", columnDefinition = "BIT(1) DEFAULT b'0'")
    private boolean enabled;
    @Column(name = "pc_deleted_date")
    private Date deleted_date;
    @Column(name = "pc_last_modified")
    private Date last_modified;
    @Column(name = "pc_created_date")
    private Date created_date;
    @Column(name = "pc_images")
    private String images;
    // Vị trí suất hiện của category khi hiển thị các category
    @Column(name = "pc_sort")
    private int sort;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<Category> childCategories;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Product> products;

    public void applyToCategory(Category category) throws Exception {
        if(category.getName() != null) {
            category.setName(this.name);
        } else {
            throw new Exception("Tên loại sản phẩm không được bỏ trống");
        }
        category.setNotes(this.getNotes());
        Date last_modified = new Date();
        category.setLast_modified(last_modified);
        category.setImages(images);
    }


}
