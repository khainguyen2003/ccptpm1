package com.khai.admin.controller;

import com.khai.admin.constants.UserRole;
import com.khai.admin.dto.user.UserCreateDto;
import com.khai.admin.dto.JwtView;
import com.khai.admin.entity.User;
import com.khai.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final UserService userService;

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
    @PostMapping("/admin/signup")
    public ResponseEntity<String> signup(@RequestBody UserCreateDto userCreateDto) {
        userCreateDto.setUserRole(UserRole.ADMIN);
        userService.create(userCreateDto);
        return ResponseEntity.ok("Đăng ký thành công");
    }

    @PostMapping("/admin/login")
    public ResponseEntity<JwtView> login(@RequestBody User loginRequest) {
        JwtView jwtView = userService.authenticate(loginRequest);
        return ResponseEntity.ok(jwtView);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtView> refreshToken(@RequestBody JwtView jwtView) {
        JwtView response = userService.refreshToken(jwtView);
        return ResponseEntity.ok(response);
    }
}
