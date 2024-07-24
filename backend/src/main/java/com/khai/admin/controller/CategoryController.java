package com.khai.admin.controller;

import com.khai.admin.dto.PaginationResponse;
import com.khai.admin.dto.category.CategoryDto;
import com.khai.admin.dto.category.CategoryRequest;
import com.khai.admin.entity.Category;
import com.khai.admin.repository.jpa.CategoryRepository;
import com.khai.admin.request.FilterRequest;
import com.khai.admin.service.CategoryDynamicServices;
import com.khai.admin.service.CategoryService;
import com.khai.admin.service.HttpServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/admin/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final CategoryDynamicServices categoryDynamicServices;
    private final HttpServices httpServices;

    @Autowired
    public CategoryController(
        CategoryService categoryService,
        CategoryRepository categoryRepository,
        HttpServices httpServices,
        CategoryDynamicServices categoryDynamicServices
    ) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
        this.httpServices = httpServices;
        this.categoryDynamicServices = categoryDynamicServices;
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(CategoryRequest categorySaveRequest) {
        CategoryDto result = categoryService.create(categorySaveRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    @GetMapping("/v1")
    public ResponseEntity<PaginationResponse> getCategories(
            FilterRequest filterRequest,
            @RequestParam(defaultValue = "0") short pageNum,
            @RequestParam(defaultValue = "20") short pageSize,
            @RequestParam(defaultValue = "id:asc") String sort
    ) {
        List<Sort.Order> orders = this.httpServices.getSortOrders(sort);
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(orders));
        PaginationResponse response = categoryDynamicServices.findByCriteria(filterRequest, pageable);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/v2")
    public ResponseEntity<PaginationResponse> getCategoriesV2(
            FilterRequest filterRequest,
            @RequestParam(defaultValue = "0") short pageNum,
            @RequestParam(defaultValue = "20") short pageSize,
            @RequestParam(defaultValue = "id:asc") String sort
    ) {
        List<Sort.Order> orders = this.httpServices.getSortOrders(sort);
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(orders));
        PaginationResponse response = categoryDynamicServices.findByProcedure(filterRequest, pageable);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping
    public ResponseEntity<PaginationResponse> getCategoriesV3(
            FilterRequest filterRequest,
            @RequestParam(defaultValue = "0") short pageNum,
            @RequestParam(defaultValue = "20") short pageSize,
            @RequestParam(value = "sort", required = false) String sort
    ) {
        List<Sort.Order> orders = this.httpServices.getSortOrders(sort);
        PaginationResponse response = categoryDynamicServices.findByEntityManager(filterRequest, pageNum, pageSize, orders);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/find-name")
    public ResponseEntity<List<Category>> findCategoriesByName(@RequestParam(value = "name", defaultValue = "") String name) {
        return new ResponseEntity<>(this.categoryService.findCategoriesByName(name), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("id") UUID id) {
        CategoryDto response = new CategoryDto(categoryService.getCategoryById(id));
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable("id") UUID id) {
        boolean isDelSuccess = categoryDynamicServices.deleteById(id);
        if(isDelSuccess) {
            return ResponseEntity.ok(true);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(
        @PathVariable("id") UUID id,
        CategoryRequest categoryRequest
    ) {
        CategoryDto response = categoryService.updateCategory(id, categoryRequest);
        return ResponseEntity.ok(response);
    }


}
