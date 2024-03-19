package com.khai.admin.service;

import com.khai.admin.dto.JwtView;
import com.khai.admin.entity.User;
import com.khai.admin.entity.security.KeyStore;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// JwtService + RSA
@Service
@Slf4j
public class JwtServiceV2 {
    private final KeyTokenService keyTokenService;

    @Autowired
    public JwtServiceV2(KeyTokenService keyTokenService) {
        this.keyTokenService = keyTokenService;
    }

    // 7 ngày
    private final long JWT_EXP_ACCESS = 7 * 3600 * 24 * 1000;
    // 1 năm
    private final long JWT_EXP_REFRESH = 1000L * 3600 * 24 * 365;

    public String generateAccessToken(User user, String publicKey) {
        return generateToken(user, JWT_EXP_ACCESS, publicKey);
    }
    public String generateRefreshToken(User user, String privateKey) {
        return generateToken(user, JWT_EXP_ACCESS, privateKey);
    }

    private String generateToken(User user, long expiration, String signinKey) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        Map<String, Object> payload = new HashMap<>();
        payload.put("user_id", user.getId());

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setId(String.valueOf(user.getId()))
                .setPayload(String.valueOf(user.getId()))
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, signinKey)
                .compact();
    }

    /**
     * Phương thức lấy tất cả claims trong token
     * @param token
     * @return Các claims trong body token
     */
    public Claims parseToken(String token, String signinKey) {
        return Jwts.parser()
                .setSigningKey(signinKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isExpired(String token, String signinKey) {
        boolean isExpire = false;
        try {
            Claims claims = parseToken(token, signinKey);
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
    public boolean validateToken(String token, String signinKey) {
        try {
            parseToken(token, signinKey);
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
}
