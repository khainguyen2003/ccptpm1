package com.khai.admin.repository;

import com.khai.admin.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

//    @Query("SELECT p, u.id, u.firstname, u.lastname, u.job, u.position, c.id, c.name FROM Product p LEFT JOIN p.creator u LEFT JOIN p.category c")
//    Page<Product> findAll(Specification<Product> s, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name=:name OR p.code=:code")
    Optional<Product> isExist(@Param("name") String name, @Param("code") String code);

    @Query("UPDATE Product p set p.deleted=:deleted WHERE id=:id")
    void updateDeletedById(@Param("id") int id, @Param("deleted") boolean deleted);
}
