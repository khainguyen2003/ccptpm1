package com.khai.admin.repository.jpa;

import com.khai.admin.entity.Product;
import com.khai.admin.projections.ProductPreview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

    @Query("SELECT p FROM Product p WHERE p.isDraft=:isDraft")
    Page<Product> findAllProductDraft(@Param("isDraft") boolean isDraft, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name=:name OR p.code=:code")
    Optional<Product> isExist(@Param("name") String name, @Param("code") String code);

    @Query("SELECT p FROM Product p WHERE p.id IN(:ids) AND p.isPublish=true")
    Page<ProductPreview> findByIdIn(List<UUID> ids, Pageable pageable);
    @Query("SELECT p FROM Product p WHERE p.id IN(:ids) AND p.isPublish=true")
    List<Product> findAllByIdIn(List<UUID> ids);

    @Query("SELECT p FROM Product p WHERE p.shop.id=:shopId AND p.isPublish=true")
    Page<ProductPreview> findByShopId(@Param("shopId") UUID shopId, Pageable pageable);

    @Query("UPDATE Product p set p.isStopCell=:status WHERE p.id IN(:ids)")
    @Modifying
    void updateSellStatusByIdIn(UUID[] ids, boolean status);

    @Query("UPDATE Product p SET p.isPublish=:isPublish, p.isDraft=:isDraft WHERE p.id=:productID AND p.shop.id=:shopId")
    @Modifying
    int updatePublishStatus(@Param("shopId") UUID shopId, @Param("productID") UUID productID, @Param("isPublish") boolean isPublish, @Param("isDraft") boolean isDraft);
    @Query("UPDATE Product p set p.deleted=:deleted WHERE id=:id")
    @Modifying
    void updateDeletedById(@Param("id") UUID id, @Param("deleted") boolean deleted);
}
