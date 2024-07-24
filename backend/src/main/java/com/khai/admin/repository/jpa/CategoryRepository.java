package com.khai.admin.repository;

import com.khai.admin.entity.Category;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    /**
     * SELECT c FROM <Tên entity> c WHERE
     * @param name
     * @return
     */
    @Query("SELECT c FROM Category c WHERE c.name=:name")
//    @Lock(LockModeType.OPTIMISTIC)
    Optional<Category> findByName(@Param("name") String name);

    Page<Category> findByNotesContaining(String notes, Pageable pageable);
    Page<Category> findByNameContaining(String name, Pageable pageable);
}
