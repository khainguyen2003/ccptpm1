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
    private final FileService fileService;

    public Map<String, Object> getEmployees(Pageable pageable, String key) {
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
        e.setCreated_date(new Date());
        e.setModified_date(new Date());
        e.setDeleted(false);
        e.setFullname(employeeDto.getFullname());
        e.setBirthday(employeeDto.getBirthday());
        e.setEmail(employeeDto.getEmail());
        e.setPhoneNumber(employeeDto.getPhoneNumber());
        e.setAddress(employeeDto.getAddress());
        e.setNotes(employeeDto.getNotes());
        e.setImage(fileService.uploadMultipleFile(employeeDto.getImageUpload(), fileService.getEmployeeFolder()));
        e = employeeRepository.save(e);
        return e.getDto();
    }

    public EmployeeDto updateEmployee(int id, EmployeeDto employeeDto) throws EmployeeException {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()) {
            Employee e = employee.get();
            e.setContract_expire(employeeDto.getContract_expire());
            e.setBirthday(employeeDto.getBirthday());
            e.setStatus(employeeDto.getStatus());
            e.setStart_date(employeeDto.getStart_date());
            e.setModified_date(new Date());
            e.setFullname(employeeDto.getFullname());
            e.setBirthday(employeeDto.getBirthday());
            e.setEmail(employeeDto.getEmail());
            e.setPhoneNumber(employeeDto.getPhoneNumber());
            e.setAddress(employeeDto.getAddress());
            e.setNotes(employeeDto.getNotes());
            if(employeeDto.getImageUpload() != null && !employeeDto.getImageUpload().isEmpty()) {
                String newPath = fileService.uploadMultipleFile(employeeDto.getImageUpload(), fileService.getEmployeeFolder());
                if(newPath != null) {
                    String oldPath = e.getImage();
                    fileService.deleteFileFromSystem(oldPath);
                    e.setImage(newPath);
                }
            }
            e = employeeRepository.save(e);
            return e.getDto();
        }
        throw new EmployeeException("Nhân viên không tồn tại!");
    }

    public void deleteEmployee(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()) {
            if(employee.get().isDeleted()) {
                employeeRepository.deleteById(id);
            } else {
                Employee e = employee.get();
                e.setDeleted(true);
                employeeRepository.save(e);
            }
        }
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

    public EmployeeDto getEmployee(int id) throws EmployeeException {
        Optional<Employee> e = employeeRepository.findById(id);
        if(e.isPresent()) return e.get().getDto();
        throw new EmployeeException("Nhân viên không tồn tại!");
    }

}
