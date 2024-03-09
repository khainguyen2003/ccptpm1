package com.khai.admin.controller;

import com.khai.admin.dto.user.UserCreateDto;
import com.khai.admin.dto.user.UserView;
import com.khai.admin.entity.User;
import com.khai.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<UserView> getProfile(@PathVariable int id) {
        UserView user = userService.getUserInfo(id);
        return ResponseEntity.ok(user);
    }
}
