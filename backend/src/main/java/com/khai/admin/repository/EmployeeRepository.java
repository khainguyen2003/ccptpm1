package com.khai.admin.repository;

import com.khai.admin.entity.Employee;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT e FORM Employee e WHERE e.name=?")
    Optional<Employee> findByName(String name);

}
