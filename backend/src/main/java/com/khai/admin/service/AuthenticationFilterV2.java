package com.khai.admin.service;

import com.khai.admin.constants.HeaderSecurity;
import com.khai.admin.entity.security.KeyStore;
import com.khai.admin.service.JwtServiceV2;
import com.khai.admin.service.KeyTokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

// Execute Before Executing Spring Security Filters
// Validate the JWT Token and Provides user details to Spring Security for Authentication
@Component
public class AuthenticationFilterV2 extends OncePerRequestFilter {

    private JwtServiceV2 jwtServiceV2;

    private UserDetailsService userDetailsService;
    private KeyTokenService keyTokenService;

    public AuthenticationFilterV2(JwtServiceV2 jwtServiceV2, UserDetailsService userDetailsService) {
        this.jwtServiceV2 = jwtServiceV2;
        this.userDetailsService = userDetailsService;
    }

    /**
     * 1 - Check user id missing
     * 2 - get Access token
     * 3 - vertify token
     * 4 - check user trong dbs
     * 5 - check key store with userID
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
        UUID userID = getClientId(request);
        if(userID == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Không tồn tại user id trong request");
        } else {

            KeyStore keyStore = keyTokenService.getKeyStoreByUserId(userID);

            // Get JWT token from HTTP request
            String token = getTokenFromRequest(request);
            if(token == null) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Không tồn tại token trong request");
            } else {
                // Validate Token
                if(StringUtils.hasText(token) && jwtServiceV2.validateToken(token, keyStore.getPublicKey())){
                    // get username from token
                    String username = jwtServiceV2.extractUsernameFromToken(token, keyStore.getPublicKey());

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    filterChain.doFilter(request, response);
                }
            }

        }


    }

    private String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }

        return null;
    }

    private UUID getClientId(HttpServletRequest request) {
        String userId = request.getHeader(HeaderSecurity.CLIENT_ID.getValue());
        if(StringUtils.hasText(userId)) {
            return UUID.fromString(userId);
        }
        return null;
    }


}
