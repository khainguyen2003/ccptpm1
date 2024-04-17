package com.khai.admin.repository;

import com.khai.admin.entity.Guarantee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GuaranteeRepository extends JpaRepository<Guarantee, UUID> {
}
