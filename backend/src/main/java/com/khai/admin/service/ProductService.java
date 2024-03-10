package com.khai.admin.service;

import com.khai.admin.entity.Category;
import com.khai.admin.entity.Product;
import com.khai.admin.exception.AlreadyExist;
import com.khai.admin.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductInfo(int id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()) {
            return product.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm có id" + id);
    }
//    public List<Product> getProducts(Pageable pageable) {
//
//    }

    @Transactional
    public void importData(List<Product> data) {
        try {
            for (Product item : data) {
                System.out.println(item);
//                productRepository.save(item);
            }
        }catch (DataAccessException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public Product create(Product product) {
        Date now = new Date();
        if(productRepository.isExist(product.getName(), product.getCode()).isPresent()) {
            throw new AlreadyExist("Sản phẩm", product.getName());
        }
        product.setDeleted(false);
        product.setEnabled(true);
        category.setCreated_date(now);
        category.setLast_modified(now);

        return categoryRepository.save(category);
    }

    public Category getCategoryById(int id) {
        Optional<Category> optCategory = categoryRepository.findById(id);
        if(optCategory.isPresent()) {
            return (Category) optCategory.get();
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
