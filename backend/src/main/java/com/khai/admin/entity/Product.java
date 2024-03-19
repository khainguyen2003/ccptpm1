package com.khai.admin.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Table(name = "tblproduct")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int id;
    @Column(name = "product_name")
    private String name;
    @Column(name = "product_desc", length = 500)
    private String description;
    @Column(name = "product_images")
    private String images;
    @CreatedDate
    @Column(name = "product_created_date")
    private Date createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_created_id", referencedColumnName = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    private boolean deleted;
    // Có ngừng kinh doanh không
    @Column(columnDefinition = "bit default 0")
    private boolean isStopCell;
    // Có bán trục tiếp không
    @Column(columnDefinition = "bit default 1")
    private boolean isDirectCell;
    // <trọng lượng>:<đơn vị tính>
    private String weight;
    @Column(name = "product_code", length = 30)
    private String code;
    @Column(name = "product_rate", columnDefinition = "DECIMAL(1,1) default 0")
    private float rate;
    // Các thuộc tính của sản phẩm
    private String attr;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "pc_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private WorkplaceDetail wpd;

    public void applyToProduct(Product product) {
        this.id = product.id;
        this.name = product.name;
        this.description = product.description;
        this.images = product.images;
        this.createdDate = product.createdDate;
        this.user = product.user;
        this.deleted = product.deleted;
        this.isStopCell = product.isStopCell;
        this.isDirectCell = product.isDirectCell;
        this.weight = product.weight;
        this.code = product.code;
        this.rate = product.rate;
        this.attr = product.attr;
        this.category = product.category;
    }
}
