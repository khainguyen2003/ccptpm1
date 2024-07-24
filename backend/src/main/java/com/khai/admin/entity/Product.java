package com.khai.admin.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khai.admin.dto.Product.ProductSaveRequest;
import com.khai.admin.dto.Product.ProductDto;
import com.khai.admin.dto.user.UserProfileDto;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * Lớp lưu trữ thông tin chung về sản phẩm mà doanh nghiệp đang kinh doanh
 */
@Entity
@Table(name = "tblproduct")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "product_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
//@Document(indexName = "product")
public class Product implements Serializable {
    @Id
//    @Field(type = FieldType.Keyword)
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    protected UUID id;
    @Column(name = "product_name", nullable = false)
    protected String name;
    @Column(name = "product_desc", length = 500)
    protected String description;
    protected String product_thumb;
    @Column(name = "product_images")
    protected List<String> images;

    @Column(name = "product_created_date")
    protected Date createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_created_id", referencedColumnName = "user_id")
    @JsonProperty("created_by")
    protected User shop;
    @Column(name = "product_lastmodified")
    protected Date lastmodified;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_modified_by", referencedColumnName = "user_id")
    @JsonProperty("last_modified_by")
    protected User lastModifiedBy;
    protected double import_price;
    protected double sell_price;
    @Column(name = "product_deleted", columnDefinition = "BIT(1) DEFAULT b'1'")
    protected boolean deleted;
    // Có ngừng kinh doanh không
    @Column(columnDefinition = "bit default b'0'")
    protected boolean isStopCell;
    // Có bán trục tiếp không
    @Column(columnDefinition = "bit default b'1'")
    protected boolean isDirectCell;
    // <trọng lượng>:<đơn vị tính>
    protected String weight;
    @Column(name = "product_code", length = 30)
    protected String code;
    @Column(name = "product_rate", columnDefinition = "DECIMAL(3,1) default 4.5")
    protected float rate;
    // Thêm tên class_thuộc tính khi cần lấy thuộc tính trả về frontend, nếu không thì là thuộc tính không trả về
    // Đây có phải là bản nháp không
    @Column(columnDefinition = "BIT DEFAULT b'1'")
    protected boolean isDraft;
    // Đã được publish lên người dùng chưa
    @Column(columnDefinition = "BIT DEFAULT b'0'")
    protected boolean isPublish;
    // Slug để tạo liên kết đến sản phẩm
    protected String slug;

    protected String product_type;
    @Column(columnDefinition = "json")
    protected String attrs;

    protected int quantity;

    // pessimitic locking
    @Version
    protected Long version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "pc_id", nullable = true)
    protected Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    protected List<WorkplaceDetail> wpd;
}
