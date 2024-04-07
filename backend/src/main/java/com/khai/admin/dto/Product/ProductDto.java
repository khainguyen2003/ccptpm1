package com.khai.admin.dto.Product;

import com.khai.admin.dto.workplace.WorkplaceDetailDto;
import com.khai.admin.dto.category.CategoryViewDto;
import com.khai.admin.dto.user.UserViewDto;
import com.khai.admin.entity.Category;
import com.khai.admin.entity.Product;
import com.khai.admin.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
public class ProductDto implements Serializable {
    private int id;
    private String name;
    private String description;
    private List<String> images;
    private MultipartFile[] files;
    private Date createdDate;
    private UserViewDto creator;
    private boolean deleted;
    private boolean isStopCell;
    private boolean isDirectCell;
    private String weight;
    private String code;
    private float rate;
    private String attr;
    private CategoryViewDto category;
//    private List<WorkplaceDetailDto> wpd;

    public ProductDto() {
    }

    /*
    For a projection class to work in tandem with a repository interface, the parameter names of its constructor must match the properties of the root entity class.

    We must also define equals and hashCode implementations; they allow Spring Data to process projection objects in a collection.
     */
    public ProductDto(int id, String name, String description, List<String> images, Date createdDate, User creator, boolean deleted, boolean isStopCell, boolean isDirectCell, String weight, String code, float rate, String attr, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.images = images;
        this.createdDate = createdDate;
        this.creator = new UserViewDto(creator);
        this.deleted = deleted;
        this.isStopCell = isStopCell;
        this.isDirectCell = isDirectCell;
        this.weight = weight;
        this.code = code;
        this.rate = rate;
        this.attr = attr;
        this.category = new CategoryViewDto(category);
//        this.wpd = wpd;
    }

    public ProductDto(Product product) {
        this.id = product.getId();
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
        if(product.getCategory() != null) {
            this.category = new CategoryViewDto(product.getCategory());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return description;
    }

    public void setDesc(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public UserViewDto getCreatedBy() {
        return creator;
    }

    public void setCreatedBy(UserViewDto createdBy) {
        this.creator = createdBy;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isStopCell() {
        return isStopCell;
    }

    public void setStopCell(boolean stopCell) {
        this.isStopCell = stopCell;
    }

    public boolean isDirectCell() {
        return isDirectCell;
    }

    public void setDirectCell(boolean directCell) {
        isDirectCell = directCell;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public CategoryViewDto getCategory() {
        return category;
    }

    public void setCategory(CategoryViewDto category) {
        this.category = category;
    }

//    public List<WorkplaceDetailDto> getWpd() {
//        return wpd;
//    }
//
//    public void setWpd(List<WorkplaceDetailDto> wpd) {
//        this.wpd = wpd;
//    }


    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserViewDto getCreator() {
        return creator;
    }

    public void setCreator(UserViewDto creator) {
        this.creator = creator;
    }

    public void applyToProduct(Product product) {
        product.setId(id);
        if(!this.getName().isBlank()) {
            product.setName(name);
        }
        product.setCode(code);
        product.setDescription(description);
        product.setRate(rate);
        product.setAttr(attr);
        product.setCode(code);
        product.setWeight(weight);
        product.setDirectCell(isDirectCell);
        product.setImages(this.images);
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
