package com.khai.admin.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthorizationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public AuthorizationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username;
        String accessToken;
        String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader != null && tokenHeader.startsWith("Bearer")) {
            accessToken = tokenHeader.substring(7);
            if(!accessToken.equals("null")) {
                username = jwtService.extractUsernameFromToken(accessToken);
                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if(jwtService.validateToken(accessToken)) {
                        // Create Authentication object and set in SecurityContext, after that the current user is authenticated
                        UsernamePasswordAuthenticationToken authenticationToken
                                = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        WebAuthenticationDetails webAuthenticationDetails = new WebAuthenticationDetailsSource().buildDetails(request);
                        authenticationToken.setDetails(webAuthenticationDetails);
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken); // --> user is authenticated
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
