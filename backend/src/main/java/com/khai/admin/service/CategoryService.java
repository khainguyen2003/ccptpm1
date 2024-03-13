package com.khai.admin.service;

import com.khai.admin.entity.Category;
import com.khai.admin.exception.AlreadyExist;
import com.khai.admin.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category create(Category category) {
        Date now = new Date();
        if(categoryRepository.findByName(category.getName()).isPresent()) {
            throw new AlreadyExist("Thể loại", category.getName());
        }
        category.setDeleted(false);
        category.setEnabled(true);
        category.setCreated_date(now);
        category.setLast_modified(now);
        System.out.println(category);

        return categoryRepository.save(category);
    }

    public Category getCategoryById(int id) {
        Optional<Category> optCategory = categoryRepository.findById(id);
        if(optCategory.isPresent()) {
            return optCategory.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy danh mục có id " + id);
        }
    }

    public Map<String, Object> getCategories(String name, Pageable pageable) {
        try {
            List<Category> categories = new ArrayList<>();
            Page<Category> pageCategories;
            if(name == null) {
                pageCategories = categoryRepository.findAll(pageable);
            } else {
                pageCategories = categoryRepository.findByNameContaining(name, pageable);
            }
            categories = pageCategories.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("categories", categories);
            response.put("curPage", pageCategories.getNumber());
            response.put("totalPage", pageCategories.getTotalPages());
            response.put("totalElements", pageCategories.getTotalElements());
            response.put("pageSize", pageCategories.getSize());
            response.put("numberOfElements", pageCategories.getNumberOfElements());
            return response;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Thực hiện không thành công. Vui lòng thử lại sau");
        }
    }

    public void deleteById(int id) {
        try {
            categoryRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Thực hiện không thành công. Vui lòng thử lại sau");
        }
    }

    public Category updateCategory(int id, Category updatedCategory) {
        try {
            Optional<Category> optCategory = categoryRepository.findById(id);
            if(optCategory.isPresent()) {
                Category category = optCategory.get();
                updatedCategory.applyToCategory(category);
                categoryRepository.save(category);
                return category;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy danh mục có id " + id);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
