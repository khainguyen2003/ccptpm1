package com.khai.admin.controller;

import com.khai.admin.dto.ProductDto;
import com.khai.admin.entity.Category;
import com.khai.admin.entity.Product;
import com.khai.admin.service.HttpServices;
import com.khai.admin.service.ProductService;
import com.khai.admin.util.Utilities;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Printable;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/test/api/admin/products")
public class ProductController {
    private ProductService productService;
    private final HttpServices httpServices;

    @Autowired
    public ProductController(
            ProductService productService,
            HttpServices httpServices
    ) {
        this.productService = productService;
        this.httpServices = httpServices;
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

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestHeader Map<String, String> headers, @RequestBody Product product) {
        ProductDto result = productService.create(headers, product);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    @GetMapping
    public ResponseEntity<Map<String, Object>> getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(defaultValue = "0") short page,
            @RequestParam(defaultValue = "5") short size,
            @RequestParam(defaultValue = "id:asc") String sort
    ) {
        List<Sort.Order> orders = this.httpServices.getSortOrders(sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        Map<String, Object> response = productService.getProducts(name, pageable);
        return ResponseEntity.status(200).body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") int id) {
        ProductDto response = productService.getProductInfo(id);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") int id) {
        productService.deleteById(id);
        return ResponseEntity.ok("Xóa sản phẩm thành công");
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") int id, @RequestBody Product product) {
        ProductDto updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @PutMapping("/sellstatus/{id}")
    public ResponseEntity<ProductDto> updateSellStatus(@RequestHeader Map<String, String> headers, @PathVariable("id") int id, @RequestBody ProductDto productDto) {
        String apiKey = headers.get("x-api-key");

        ProductDto res = productService.updateSellStatus(id, productDto);
        return ResponseEntity.ok(res);
    }
}
