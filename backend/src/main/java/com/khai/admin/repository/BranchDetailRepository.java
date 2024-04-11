package com.khai.admin.repository;

import com.khai.admin.entity.Branch;
import com.khai.admin.entity.BranchDetail;
import com.khai.admin.entity.BranchDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface BranchDetailRepository extends JpaRepository<BranchDetail, Integer> {
    @Query("SELECT bd FROM tblbd bd WHERE bd.name=:name")
    Optional<Branch> findByName(@Param("name") String name);
    @Query("SELECT bd FROM tblbd bd WHERE bd.created_date Between date1 AND date2")
    Optional<Branch> findByCreatedDate(@Param("date1") Date date1, @Param("date2") Date date2);

}
