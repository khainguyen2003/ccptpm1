package com.khai.admin.service;

import com.khai.admin.dto.JwtView;
import com.khai.admin.entity.User;
import com.khai.admin.entity.security.KeyStore;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// JwtService + RSA
@Service
@Slf4j
public class JwtServiceV2 {
    private KeyTokenService keyTokenService;

    @Autowired
    public JwtServiceV2(KeyTokenService keyTokenService) {
        this.keyTokenService = keyTokenService;
    }

    // 7 ngày
    private final long JWT_EXP_ACCESS = 7 * 3600 * 24 * 1000;
    // 1 năm
    private final long JWT_EXP_REFRESH = 1000L * 3600 * 24 * 365;

    public String extractUsernameFromToken(String token, PublicKey publicKey) {
        String username;
        try {
            Claims claims = parseToken(token, publicKey);
            username = String.valueOf(claims.getSubject());
        } catch (Exception e) {
            username = null;
            log.error(e.getMessage());
        }
        if (username == null) {
            log.warn("Can't extract username with token " + token);
        }
        return username;
    }

    public String generateAccessToken(User user, PrivateKey privateKey) {
        return generateToken(user, JWT_EXP_ACCESS, privateKey);
    }
    public String generateRefreshToken(User user, PrivateKey privateKey) {
        return generateToken(user, JWT_EXP_ACCESS, privateKey);
    }

    private String generateToken(User user, long expiration, PrivateKey privateKey) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        System.out.printf("private key: [format: %s, alogrithm: %s]", privateKey.getFormat(), privateKey.getAlgorithm());
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(privateKey)
                .compact();
    }

    /**
     * Phương thức lấy tất cả claims trong token
     * @param token
     * @return Các claims trong body token
     */
    public Claims parseToken(String token, PublicKey publicKey) {
        return Jwts.parser()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isExpired(String token, PublicKey publicKey) {
        boolean isExpire = false;
        try {
            Claims claims = parseToken(token, publicKey);
            Date currentDate = new Date();
            // check expiration date is before current date
            isExpire = claims.getExpiration().before(currentDate);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return isExpire;
    }

    /**
     * Phương thức kiểm tra token đã hết hạn hay chưa
     * @param token
     * @return
     */
    public boolean validateToken(String token, PublicKey publicKey) {
        try {
            parseToken(token, publicKey);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return false;
    }

    private SecretKey generalKey(byte[] keyBytes) {
        return new SecretKeySpec(keyBytes, "HmacSHA512");
    }
}
