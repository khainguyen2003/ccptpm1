package com.khai.admin.dto.Product;

import com.khai.admin.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSumary {
    private UUID id;

    private String name;

    private String thumb;

    private double sell_price;

    private boolean isDeleted;

    private String code;

    private boolean isIsDraft;

    private boolean isPublish;

    private String slug;

    private String product_type;

    public void fromEntity(Product entity) {
        this.id = entity.getId();
        this.
    }
}
