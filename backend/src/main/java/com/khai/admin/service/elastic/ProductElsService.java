//package com.khai.admin.service.elastic;
//
//import com.khai.admin.entity.Product;
//import com.khai.admin.repository.elasticsearch.ProductElsRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//public class ProductElsService {
//    private final ProductElsRepository productElsRepository;
//
//    @Autowired
//    public ProductElsService(ProductElsRepository productElsRepository) {
//        this.productElsRepository = productElsRepository;
//    }
//
//    public void save(Product p) {
//        productElsRepository.save(p);
//    }
//
//    public Product findById(UUID id) {
//        Optional<Product> productOpt = productElsRepository.findById(id);
//        if (productOpt.isEmpty())
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found");
//
//        return productOpt.get();
//    }
//}
