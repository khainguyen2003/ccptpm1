package com.khai.admin.dto;

import com.khai.admin.dto.user.UserView;
import com.khai.admin.entity.Category;
import com.khai.admin.entity.Product;
import com.khai.admin.entity.WorkplaceDetail;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ProductView {
    private int id;
    private String name;
    private String desc;
    private String images;
    private Date createdDate;
    private UserView createdBy;
    private boolean deleted;
    private boolean stopCell;
    private boolean isDirectCell;
    private String weight;
    private String code;
    private float rate;
    private String attr;
    private Category category;
    private List<WorkplaceDetail> wpd;

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
        this.category = product.getCategory();
    }
}
