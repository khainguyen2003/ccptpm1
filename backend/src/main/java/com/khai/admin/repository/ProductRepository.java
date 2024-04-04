package com.khai.admin.repository;

import com.khai.admin.dto.Product.ProductDto;
import com.khai.admin.dto.Product.ProductRecord;
import com.khai.admin.dto.Product.ProductSumary;
import com.khai.admin.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

//    @Query("SELECT p, p.creator.id, p.creator.firstname, p.creator.lastname, p.creator.job, p.creator.position, p.category.id, p.category.name FROM Product p")
//    @Query(value = "SELECT p.* FFOM tblproduct p LEFT JOIN tblcategory c ON p.category_id=c.pc_id LEFT JOIN tbluser u ON p.", nativeQuery = true)
//    @Query("SELECT new com.khai.admin.dto.Product.ProductDto(p, new com.khai.admin.dto.category( p FROM Product p")
//    @EntityGraph(value = "Product.list", type = EntityGraph.EntityGraphType.LOAD)

    Page<ProductSumary> findBy(Pageable pageable);

    Page<ProductDto> findAllBy(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name=:name OR p.code=:code")
    Optional<Product> isExist(@Param("name") String name, @Param("code") String code);

    @Query("UPDATE Product p set p.deleted=:deleted WHERE id=:id")
    @Modifying
    void updateDeletedById(@Param("id") int id, @Param("deleted") boolean deleted);
}
