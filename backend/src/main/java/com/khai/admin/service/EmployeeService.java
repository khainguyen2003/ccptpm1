package com.khai.admin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.khai.admin.dto.EmployeeDto;
import com.khai.admin.entity.Employee;
import com.khai.admin.exception.EmployeeException;
import com.khai.admin.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Map<String, Object> getEmployees(Pageable pageable) {
        Map<String, Object> map = new HashMap<>();
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        Page<Employee> employees = employeeRepository.findAll(pageable);
        employees.getContent().forEach(item -> {
            employeeDtos.add(item.getDto());
        });
        map.put("employees", employeeDtos);
        map.put("curPage", employees.getNumber());
        map.put("totalPage", employees.getTotalPages());
        map.put("totalElements", employees.getTotalElements());
        map.put("pageSize", employees.getSize());
        map.put("numberOfElements", employees.getNumberOfElements());
        return map;
    }

    public EmployeeDto createEmployee(EmployeeDto employeeDto) throws EmployeeException {
        Employee e = new Employee();
        e.setContract_expire(employeeDto.getContract_expire());
        e.setBirthday(employeeDto.getBirthday());
        e.setStatus((byte)1);
        e.setStart_date(employeeDto.getStart_date());
        e.setRole((byte)0);
        e.setCreated_date(new Date());
        e.setModified_date(new Date());
        e.setDeleted(false);
        e.setFullname(employeeDto.getFullname());
        e.setBirthday(employeeDto.getBirthday());
        e.setEmail(employeeDto.getEmail());
        e.setPhoneNumber(employeeDto.getPhoneNumber());
        e.setAddress(employeeDto.getAddress());
        e.setNotes(employeeDto.getNotes());
        //e.setImage(employeeDto.getImage());
        e.setName(employeeDto.getName().trim());
        e.setPass(employeeDto.getPass());
        if(e.getName() != null && !e.getName().equalsIgnoreCase("")) {
         
           if(this.isExisting(e.getName())) {
                throw new EmployeeException("Tên tài khoản nhân viên đã được sử dụng!");
            }
        }
        e = this.employeeRepository.save(e);
        return e.getDto();
    }

    private boolean isExisting(String name) {
        return this.employeeRepository.findFirstByName(name).isPresent();
    }

    public EmployeeDto updateEmployee(int id, EmployeeDto employeeDto) throws EmployeeException {
        if(!this.employeeRepository.existsById(id)) {
            throw new EmployeeException("Nhân viên không tồn tại!");
        }
        Employee e = this.employeeRepository.findById(id).get();
        e.setContract_expire(employeeDto.getContract_expire());
        e.setBirthday(employeeDto.getBirthday());
        e.setStatus(employeeDto.getStatus());
        e.setStart_date(employeeDto.getStart_date());
        e.setRole(employeeDto.getRole());
        e.setModified_date(new Date());
        e.setFullname(employeeDto.getFullname());
        e.setBirthday(employeeDto.getBirthday());
        e.setEmail(employeeDto.getEmail());
        e.setPhoneNumber(employeeDto.getPhoneNumber());
        e.setAddress(employeeDto.getAddress());
        e.setNotes(employeeDto.getNotes());
        //e.setImage(employeeDto.getImage());
        e.setName(employeeDto.getName().trim());
        e.setPass(employeeDto.getPass());
        if(e.getName() != null && !e.getName().equalsIgnoreCase("")) {
            if(this.isExisting(e.getName())) {
                throw new EmployeeException("Tên tài khoản nhân viên đã được sử dụng!");
            }
        }
        e = this.employeeRepository.save(e);
        return e.getDto();
    }

    public boolean deleteEmployee(int id) {
        boolean flag = false;
        Optional<Employee> employee = this.employeeRepository.findById(id);
        if(employee.isPresent()) {
            if(employee.get().isDeleted()) {
                this.employeeRepository.deleteById(id);
                flag = true;
            } else {
                Employee e = employee.get();
                e.setDeleted(true);
                e = this.employeeRepository.save(e);
                flag = e != null;
            }
        }
        return flag;
    }

    public Map<String, Object> getEmployeesInTrash(Pageable pageable) {
        Map<String, Object> map = new HashMap<>();
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        Page<Employee> employees = employeeRepository.findByDeleted(true, pageable);
        employees.getContent().forEach(item -> {
            employeeDtos.add(item.getDto());
        });
        map.put("employees", employeeDtos);
        map.put("curPage", employees.getNumber());
        map.put("totalPage", employees.getTotalPages());
        map.put("totalElements", employees.getTotalElements());
        map.put("pageSize", employees.getSize());
        map.put("numberOfElements", employees.getNumberOfElements());
        return map;
    }

}
