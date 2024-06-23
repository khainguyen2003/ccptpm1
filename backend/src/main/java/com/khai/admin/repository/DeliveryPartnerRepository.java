package com.khai.admin.repository;



import com.khai.admin.entity.DeliveryPartner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartner, Integer> {
    @Query("SELECT c FROM Category c WHERE c.name=:name")
//    @Lock(LockModeType.OPTIMISTIC)
    Optional<DeliveryPartner> findByName(@Param("name") String name);

    Page<DeliveryPartner> findByNotesContaining(String notes, Pageable pageable);
    Page<DeliveryPartner> findByNameContaining(String name, Pageable pageable);
}
