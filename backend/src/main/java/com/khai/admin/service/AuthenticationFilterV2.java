package com.khai.admin.service;

import com.khai.admin.constants.HeaderSecurity;
import com.khai.admin.entity.User;
import com.khai.admin.entity.security.KeyStore;
import com.khai.admin.util.HttpUtilities;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

// Execute Before Executing Spring Security Filters
// Validate the JWT Token and Provides user details to Spring Security for Authentication
@Component
public class AuthenticationFilterV2 extends OncePerRequestFilter {

    private final JwtServiceV2 jwtServiceV2;

    private final UserDetailsService userDetailsService;
    private final KeyTokenService keyTokenService;

    public AuthenticationFilterV2(JwtServiceV2 jwtServiceV2, UserDetailsService userDetailsService, KeyTokenService keyTokenService) {
        this.jwtServiceV2 = jwtServiceV2;
        this.userDetailsService = userDetailsService;
        this.keyTokenService = keyTokenService;
    }

    /**
     * 1 - Check user id missing
     * 2 - get api key trong request (x-api-key)
     * 3 - get refresh token trong header x-rfresh-token
     *      - Nếu có
     * 4 - get Access token (Authorization)
     * 5 - vertify token
     * 6 - check user trong dbs
     * 7 - check key store with userID
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 1 -
        UUID userID = HttpUtilities.getClientId(request);
        System.out.println("userID: " + userID);
        if(userID != null) {
            // 2 - get Access token
            KeyStore keyStore = keyTokenService.getKeyStoreByUserId(userID);
            // Get JWT token from HTTP request
            String token = HttpUtilities.getTokenFromRequest(request);
            System.out.println("token: " + token);
            if(token != null) {
                // 3 - Validate Token
                try {
                    PublicKey publicKey = keyTokenService.decodePublicKey(keyStore.getPublicKey());
                    if(StringUtils.hasText(token) && jwtServiceV2.validateToken(token, publicKey)) {
                        // get username from token
                        String username = jwtServiceV2.extractUsernameFromToken(token, publicKey);
                        // 4 - check user trong dbs
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                } catch (InvalidKeySpecException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        filterChain.doFilter(request, response);

    }

}
