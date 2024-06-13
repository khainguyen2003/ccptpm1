package com.khai.admin.controller;

import com.khai.admin.entity.Provider;
import com.khai.admin.entity.User;
import com.khai.admin.service.HttpServices;
import com.khai.admin.service.ProviderService;
import com.khai.admin.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
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

@CrossOrigin("*")
@RestController
@RequestMapping("/test/api/admin/providers")
public class ProviderController {
    private ProviderService providerService;
    private final HttpServices httpServices;

    @Autowired
    public ProviderController(
            ProviderService providerService,
            HttpServices httpServices
    ) {
        this.providerService = providerService;
        this.httpServices = httpServices;
    }
    @PostMapping("/import-json")
    public ResponseEntity<String> importJsonData(@RequestParam("file") MultipartFile jsonData) {
        try {
            List<Provider> providerData = Utilities.convertJSONToEntity(jsonData);
            //providerService.importData(providerData);
            providerService.importData(providerData);
            return ResponseEntity.ok("Import thành công!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi import dữ liệu từ file JSON");
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getProviders(
            @RequestParam(required = false) String name,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(defaultValue = "0") short page,
            @RequestParam(defaultValue = "5") short size,
            @RequestParam(defaultValue = "id:asc") String sort
    ) {
        List<Sort.Order> orders = this.httpServices.getSortOrders(sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        Map<String, Object> response = providerService.getProvider(name, pageable);
        return ResponseEntity.status(200).body(response);
    }
}
