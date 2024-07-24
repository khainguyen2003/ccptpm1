package com.khai.admin.controller;

import com.khai.admin.constants.HeaderSecurity;
import com.khai.admin.constants.UserRole;
import com.khai.admin.dto.user.UserCreateDto;
import com.khai.admin.dto.JwtView;
import com.khai.admin.service.UserService;
import com.khai.admin.util.HttpUtilities;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final UserService userService;
    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

//    @Operation(summary = "Create a user with credential")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Create user successfully", content = {
//                    @Content(mediaType = "application/json", schema = @Schema(implementation = JwtView.class))
//            }),
//            @ApiResponse(responseCode = "400", description = "User already exists", content = {@Content(mediaType = "application/json")})
//    })

    /*@PostMapping("/admin/signup")
    public ResponseEntity<String> signup(@RequestBody UserCreateDto userCreateDto) {
        userCreateDto.setUserRole(UserRole.ADMIN);
        userService.create(userCreateDto);
        return ResponseEntity.ok("Đăng ký thành công");
    }*/

    @PostMapping("/admin/signup")
    public ResponseEntity<String> signupV2(@RequestBody UserCreateDto userCreateDto) {
        userCreateDto.setUserRole(UserRole.ROLE_ADMIN);
        userService.createV2(userCreateDto);
        return ResponseEntity.ok("Đăng ký thành công");
    }

    /*@PostMapping("/admin/login")
    public ResponseEntity<JwtView> login(@RequestBody User loginRequest) {
        JwtView jwtView = userService.authenticate(loginRequest);
        return ResponseEntity.ok(jwtView);
    }*/

    @PostMapping("/admin/login")
    public ResponseEntity<JwtView> loginV2(@RequestHeader Map<String, Object> headers, @RequestBody UserCreateDto loginRequest) {
        JwtView jwtView = userService.loginV2(loginRequest);
        return ResponseEntity.ok(jwtView);
    }

    /**
     * Invalidate the HTTP session (SecurityContextLogoutHandler)
     * Clear the SecurityContextHolderStrategy (SecurityContextLogoutHandler)
     * Clear the SecurityContextRepository (SecurityContextLogoutHandler)
     * Clean up any RememberMe authentication (TokenRememberMeServices / PersistentTokenRememberMeServices)
     * Clear out any saved CSRF token (CsrfLogoutHandler)
     * Fire a LogoutSuccessEvent (LogoutSuccessEventPublishingLogoutHandler)
     * @param authentication
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        // .. perform logout
        this.logoutHandler.logout(request, response, authentication);

        UUID userId = HttpUtilities.getClientId(request);
        userService.logout(userId);

        return new ResponseEntity<>("Đăng suất thành công", HttpStatus.OK);
    }

//    @PostMapping("/refresh-token")
//    public ResponseEntity<JwtView> refreshToken(HttpServletRequest request) {
//        JwtView response = userService.refreshToken(jwtView);
//        return ResponseEntity.ok(response);
//    }
}
