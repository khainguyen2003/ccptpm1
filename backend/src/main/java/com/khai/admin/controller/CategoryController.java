package com.khai.admin.controller;

import com.khai.admin.entity.Category;
import com.khai.admin.repository.CategoryRepository;
import com.khai.admin.service.CategoryService;
import com.khai.admin.service.HttpServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CrossOrigin("*")
@RestController
@RequestMapping("/test/api/admin/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final HttpServices httpServices;

    @Autowired
    public CategoryController(
        CategoryService categoryService,
        CategoryRepository categoryRepository,
        HttpServices httpServices
    ) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
        this.httpServices = httpServices;
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category result = categoryService.create(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    @GetMapping
    public ResponseEntity<Map<String, Object>> getCategories(
            @RequestParam(required = false) String name,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(defaultValue = "0") short page,
            @RequestParam(defaultValue = "5") short size,
            @RequestParam(defaultValue = "id:asc") String sort
    ) {
        List<Sort.Order> orders = this.httpServices.getSortOrders(sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        Map<String, Object> response = categoryService.getCategories(name, pageable);
        return ResponseEntity.status(200).body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") int id) {
        Category response = categoryService.getCategoryById(id);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTutorial(@PathVariable("id") int id) {
        categoryService.deleteById(id);
        return ResponseEntity.ok("Xóa danh mục thành công");
    }
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateTutorial(@PathVariable("id") int id, @RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(updatedCategory);
    }


}
