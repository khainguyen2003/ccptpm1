package com.khai.admin.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khai.admin.dto.Product.ProductDto;
import com.khai.admin.dto.user.UserProfileDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tblproduct")
//@NamedEntityGraph(
//        name = "Product.list",
//        attributeNodes = {
//                @NamedAttributeNode(value = "creator", subgraph = "creator"),
//                @NamedAttributeNode(value = "category", subgraph = "category")
//        },
//        subgraphs = {
//                @NamedSubgraph(name = "creator", attributeNodes = {
//                        @NamedAttributeNode("id"),
//                        @NamedAttributeNode("firstname"),
//                        @NamedAttributeNode("lastname")
//                }),
//                @NamedSubgraph(name = "category", attributeNodes = {
//                        @NamedAttributeNode("id"),
//                        @NamedAttributeNode("name")
//                })
//        }
//)
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private UUID id;
    @Column(name = "product_name")
    private String name;
    @Column(name = "product_desc", length = 500)
    private String description;
    @Column(name = "product_images")
    private List<String> images;
    @CreatedDate
    @Column(name = "product_created_date")
    private Date createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
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

    // pessimitic locking
    @Version
    private Long version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "pc_id", nullable = true)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
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
        UserProfileDto createdBy = new UserProfileDto();
        createdBy.loadFromUser(creator);
        return new ProductDto(this);
    }
}
