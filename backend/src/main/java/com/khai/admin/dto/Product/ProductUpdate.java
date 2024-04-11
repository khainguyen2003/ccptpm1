package com.khai.admin.dto.Product;

import com.khai.admin.dto.category.CategoryViewDto;
import com.khai.admin.dto.user.UserViewDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public class ProductUpdate {
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
}
