package com.khai.admin.repository.jpa;

import com.khai.admin.entity.Log;
import com.khai.admin.entity.LogDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LogDetailRepository extends JpaRepository<LogDetail, UUID> {
    @Modifying
    @Query("UPDATE LogDetail l SET l.readed=:isRead WHERE l.key.logId=:logId AND l.key.userId=:userId")
    Optional<Log> updateReadById(@Param("logId") UUID logId, @Param("logId") UUID userId, @Param("isRead") boolean isRead);
}
