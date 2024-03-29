package com.khai.admin.config;

import com.khai.admin.constants.UserRole;
import com.khai.admin.service.AuthorizationFilter;
import com.khai.admin.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Value("${app.client.url}")
    private String appClientUrl;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, JwtService jwtService) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthorizationFilter authorizationFilter = new AuthorizationFilter(jwtService, userDetailsService);
        http
                // disable CSRF protection
                .csrf((csrf) -> csrf.disable())
//                .securityMatcher("/api/**")
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers(
                                        "/login", "/signup", "/error", "/api/auth/**", "/test/**", "/admin/**"
                                )
                                .permitAll() // Các trang trên sẽ không được bảo vệ
                                .requestMatchers(
                                         "/api/user/**", "/api/refresh-token"
                                )
                                .hasRole(UserRole.USER.getRole())
                                .requestMatchers("/api/admin/**", "/api/admin/refresh-token")
                                .hasRole(UserRole.ADMIN.getRole())

                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement((sessionManagement) ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * by default return ProviderManager object (ProviderManager class implements AuthenticationManager)
     * when call authenticate() method in ProviderManager class
     * it uses the authenticate() method in AuthenticationProvider
     * that sets the PasswordEncoder and UserDetailService --> config the AuthenticationProvider
     * @param authConfig
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        // by default return ProviderManager object (ProviderManager class implements AuthenticationManager)
        // when call authenticate() method in ProviderManager class
        // it uses the authenticate() method in AuthenticationProvider
        // that sets the PasswordEncoder and UserDetailService --> config the AuthenticationProvider
        return authConfig.getAuthenticationManager();
    }

    /**
     * config AuthenticationProvider that implement the authenticate method
     * use DaoAuthenticationProvider that use the PasswordEncoder and UserDetailsService to authenticate
     * @return
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        // config AuthenticationProvider that implement the authenticate method
        // use DaoAuthenticationProvider that use the PasswordEncoder and UserDetailsService to authenticate
        // (this app use the username and password authentication method)
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);

        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "PUT"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        System.out.println(source.getCorsConfigurations());

        return source;
    }
}
