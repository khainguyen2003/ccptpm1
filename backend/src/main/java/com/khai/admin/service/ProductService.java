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
        product.setCreatedDate(now);
//        product.setLast_modified(now);

        return productRepository.save(product);
    }

    public Map<String, Object> getProducts(String name, Pageable pageable) {
        try {
            List<Product> products = new ArrayList<>();
            Page<Product> pageProducts;
//            if(name == null) {
                pageProducts = productRepository.findAll(pageable);
//            }
//            } else {
//                pageProducts = productRepository.findByNameContaining(name, pageable);
//            }
            products = pageProducts.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("products", products);
            response.put("curPage", pageProducts.getNumber());
            response.put("totalPage", pageProducts.getTotalPages());
            response.put("totalElements", pageProducts.getTotalElements());
            response.put("pageSize", pageProducts.getSize());
            response.put("numberOfElements", pageProducts.getNumberOfElements());
            return response;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Thực hiện không thành công. Vui lòng thử lại sau");
        }
    }

    public void deleteById(int id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Thực hiện không thành công. Vui lòng thử lại sau");
        }
    }

    public Product updateProduct(int id, Product updatedProduct) {
        try {
            Optional<Product> optProduct = productRepository.findById(id);
            if(optProduct.isPresent()) {
                Product product = optProduct.get();
                updatedProduct.applyToProduct(product);
                productRepository.save(product);
                return product;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy danh mục có id " + id);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
