package com.khai.admin.controller;

import com.khai.admin.entity.Product;
import com.khai.admin.service.ProductService;
import com.khai.admin.util.Utilities;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.awt.print.Printable;
import java.io.IOException;
import java.util.List;

@RestController("/api/admin/products")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

//    public ResponseEntity<Page<Product>> getProducts()
    @PostMapping("/import-json")
    public ResponseEntity<String> importJsonData(@RequestParam("file") MultipartFile jsonData) {
        try {
            List<Product> productData = Utilities.convertJSONToEntity(jsonData);
            productService.importData(productData);
            return ResponseEntity.ok("Import thành công!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi import dữ liệu từ file JSON");
        }
    }

    @GetMapping("/products")
    public ResponseEntity<String> getProducts(@RequestParam("p") int page) {
        return null;
    }
}
