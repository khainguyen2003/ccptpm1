package com.khai.admin.repository;


import com.khai.admin.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProviderRepository extends JpaRepository<Provider, Integer> {
    @Query("SELECT p FROM Provider p WHERE p.name=:name ")
    Optional<Provider> isExist(@Param("name") String name);

}