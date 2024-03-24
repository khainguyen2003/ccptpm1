package com.khai.admin.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khai.admin.dto.ProductDto;
import com.khai.admin.dto.user.UserView;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.List;

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
    @ManyToOne
    @JoinColumn(name = "user_created_id", referencedColumnName = "user_id", nullable = false)
    @JsonProperty("created_by")
    private User creator;
    @Column(name = "user_deleted", columnDefinition = "BIT(1) DEFAULT b'1'")
    private boolean deleted;
    // Có ngừng kinh doanh không
    @Column(columnDefinition = "bit default b'0'")
    private boolean isStopCell;
    // Có bán trục tiếp không
    @Column(columnDefinition = "bit default b'1'")
    private boolean isDirectCell;
    // <trọng lượng>:<đơn vị tính>
    private String weight;
    @Column(name = "product_code", length = 30)
    private String code;
    @Column(name = "product_rate", columnDefinition = "DECIMAL(1,1) default 0")
    private float rate;
    // Các thuộc tính của sản phẩm
    private String attr;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "pc_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkplaceDetail> wpd;

    public void applyToProduct(Product product) {
        if(!product.getName().isBlank()) {
            this.name = product.getName();
        }
        this.description = product.getDescription();
        this.images = product.getImages();
        this.deleted = product.isDeleted();
        this.isStopCell = product.isStopCell();
        this.isDirectCell = product.isDirectCell();
        this.weight = product.getWeight();
        this.code = product.getCode();
        this.rate = product.getRate();
        this.attr = product.getAttr();
        this.category = product.getCategory();
    }

    public ProductDto toDto() {
        UserView createdBy = new UserView();
        createdBy.loadFromUser(creator);
        return new ProductDto(this);
    }
}
