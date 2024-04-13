package com.khai.admin.controller;

import com.khai.admin.dto.Product.BulkUpdateSellProduct;
import com.khai.admin.dto.Product.ProductBarcode;
import com.khai.admin.dto.Product.ProductDto;
import com.khai.admin.entity.Product;
import com.khai.admin.service.HttpServices;
import com.khai.admin.service.ProductService;
import com.khai.admin.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    @PostMapping("/import/json")
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
    public ResponseEntity<ProductDto> createProduct(@RequestHeader Map<String, String> headers, @ModelAttribute ProductDto updatedProduct) {
        ProductDto result = productService.create(headers, updatedProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/insert/batch")
    public ResponseEntity<?> insertProducts(List<Product> data) {
        productService.batchInsertProducts(data);
        return new ResponseEntity<>("Import thành công", HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Map<String, Object>> getProducts(
            @RequestParam(defaultValue = "0") short page,
            @RequestParam(defaultValue = "5") short size,
            @RequestParam(defaultValue = "id:asc") String sort,
            @RequestParam(value = "q", required = false) String search,
            @RequestParam(defaultValue = "1", required = false) byte allowSale,
            @RequestParam(defaultValue = "alltime") String stockOutDate,
            @RequestParam(value = "stockoutStartDate", defaultValue = "") String stockoutStartDate,
            @RequestParam(value = "stockoutEndDate", defaultValue = "") String stockoutEndDate,
            @RequestParam(defaultValue = "0", required = false) byte onHandFilter,
            @RequestParam(defaultValue = "", required = false) String onHandFilterStr,
            @RequestParam(defaultValue = "", required = false) int[] branchIds,
            @RequestParam(defaultValue = "0", required = false) byte directSell,
            @RequestParam(defaultValue = "0", required = false) byte relateToChanel

    ) {
        List<Sort.Order> orders = this.httpServices.getSortOrders(sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        Map<String, Object> response = productService.getProducts(pageable, search, allowSale, stockOutDate, stockoutStartDate, stockoutEndDate, onHandFilter, onHandFilterStr, branchIds, directSell, relateToChanel);
        return ResponseEntity.status(200).body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") UUID id) {
        ProductDto response = productService.getProductInfo(id);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") UUID id) {
        productService.deleteById(id);
        return ResponseEntity.ok("Xóa sản phẩm thành công");
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") UUID id, @ModelAttribute ProductDto product) {
        ProductDto updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @PutMapping("/sellstatus/{id}")
    public ResponseEntity<ProductDto> updateSellStatus(@RequestHeader Map<String, String> headers, @PathVariable("id") UUID id, @RequestBody ProductDto productDto) {
        String apiKey = headers.get("x-api-key");

        ProductDto res = productService.updateSellStatus(id, productDto);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/sellstatus/bulk")
    public ResponseEntity<String> bulkUpdateSellStatus(@RequestBody BulkUpdateSellProduct request) {
        productService.bulkUpdateSellStatus(request.getIds(), request.isSellStatus());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/print/barcode")
    public ResponseEntity<List<ProductBarcode>> printBarcode(@RequestBody UUID[] ids) {
        List<ProductBarcode> data = productService.printBarcode(ids);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }


}
