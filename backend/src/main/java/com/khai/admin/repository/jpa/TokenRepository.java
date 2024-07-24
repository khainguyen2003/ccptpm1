package com.khai.admin.repository.jpa;

import com.khai.admin.entity.security.KeyStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.security.Key;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<KeyStore, UUID> {
    Optional<KeyStore> findByUserId(UUID userId);
    Optional<KeyStore> deleteByUserId(UUID userId);

    Optional<KeyStore> findByRefreshTokenIn(List<String> refreshedTokens);
    Optional<KeyStore> findByRefreshToken(String refreshToken);
}
