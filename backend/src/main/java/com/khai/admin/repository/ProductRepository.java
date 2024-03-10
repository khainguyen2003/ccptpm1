package com.khai.admin.repository;

import com.khai.admin.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT * FROM p Product WHERE c.name=:name OR c.code=:code")
    Optional<Product> isExist(@Param("name") String name, @Param("code") String code);
}
