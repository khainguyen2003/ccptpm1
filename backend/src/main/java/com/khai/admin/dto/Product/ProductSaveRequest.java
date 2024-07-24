package com.khai.admin.dto.Product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khai.admin.dto.category.CategoryViewDto;
import com.khai.admin.dto.user.UserViewDto;
import com.khai.admin.entity.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ProductCreateRequest {
    private String name;
    private String description;
    private List<String> old_images;
    private MultipartFile thumb;
    private MultipartFile[] new_images;
    private UserViewDto creator;
    private boolean deleted;
    private boolean isStopCell;
    private boolean isDirectCell;
    private boolean isDraft;
    private boolean isPublished;
    private String weight;
    private String code;
    private float import_price;
    private float sell_price;
    private String attrs;
    private Map<String, String> variations;
    private String type;
    private int quantity;

    public ProductCreateRequest() {

    }

    public void applyToProduct(Product product) {
        ObjectMapper mapper = new ObjectMapper();
        if(!this.getName().isBlank()) {
            product.setName(name);
        }
        product.setCode(code);
        product.setDescription(description);
        product.setCode(code);
        product.setWeight(weight);
        product.setDirectCell(isDirectCell);
        product.setDirectCell(isDirectCell);
        product.setDraft(this.isDraft);
        product.setSell_price(sell_price);
        product.setImport_price(import_price);
        product.setProduct_type(type);
        product.setQuantity(quantity);
    }
}
