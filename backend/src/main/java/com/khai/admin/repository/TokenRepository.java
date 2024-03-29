package com.khai.admin.repository;

import com.khai.admin.entity.security.KeyStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<KeyStore, Integer> {
    Optional<KeyStore> findByUserId(int userId);

    Optional<KeyStore> findByRefreshTokenIn(List<String> refreshedTokens);
}
