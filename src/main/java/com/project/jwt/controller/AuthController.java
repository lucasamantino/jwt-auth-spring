package com.project.jwt.controller;

import com.project.jwt.DTO.RequestAuthParams;
import com.project.jwt.DTO.RequestLoginParams;
import com.project.jwt.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody RequestLoginParams request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody RequestAuthParams request) {
        return authService.register(request);
    }

}
