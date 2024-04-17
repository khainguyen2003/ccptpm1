package com.khai.admin.repository;

import com.khai.admin.dto.Product.ProductBarcode;
import com.khai.admin.dto.Product.ProductDto;
import com.khai.admin.dto.Product.ProductRecord;
import com.khai.admin.dto.Product.ProductSumary;
import com.khai.admin.entity.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

//    @Query("SELECT p, p.creator.id, p.creator.firstname, p.creator.lastname, p.creator.job, p.creator.position, p.category.id, p.category.name FROM Product p")
//    @Query(value = "SELECT p FROM tblproduct p JOIN p.category c")
//    @Query("SELECT new com.khai.admin.dto.Product.ProductDto(p, new com.khai.admin.dto.category( p FROM Product p")
//    @EntityGraph(value = "Product.list", type = EntityGraph.EntityGraphType.LOAD)

    Page<ProductDto> findBy(Pageable pageable);

    Page<ProductDto> findAllBy(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name=:name OR p.code=:code")
    Optional<Product> isExist(@Param("name") String name, @Param("code") String code);

    List<ProductBarcode> findByIdIn(UUID[] ids);

    @Query("UPDATE Product p set p.isStopCell=:status WHERE p.id IN(:ids)")
    @Modifying
    void updateSellStatusByIdIn(UUID[] ids, boolean status);
    @Query("UPDATE Product p set p.deleted=:deleted WHERE id=:id")
    @Modifying
    void updateDeletedById(@Param("id") UUID id, @Param("deleted") boolean deleted);
}
