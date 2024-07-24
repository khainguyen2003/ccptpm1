package com.khai.admin.controller;

import com.khai.admin.dto.PaginationResponse;
import com.khai.admin.dto.Product.*;
import com.khai.admin.entity.Product;
import com.khai.admin.projections.ProductPreview;
import com.khai.admin.service.HttpServices;
import com.khai.admin.service.ProductService;
import com.khai.admin.util.HttpUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/admin/products")
public class ProductController {
    private ProductService productService;
    private final HttpServices httpServices;
//    private final ProductElsService productElsService;

    @Autowired
    public ProductController(
            ProductService productService,
            HttpServices httpServices
    ) {
        this.productService = productService;
        this.httpServices = httpServices;
    }

//    public ResponseEntity<Page<Product>> getProducts()
    @PostMapping("/import/json")
    public ResponseEntity<String> importJsonData(@RequestParam("file") MultipartFile jsonData) {
        try {
            List<Product> productData = HttpUtilities.convertJSONToEntity(jsonData);
            productService.importData(productData);
            return ResponseEntity.ok("Import thành công!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi import dữ liệu từ file JSON");
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ProductDto> createProduct(
            @RequestHeader Map<String, String> headers,
            @ModelAttribute ProductSaveRequest updatedProduct
    ) {
        ProductDto result = productService.create(headers, updatedProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public ResponseEntity<PaginationResponse> getProducts(
            @RequestParam(defaultValue = "0") short page,
            @RequestParam(defaultValue = "20") short size,
            @RequestParam(defaultValue = "id:asc") String sort,
            @RequestParam(value = "q", required = false) String search
    ) {
        List<Sort.Order> orders = this.httpServices.getSortOrders(sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        PaginationResponse response = productService.getProducts(pageable, search);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/shop/{shop}/drafts")
    public ResponseEntity<Map<String, Object>> getAllProductDrafts(
        @RequestParam(defaultValue = "0") short page,
        @RequestParam(defaultValue = "20") short size,
        @RequestParam(defaultValue = "id:asc") String sort,
        @RequestParam(value = "q", required = false) String search,
        @PathVariable("shop") UUID shopId
    ) {
        List<Sort.Order> orders = this.httpServices.getSortOrders(sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        Map<String, Object> response = productService.getAllProductDraft(shopId, pageable);
        return ResponseEntity.status(200).body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetail> getProduct(@PathVariable("id") UUID id) {
        ProductDetail response = productService.getProductInfo(id);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable("id") UUID id) {
        boolean isDel = productService.deleteById(id);
        if(isDel) {
            return new ResponseEntity<>(isDel, HttpStatus.OK);
        }
        return new ResponseEntity<>(isDel, HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") UUID id, @ModelAttribute ProductSaveRequest saveRequest) {
        ProductDto updatedProduct = productService.updateProduct(id, saveRequest);
        return ResponseEntity.ok(updatedProduct);
    }

    @PutMapping("/publish/{id}")
    public ResponseEntity<String> publishProduct(@PathVariable("id") UUID productId) {
        boolean pulishSuccess = productService.publishProduct(productId);
        return ResponseEntity.ok("Publish sản phẩm thành công");
    }

    @PutMapping("/sellstatus/{id}")
    public ResponseEntity<ProductDto> updateSellStatus(@RequestHeader Map<String, Object> headers, @PathVariable("id") UUID id, @RequestBody ProductDto productDto) {
//        String apiKey = headers.get("x-api-key");

        ProductDto res = productService.updateSellStatus(id, productDto);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/sellstatus/bulk")
    public ResponseEntity<String> bulkUpdateSellStatus(@RequestBody BulkUpdateSellProduct request) {
        productService.bulkUpdateSellStatus(request.getIds(), request.isSellStatus());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/print/barcode")
    public ResponseEntity<Page<ProductPreview>> printBarcode(
            @RequestBody List<UUID> ids,
            @RequestParam(defaultValue = "0") short page,
            @RequestParam(defaultValue = "20") short size,
            @RequestParam(defaultValue = "id:asc") String sort
    ) {
        List<Sort.Order> orders = this.httpServices.getSortOrders(sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        Page<ProductPreview> data = productService.printBarcode(ids, pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
