package com.khai.admin.dto.Product;

import com.khai.admin.dto.category.CategoryViewDto;
import com.khai.admin.dto.user.UserViewDto;
import com.khai.admin.entity.User;

import java.util.Date;

public record ProductRecord(int id, String name, String description, String images, Date createdDate, User creator, boolean deleted, boolean isStopCell, boolean isDirectCell, String weight, String code, float rate, String attr, CategoryViewDto category) {
}
