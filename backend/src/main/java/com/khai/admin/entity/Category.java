package com.khai.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tblpc")
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pc_id")
    private int id;
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

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
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
