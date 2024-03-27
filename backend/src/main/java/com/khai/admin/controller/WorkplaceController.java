package com.khai.admin.controller;

import com.khai.admin.dto.WorkplaceDto;
import com.khai.admin.service.HttpServices;
import com.khai.admin.service.WorkplaceService;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/test/api/admin/workplaces")
public class WorkplaceController {
    private WorkplaceService workplaceService;

    private HttpServices httpServices;
    @Autowired
    public WorkplaceController(WorkplaceService workplaceService, HttpServices httpServices) {
        this.workplaceService = workplaceService;
        this.httpServices = httpServices;
    }
    @PostMapping
    @Transactional
    public ResponseEntity<WorkplaceDto> create(@RequestBody WorkplaceDto workplaceDto){
        WorkplaceDto response = this.workplaceService.create(workplaceDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<WorkplaceDto> update(@RequestBody WorkplaceDto workplaceDto){
        WorkplaceDto response = this.workplaceService.update(workplaceDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<String> delete(@RequestBody WorkplaceDto workplaceDto){
        this.workplaceService.delete(workplaceDto.getId());
        return ResponseEntity.ok("Xóa chi nhánh thành công");
    }

    public ResponseEntity<WorkplaceDto> getWorkplaceById(
            @RequestParam(value = "") int id
    ){
        return new ResponseEntity<>(this.workplaceService.getWorkplaceById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Map<String,Object>> getWorkplaces(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(defaultValue = "0") short page,
            @RequestParam(defaultValue = "5") short size,
            @RequestParam(defaultValue = "id:asc") String sort
    ){
        List<Sort.Order> orderList = this.httpServices.getSortOrders(sort);

        Pageable pageable = PageRequest.of(page,size,Sort.by(orderList));

        return new ResponseEntity<>(this.workplaceService.getWorkplaces(pageable), HttpStatus.OK);
    }


}
