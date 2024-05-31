package com.khai.admin.repository;

import com.khai.admin.entity.Employee;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findFirstByName(String name);

    @Query("SELECT e FROM Employee e WHERE e.fullname LIKE %:keyword% OR e.email LIKE %:keyword%")
    Page<Employee> findEmployees(@Param("keyword") String keyword, Pageable pageable);

    Page<Employee> findByDeleted(boolean deleted, Pageable pageable);

}
