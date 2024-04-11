package com.khai.admin.controller;

import com.khai.admin.dto.BranchDto;
import com.khai.admin.service.HttpServices;
import com.khai.admin.service.BranchService;
import jakarta.transaction.Transactional;
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
public class BranchController {
    private BranchService branchService;

    private HttpServices httpServices;
    @Autowired
    public BranchController(BranchService branchService, HttpServices httpServices) {
        this.branchService = branchService;
        this.httpServices = httpServices;
    }
    @PostMapping
    @Transactional
    public ResponseEntity<BranchDto> create(@RequestBody BranchDto branchDto){
        BranchDto response = this.branchService.create(branchDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<BranchDto> update(@RequestBody BranchDto branchDto){
        BranchDto response = this.branchService.update(branchDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<String> delete(@RequestBody BranchDto branchDto){
        this.branchService.delete(branchDto.getId());
        return ResponseEntity.ok("Xóa chi nhánh thành công");
    }

    public ResponseEntity<BranchDto> getBranchById(
            @RequestParam(value = "") int id
    ){
        return new ResponseEntity<>(this.branchService.getBranchById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Map<String,Object>> getBranchs(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(defaultValue = "0") short page,
            @RequestParam(defaultValue = "5") short size,
            @RequestParam(defaultValue = "id:asc") String sort
    ){
        List<Sort.Order> orderList = this.httpServices.getSortOrders(sort);

        Pageable pageable = PageRequest.of(page,size,Sort.by(orderList));

        return new ResponseEntity<>(this.branchService.getBranchs(pageable), HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<Map<String,Object>> getBranchsByCreatedDate(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(defaultValue = "0") short page,
            @RequestParam(defaultValue = "5") short size,
            @RequestParam(defaultValue = "id:asc") String sort,
            @RequestParam(defaultValue = "") Date date1,
            @RequestParam(defaultValue = "") Date date2
    ){
        List<Sort.Order> orderList = this.httpServices.getSortOrders(sort);

        Pageable pageable = PageRequest.of(page,size,Sort.by(orderList));

        return new ResponseEntity<>(this.branchService.getBranchsByCreatedDate(date1,date2,pageable), HttpStatus.OK);
    }


}
