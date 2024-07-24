package com.khai.admin.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.khai.admin.dto.EmployeeDto;
import com.khai.admin.exception.EmployeeException;
import com.khai.admin.service.EmployeeService;
import com.khai.admin.service.HttpServices;

import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("/test/api/admin/employees")
@RequiredArgsConstructor
public class EmployeeController {
    
    private final HttpServices httpServices;
    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getEmployees(
        @RequestParam(defaultValue = "0") short page,
        @RequestParam(defaultValue = "5") short size,
        @RequestParam(defaultValue = "id:desc") String sort
    ) {
        List<Sort.Order> orders = httpServices.getSortOrders(sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        Map<String, Object> response = employeeService.getEmployees(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeRequest) throws EmployeeException {
        EmployeeDto e = this.employeeService.createEmployee(employeeRequest);
        return new ResponseEntity<>(e, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("id") UUID id, @RequestBody EmployeeDto employeeRequest) throws EmployeeException {
        EmployeeDto e = this.employeeService.updateEmployee(id, employeeRequest);
        return new ResponseEntity<>(e, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") UUID id) {
        boolean check = this.employeeService.deleteEmployee(id);
        return check ? new ResponseEntity<>("Xóa nhân viên thành công!", HttpStatus.OK) : new ResponseEntity<>("Xóa nhân viên không thành công!", HttpStatus.BAD_REQUEST);
    }

}
