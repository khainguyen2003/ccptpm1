package com.khai.admin.dto.Product;

import com.khai.admin.dto.workplace.WorkplaceDetailDto;
import com.khai.admin.dto.category.CategoryViewDto;
import com.khai.admin.dto.user.UserViewDto;
import com.khai.admin.entity.Category;
import com.khai.admin.entity.Product;
import com.khai.admin.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDto implements Serializable {
    private UUID id;
    private String name;
    private String description;
    private String thumb;
    private List<String> images;
    private Date createdDate;
    private UserViewDto creator;
    private boolean deleted;
    private boolean isStopCell;
    private boolean isDirectCell;
    private String weight;
    private String code;
    private float rate;
    private String attr;
    private boolean isPulished;
    private boolean isDraft;
    private String slug;
    private int quantity;
    private float import_price;
    private float sell_price;
    private CategoryViewDto category;
//    private List<WorkplaceDetailDto> wpd;


    public ProductDto(Product product) {
        this.id = product.getId();
        if(!product.getName().isBlank()) {
            this.name = product.getName();
        }
        this.description = product.getDescription();
        this.images = product.getImages();
        this.thumb = product.getProduct_thumb();
        this.createdDate = product.getCreatedDate();
        this.creator = new UserViewDto(product.getShop());
        this.attr = product.getAttrs();

        this.deleted = product.isDeleted();
        this.isStopCell = product.isStopCell();
        this.isDirectCell = product.isDirectCell();
        this.weight = product.getWeight();
        this.code = product.getCode();
        this.rate = product.getRate();
        this.isPulished = product.isPublish();
        this.isDraft = product.isDraft();
        this.slug = product.getSlug();
        if(product.getCategory() != null) {
            this.category = new CategoryViewDto(product.getCategory());
        }
    }

    public void applyToProduct(Product product) {
        if(!this.getName().isBlank()) {
            product.setName(name);
        }
        product.setCode(code);
        product.setDescription(description);
        product.setRate(rate);
        product.setCode(code);
        product.setWeight(weight);
        product.setDirectCell(isDirectCell);
        product.setImages(this.images);
        product.setSlug(this.slug);
        product.setDraft(isDraft);
        product.setProduct_thumb(thumb);
        if(category != null) {
            product.setCategory(category.convertToCategory());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
