package com.khai.admin.repository;

import com.khai.admin.entity.ImportBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportBillRepository extends JpaRepository<ImportBill, Integer> {
}
