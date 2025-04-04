package com.project.jwt.controller;

import com.project.jwt.service.DashBoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashBoardController {
    private final DashBoardService dashBoardService;
    public DashBoardController(DashBoardService dashBoardService) {
        this.dashBoardService = dashBoardService;
    }
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> dashboard(Authentication authentication) {
        if (authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("SCOPE_ADMIN"))) {
            return ResponseEntity.ok(dashBoardService.admin());
        } else {
            return ResponseEntity.ok(dashBoardService.common(authentication.getName()));
        }
    }
}
