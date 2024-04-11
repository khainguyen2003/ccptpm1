package com.khai.admin.repository;

import com.khai.admin.entity.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {
    @Query("SELECT b FROM tblbranch b WHERE b.name=:name")
    Optional<Branch> findByName(@Param("name") String name);
    @Query("SELECT b FROM tblbranch b WHERE b.created_date Between date1 AND date2")
    Page<Branch> findByCreatedDate(@Param("date1") Date date1, @Param("date2") Date date2, Pageable pageable);
}
