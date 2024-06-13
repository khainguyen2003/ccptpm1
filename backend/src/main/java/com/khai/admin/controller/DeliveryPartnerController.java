package com.khai.admin.controller;

import com.khai.admin.dto.DeliveryPartnerDto;

import com.khai.admin.entity.DeliveryPartner;

import com.khai.admin.service.DeliveryPartnerService;
import com.khai.admin.service.HttpServices;

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
@RequestMapping("/test/api/admin/deliveryPartners")
public class DeliveryPartnerController {

    private DeliveryPartnerService deliveryPartnerService;
    private final HttpServices httpServices;

    @Autowired
    public DeliveryPartnerController(
            DeliveryPartnerService deliveryPartnerService,
            HttpServices httpServices
    ) {
        this.deliveryPartnerService = deliveryPartnerService;
        this.httpServices = httpServices;
    }


    @PostMapping("/import-json")
    public ResponseEntity<String> importJsonData(@RequestParam("file") MultipartFile jsonData) {
        try {
            List<DeliveryPartner> deliveryPartnerData = Utilities.convertJSONToEntity(jsonData);
            deliveryPartnerService.importData(deliveryPartnerData);
            return ResponseEntity.ok("Import thành công!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi import dữ liệu từ file JSON");
        }
    }

    @PostMapping
    public ResponseEntity<DeliveryPartnerDto> DeliveryPartner(@RequestHeader Map<String, String> headers, @RequestBody DeliveryPartner deliveryPartner) {
        DeliveryPartnerDto result = deliveryPartnerService.create(headers, deliveryPartner).toDto();
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    @GetMapping
    public ResponseEntity<Map<String, Object>> getDeliveryPartners(
            @RequestParam(required = false) String name,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(defaultValue = "0") short page,
            @RequestParam(defaultValue = "5") short size,
            @RequestParam(defaultValue = "id:asc") String sort
    ) {
        List<Sort.Order> orders = this.httpServices.getSortOrders(sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        Map<String, Object> response = deliveryPartnerService.getDeliveryPartners(name, pageable);
        return ResponseEntity.status(200).body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DeliveryPartnerDto> getDeliveryPartner(@PathVariable("id") int id) {
        DeliveryPartnerDto response = deliveryPartnerService.getDeliveryPartnerInfo(id);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDeliveryPartner(@PathVariable("id") int id) {
        deliveryPartnerService.deleteById(id);
        return ResponseEntity.ok("Xóa sản phẩm thành công");
    }
    @PutMapping("/{id}")
    public ResponseEntity<DeliveryPartnerDto> updateDeliveryPartner(@PathVariable("id") int id, @RequestBody DeliveryPartner deliveryPartner) {
        DeliveryPartnerDto updatedDeliveryPartner = deliveryPartnerService.updateDeliveryPartner(id, deliveryPartner).toDto();
        return ResponseEntity.ok(updatedDeliveryPartner);
    }

    @PutMapping("/sellstatus/{id}")
    public ResponseEntity<DeliveryPartnerDto> updateSellStatus(@PathVariable("id") int id, @RequestBody DeliveryPartnerDto deliveryPartnerDto) {
        DeliveryPartnerDto res = deliveryPartnerService.updateSellStatus(id, deliveryPartnerDto);
        return ResponseEntity.ok(res);
    }

}
