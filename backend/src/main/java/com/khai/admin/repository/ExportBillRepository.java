package com.khai.admin.repository;

import com.khai.admin.entity.ExportBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExportBillRepository extends JpaRepository<ExportBill, Integer> {
}
