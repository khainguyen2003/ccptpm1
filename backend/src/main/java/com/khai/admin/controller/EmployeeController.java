package com.khai.admin.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.khai.admin.dto.EmployeeDto;
import com.khai.admin.exception.EmployeeException;
import com.khai.admin.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("/test/api/admin/employees")
@RequiredArgsConstructor
public class EmployeeController {
    
    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<?> getEmployees() {
        return null;
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeRequest) throws EmployeeException {
        EmployeeDto e = this.employeeService.createEmployee(employeeRequest);
        return new ResponseEntity<>(e, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("id") int id, @RequestBody EmployeeDto employeeRequest) throws EmployeeException {
        EmployeeDto e = this.employeeService.updateEmployee(id, employeeRequest);
        return new ResponseEntity<>(e, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") int id) {
        boolean check = this.employeeService.deleteEmployee(id);
        return check ? new ResponseEntity<>("Xóa nhân viên thành công!", HttpStatus.OK) : new ResponseEntity<>("Xóa nhân viên không thành công!", HttpStatus.BAD_REQUEST);
    }

}
