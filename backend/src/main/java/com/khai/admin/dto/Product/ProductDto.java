package com.khai.admin.dto;

import com.khai.admin.dto.category.CategoryViewDto;
import com.khai.admin.dto.user.UserProfileDto;
import com.khai.admin.dto.user.UserViewDto;
import com.khai.admin.entity.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ProductDto {
    private int id;
    private String name;
    private String desc;
    private String images;
    private Date createdDate;
    private UserViewDto createdBy;
    private boolean deleted;
    private boolean stopCell;
    private boolean isDirectCell;
    private String weight;
    private String code;
    private float rate;
    private String attr;
    private CategoryViewDto category;
    private List<WorkplaceDetailDto> wpd;

    public ProductDto() {
    }

    public ProductDto(Product product) {
        this.id = product.getId();
        if(!product.getName().isBlank()) {
            this.name = product.getName();
        }
        this.desc = product.getDescription();
        this.images = product.getImages();
        this.deleted = product.isDeleted();
        this.stopCell = product.isStopCell();
        this.isDirectCell = product.isDirectCell();
        this.weight = product.getWeight();
        this.code = product.getCode();
        this.rate = product.getRate();
        this.attr = product.getAttr();
        this.category = new CategoryViewDto(product.getCategory());
    }

    private void loadFromProduct(Product product) {
        this.id = product.getId();
        if(!product.getName().isBlank()) {
            this.name = product.getName();
        }
        this.desc = product.getDescription();
        this.images = product.getImages();
        this.deleted = product.isDeleted();
        this.stopCell = product.isStopCell();
        this.isDirectCell = product.isDirectCell();
        this.weight = product.getWeight();
        this.code = product.getCode();
        this.rate = product.getRate();
        this.attr = product.getAttr();
        this.category = new CategoryViewDto(product.getCategory());
    }

    public void updateToProduct(Product product) {
        product.setId(id);
        if(!this.getName().isBlank()) {
            product.setName(name);
        }
        product.setCode(code);
        product.setDescription(desc);
        product.setRate(rate);
        product.setAttr(attr);
        product.setCode(code);
        product.setWeight(weight);
        product.setDirectCell(isDirectCell);
        product.setCategory(category.convertToCategory());
    }
}
