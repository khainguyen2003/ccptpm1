package com.khai.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khai.admin.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    private boolean delEmployee(int id) {

        employeeRepository.deleteById(id);
        return false;
    }

}
