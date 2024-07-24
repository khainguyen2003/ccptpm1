package com.khai.admin.repository.jpa;

import com.khai.admin.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer> {
    @Query("SELECT f FROM Resource f WHERE f.asset_id=:assetId")
    Optional<Resource> findByAssetId(@Param("assetId") String assetId);
    @Query("SELECT f FROM Resource f WHERE f.file_name=:name")
    Optional<Resource> findByName(@Param("name") String name);
}
