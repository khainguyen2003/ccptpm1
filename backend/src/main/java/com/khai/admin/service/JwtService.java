package com.khai.admin.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {
    @Value("${jwt.secret.key}")
    private String secretKey;

    // 7 ngày
    private final long JWT_EXP_ACCESS = 7 * 3600 * 24 * 1000;
    // 1 năm
    private final long JWT_EXP_REFRESH = 1000L * 3600 * 24 * 365;

    public String extractUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = parseToken(token);
            username = String.valueOf(claims.getSubject());
        } catch (Exception e) {
            username = null;
            log.error(e.getMessage());
        }
        if(username == null) {
            // Hiển thị thông báo ở console
            log.warn("Cannot extract username from " + token);
        }

        return username;
    }

    private String generateToken(UserDetails userDetails, long expiration) {
        return generateToken(userDetails.getUsername(), expiration);
    }

    private String generateToken(String username, long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(userDetails.getUsername(), JWT_EXP_ACCESS);
    }

    public String generateAccessToken(String username) {
        return generateToken(username, JWT_EXP_ACCESS);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(userDetails.getUsername(), JWT_EXP_REFRESH);
    }

    public String generateRefreshToken(String username) {
        return generateToken(username, JWT_EXP_REFRESH);
    }

    /**
     * Phương thức lấy tất cả claims trong token
     * @param token
     * @return Các claims trong body token
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isExpired(String token) {
        boolean isExpire = false;
        try {
            Claims claims = parseToken(token);
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
    public boolean validateToken(String token) {
        try {
            parseToken(token);
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

    /**
     * Sử dụng cho secretKey ở dạng base64
     * <br/>
     * Nếu secretKey ở dạng chuỗi thường thì dùng <br/>
     * <b>secretKey.getBytes(StandardCharsets.UTF_8)</b>
     *
     * @return key sau khi đã được giải mã base64
     *
     */
    private Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }

    /**
     * Phương thức chuyển token thành các dạng thông tin mong muốn
     * @param token chuỗi muốn chuyển
     * @param claimsResolver Hàm lấy thông tin mong muốn của claims
     * @return Thông tin ở dạng mong muốn
     * @param <T> dạng trả về
     */
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = parseToken(token);
//        return claimsResolver.apply(claims);
//    }
}
