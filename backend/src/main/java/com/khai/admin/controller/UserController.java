package com.khai.admin.controller;

import com.khai.admin.dto.user.UserProfileDto;
import com.khai.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public ResponseEntity<UserProfileDto> getProfile(@PathVariable int id) {
        UserProfileDto user = userService.getUserInfo(id);
        return ResponseEntity.ok(user);
    }
}
