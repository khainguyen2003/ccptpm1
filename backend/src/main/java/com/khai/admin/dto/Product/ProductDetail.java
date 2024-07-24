package com.khai.admin.dto.Product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khai.admin.dto.category.CategoryViewDto;
import com.khai.admin.dto.user.UserViewDto;
import com.khai.admin.entity.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ProductDetail {
    protected UUID id;
    protected String name;
    protected String description;
    protected String product_thumb;
    protected List<String> images;
    protected Date createdDate;
    protected UserViewDto shop;
    protected Date lastModified;
    protected UserViewDto lastModifiedBy;
    protected double import_price;
    protected double sell_price;
    protected boolean isDeleted;
    // Có ngừng kinh doanh không
    protected boolean isStopCell;
    // Có bán trục tiếp không
    protected boolean isDirectCell;
    // <trọng lượng>:<đơn vị tính>
    protected String weight;
    protected String code;
    protected float rate;
    // Thêm tên class_thuộc tính khi cần lấy thuộc tính trả về frontend, nếu không thì là thuộc tính không trả về
    // Đây có phải là bản nháp không
    protected boolean isDraft;
    // Đã được publish lên người dùng chưa
    protected boolean isPublish;
    protected String slug;

    protected String product_type;
    protected String attrs;
    protected int quantity;
//    protected List<WorkplaceDetail> wpd;
    protected List<Variation> variations;
    protected CategoryViewDto category;

    public void fromEntity(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.images = product.getImages();
        UserViewDto creator = new UserViewDto();
        creator.fromEntity(product.getShop());
        this.shop = creator;
        this.isDeleted = product.isDeleted();
        this.isStopCell = product.isStopCell();
        this.isDirectCell = product.isDirectCell();
        this.weight = product.getWeight();
        this.code = product.getCode();
        this.import_price = product.getImport_price();
        this.sell_price = product.getSell_price();
        this.createdDate = product.getCreatedDate();
        this.lastModified = product.getLastmodified();
        UserViewDto user_modified = new UserViewDto(product.getLastModifiedBy());
        this.lastModifiedBy = user_modified;
        this.rate = product.getRate();
        this.isDraft = product.isDraft();
        this.isPublish = product.isPublish();
        this.slug = product.getSlug();
        this.product_type = product.getProduct_type();
        this.quantity = product.getQuantity();
//        this.variations = product.getVariations();
        if(product.getCategory() != null) {
            CategoryViewDto categoryViewDto = new CategoryViewDto(product.getCategory());
            this.category = categoryViewDto;
        }
//        this.wpd = product.getWpd().stream().map(item -> {
//            Wp
//        })
    }

    public void applyToEntity(Product entity) {
        entity.setId(this.id);
        entity.setName(this.name);
        entity.setDescription(this.description);
        entity.setImages(this.images);
        entity.setDeleted(this.isDeleted);
        entity.setStopCell(this.isStopCell);
        entity.setDirectCell(this.isDirectCell);
        entity.setWeight(this.weight);
        entity.setCode(this.code);
        entity.setImport_price(this.import_price);
        entity.setSell_price(this.sell_price);
        entity.setCreatedDate(this.createdDate);
        entity.setLastmodified(this.lastModified);
        entity.setRate(this.rate);
        entity.setDraft(this.isDraft);
        entity.setPublish(this.isPublish);
        entity.setSlug(this.slug);
        entity.setProduct_type(this.product_type);
        entity.setAttrs(this.attrs);
        entity.setQuantity(this.quantity);
//        entity.setVariations(this.variations);
    }
}
