package com.khai.admin.controller;
import com.khai.admin.dto.CustomerDto;
import com.khai.admin.dto.ProductDto;
import com.khai.admin.entity.*;
import com.khai.admin.service.HttpServices;
import com.khai.admin.service.CustomerService;
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
@RequestMapping("/test/api/admin/customers")
public class CustomerController {
    private CustomerService customerService;
    private final HttpServices httpServices;

    @Autowired
    public  CustomerController(
            CustomerService customerServiceService,
            HttpServices httpServices
    ) {
        this.customerService = customerService;
        this.httpServices = httpServices;
    }
    @PostMapping("/import-json")
    public ResponseEntity<String> importJsonData(@RequestParam("file") MultipartFile jsonData) {
        try {
            List<Customer> customerData = Utilities.convertJSONToEntity(jsonData);
            customerService.importData(customerData);
            return ResponseEntity.ok("Import thành công!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi import dữ liệu từ file JSON");
        }
    }
    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestHeader Map<String, String> headers, @RequestBody Customer customer) {
        CustomerDto result = customerService.create(headers, customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    @GetMapping
    public ResponseEntity<Map<String, Object>> getCustomers(
            @RequestParam(required = false) String name,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(defaultValue = "0") short page,
            @RequestParam(defaultValue = "5") short size,
            @RequestParam(defaultValue = "id:asc") String sort
    ) {
        List<Sort.Order> orders = this.httpServices.getSortOrders(sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        Map<String, Object> response = customerService.getCustomers(name, pageable);
        return ResponseEntity.status(200).body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("id") int id) {
        CustomerDto response = customerService.getCustomerInfo(id);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") int id) {
        customerService.deleteById(id);
        return ResponseEntity.ok("Xóa sản phẩm thành công");
    }
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateustomer(@PathVariable("id") int id, @RequestBody Customer customer) {
        CustomerDto updatedCustomer = customerService.updateCustomer(id, customer);
        return ResponseEntity.ok(updatedCustomer);
    }

    @PutMapping("/sellstatus/{id}")
    public ResponseEntity<CustomerDto> updateSellStatus(@PathVariable("id") int id, @RequestBody CustomerDto customerDto) {
        CustomerDto res = customerService.updateSellStatus(id, customerDto);
        return ResponseEntity.ok(res);
    }
}
