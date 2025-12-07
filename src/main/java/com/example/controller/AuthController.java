package com.example.controller;

import com.example.dto.RegisterRequest;
import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // API Đăng ký tài khoản mới
    // POST /api/auth/register
    // Body: { "username": "...", "password": "...", "email": "...", "roles":
    // ["STUDENT"] }

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            User user = userService.registerUser(
                    registerRequest.getUsername(),
                    registerRequest.getPassword(),
                    registerRequest.getEmail(),
                    registerRequest.getRoles());
            return ResponseEntity.ok("User registered successfully: " + user.getUsername());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // API Lấy thông tin người dùng hiện tại (đang đăng nhập)
    // GET /api/auth/me
    // Yêu cầu: Phải gửi kèm Token hoặc Cookie session
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(java.security.Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body("Chưa đăng nhập!");
        }
        return ResponseEntity.ok(userService.findByUsername(principal.getName()));
    }
}
