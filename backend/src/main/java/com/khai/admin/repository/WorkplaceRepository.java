package com.khai.admin.repository;

import com.khai.admin.entity.Workplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkplaceRepository extends JpaRepository<Workplace, Integer> {
    @Query("SELECT w FROM Workplace w WHERE w.name=:name")
    Optional<Workplace> findByName(@Param("name") String name);

}
