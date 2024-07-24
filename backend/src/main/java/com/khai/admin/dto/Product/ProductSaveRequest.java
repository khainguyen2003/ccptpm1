package com.khai.admin.dto.Product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khai.admin.dto.category.CategoryRequest;
import com.khai.admin.dto.user.UserViewDto;
import com.khai.admin.entity.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class ProductSaveRequest {
    private String name;
    private String desc;
    private List<String> old_images;
    private MultipartFile thumb;
    private String new_images;
    private Boolean deleted;
    private Boolean isStopCell;
    private Boolean isDirectCell;
    private Boolean isDraft;
    private Boolean isPublished;
    private String weight;
    private String code;
    private Float importPrice;
    private Float sellPrice;
    private String attrs;
    private Map<String, String> variations;
    private String categoryId;
    private Integer quantity;

    public ProductSaveRequest() {

    }

    public void applyToProduct(Product product) {
        ObjectMapper mapper = new ObjectMapper();
        if(!this.getName().isBlank()) {
            product.setName(name);
        }
        if(this.code != null && !this.code.isBlank()) {
            product.setCode(code);

        }
        product.setDescription(desc);
        if(this.weight != null) {
            product.setWeight(weight);

        }
        if(this.isDraft != null ) {
            product.setDraft(this.isDraft);
        }
        if(this.isDirectCell != null ) {
            product.setDirectCell(isDirectCell);
        }
        if(this.sellPrice != null ) {
            product.setSell_price(sellPrice);
        }
        if(this.importPrice != null ) {
            product.setImport_price(importPrice);
        }
        if(this.quantity != null) {
            product.setQuantity(quantity);
        }
    }
}
