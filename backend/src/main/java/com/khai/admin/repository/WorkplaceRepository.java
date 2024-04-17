package com.khai.admin.repository;

import com.khai.admin.entity.Workplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkplaceRepository extends JpaRepository<Workplace, UUID> {
}
