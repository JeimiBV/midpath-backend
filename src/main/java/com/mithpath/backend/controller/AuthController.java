package com.mithpath.backend.controller;

import com.mithpath.backend.dto.AuthDto;
import com.mithpath.backend.model.User;
import com.mithpath.backend.service.interfaces.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Authentication endpoints")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<User> register(@Validated(AuthDto.RegisterGroup.class) @RequestBody AuthDto dto) {
        return ResponseEntity.ok(authService.register(dto));
    }

    @Operation(summary = "Login user and get JWT token")
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Validated(AuthDto.LoginGroup.class) @RequestBody AuthDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @Operation(summary = "Get current user profile")
    @GetMapping("/profile")
    public ResponseEntity<User> getProfile() {
        return ResponseEntity.ok(authService.getProfile());
    }
}
