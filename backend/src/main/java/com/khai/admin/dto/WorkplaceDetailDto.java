package com.khai.admin.dto;

import com.khai.admin.entity.Product;
import com.khai.admin.entity.Workplace;
import com.khai.admin.entity.WorkplaceDetail;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkplaceDetailDto {
    private Product product;
    private Workplace workplace;
    private int product_quantity;
    private int product_price;

    public void fromEntity(WorkplaceDetail entity) {
        this.product = entity.getProduct();
        this.workplace = entity.getWorkplace();
        this.product_quantity = entity.getProduct_quantity();
        this.product_price = entity.getProduct_price();
    }

    public void applyToEntity(WorkplaceDetail entity) {
        entity.setProduct(this.product);
        entity.setWorkplace(this.workplace);
        entity.setProduct_quantity(this.product_quantity);
        entity.setProduct_price(this.product_price);
    }
}
