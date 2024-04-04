package com.khai.admin.dto.workplace;

import com.khai.admin.entity.Product;
import com.khai.admin.entity.Workplace;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

public class WorkplaceDetailDto implements Serializable {
    private Product product;
    private Workplace workplace;
    private int product_quantity;
    private int product_price;

    public WorkplaceDetailDto() {
    }

    public WorkplaceDetailDto(Product product, Workplace workplace, int product_quantity, int product_price) {
        this.product = product;
        this.workplace = workplace;
        this.product_quantity = product_quantity;
        this.product_price = product_price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Workplace getWorkplace() {
        return workplace;
    }

    public void setWorkplace(Workplace workplace) {
        this.workplace = workplace;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkplaceDetailDto that)) return false;
        return Objects.equals(product, that.product) && Objects.equals(workplace, that.workplace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, workplace);
    }
}
