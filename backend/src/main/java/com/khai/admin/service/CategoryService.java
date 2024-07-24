package com.khai.admin.service;

import com.khai.admin.dto.PaginationResponse;
import com.khai.admin.dto.category.CategoryDto;
import com.khai.admin.dto.category.CategoryRequest;
import com.khai.admin.entity.Category;
import com.khai.admin.exception.AlreadyExist;
import com.khai.admin.repository.jpa.CategoryRepository;
import com.khai.admin.request.FilterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final FileService fileService;

    @Autowired
    public CategoryService(
        CategoryRepository categoryRepository,
        FileService fileService
    ) {
        this.categoryRepository = categoryRepository;
        this.fileService = fileService;
    }

    @Transactional
    public CategoryDto create(CategoryRequest categorySaveRequest) {
            if(categorySaveRequest.name() == null || categorySaveRequest.name().isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tên danh mục không được bỏ trống");
            }
            if(categoryRepository.findByName(categorySaveRequest.name()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Thể loại đã tồn tại");
            }
            Date now = new Date();
            Category newCategory = new Category();
            if(categorySaveRequest.parentID() != null) {
                Category parent = categoryRepository.findById(categorySaveRequest.parentID()).orElseThrow();
                newCategory.setParent(parent);
            }
            newCategory.setCreated_date(now);
            newCategory.setLast_modified(now);
            newCategory.setName(categorySaveRequest.name());
            newCategory.setNotes(categorySaveRequest.notes());
            newCategory.setEnabled(categorySaveRequest.enabled());

            if(categorySaveRequest.new_image() != null && !categorySaveRequest.new_image().isEmpty()) {
                String new_img_path = this.fileService.uploadFileToSystem(categorySaveRequest.new_image(), this.fileService.getCategoryFolder());
                newCategory.setImages(new_img_path);
            }
            newCategory = categoryRepository.save(newCategory);
            return new CategoryDto(newCategory);

    }

    public Category getCategoryById(UUID id) {
        Optional<Category> optCategory = categoryRepository.findById(id);
        if(optCategory.isPresent()) {
            return optCategory.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tồn tại danh mục");
        }
    }
    public Category getCategoryByName(String name) {
        Optional<Category> optCategory = categoryRepository.findByName(name);
        if(optCategory.isPresent()) {
            return optCategory.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tồn tại danh mục");
        }
    }

    public List<Category> findCategoriesByName(String name) {
        List<Category> result = categoryRepository.findAllByName(name);
        return result;
    }

    public PaginationResponse getCategoriesByProcedure(FilterRequest filterRequest, Pageable pageable) {
        try {
            List<CategoryDto> categories;
            Page<Category> pageCategories;
            pageCategories = categoryRepository.findAll(pageable);
            categories = pageCategories.getContent().stream().map(category -> {
                CategoryDto item = new CategoryDto(category);
                return item;
            }).collect(Collectors.toList());
            PaginationResponse response = PaginationResponse.builder()
                    .totalElements(pageCategories.getTotalElements())
                    .items(categories)
                    .curPage(pageCategories.getNumber())
                    .pageSize(pageCategories.getSize())
                    .totalPage(pageCategories.getTotalPages())
                    .numberOfElements(pageCategories.getNumberOfElements())
                    .build();
            return response;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public PaginationResponse getCategoriesByCriteria(FilterRequest filterRequest, Pageable pageable) {
        try {
            List<CategoryDto> categories;
            Page<Category> pageCategories;
            pageCategories = categoryRepository.findAll(pageable);
            categories = pageCategories.getContent().stream().map(category -> {
                CategoryDto item = new CategoryDto(category);
                return item;
            }).collect(Collectors.toList());
            PaginationResponse response = PaginationResponse.builder()
                    .totalElements(pageCategories.getTotalElements())
                    .items(categories)
                    .curPage(pageCategories.getNumber())
                    .pageSize(pageCategories.getSize())
                    .totalPage(pageCategories.getTotalPages())
                    .numberOfElements(pageCategories.getNumberOfElements())
                    .build();
            return response;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Transactional
    public boolean deleteById(UUID id) {
        try {
            categoryRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false; // Thực thể không tồn tại
        }
    }

    @Transactional
    public CategoryDto updateCategory(UUID id, CategoryRequest updatedCategory) {
        if(updatedCategory.name() == null || updatedCategory.name().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tên danh mục không được bỏ trống");
        }
        Category category = getCategoryById(id);
        category.setName(updatedCategory.name());
        category.setNotes(updatedCategory.notes());
        category.setEnabled(updatedCategory.enabled());

        // Cập nhật ảnh
        if(updatedCategory.new_image() != null && !updatedCategory.new_image().isEmpty()) {
            String new_img_path = this.fileService.uploadFileToSystem(updatedCategory.new_image(), this.fileService.getCategoryFolder());
            category.setImages(new_img_path);
        }
        deleteCategoryImage(category);
        category = categoryRepository.save(category);
        return new CategoryDto(category);
    }

    @Transactional
    public boolean deleteCategoryImage(Category category) {
        if(category.getImages() != null && !category.getImages().isBlank()) {
            return this.fileService.deleteFileFromSystem(category.getImages());
        }
        return false;
    }
}
