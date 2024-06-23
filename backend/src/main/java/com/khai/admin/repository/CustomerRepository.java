package com.khai.admin.repository;

import com.khai.admin.entity.Category;
import com.khai.admin.entity.Customer;
import com.khai.admin.entity.Provider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query("SELECT c FROM Customer c WHERE c.name=:name")
    Optional<Provider> isExist(@Param("name") String name);
}