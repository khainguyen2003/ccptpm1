package com.khai.admin.repository;

import com.khai.admin.entity.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LogRepository extends JpaRepository<Log, Integer> {
    @Modifying
    @Query("UPDATE LogDetail l SET l.readed = 1 WHERE ld.key.logId = :logId AND ld.key.userId = :userId")
    Optional<Log> updateReadById(@Param("logId") int logId,@Param("logId") int userId);

    @Query("SELECT * FROM Log l LEFT JOIN LogDetail ld " )
    Page<Log> getLogs(Pageable pageable);


}
