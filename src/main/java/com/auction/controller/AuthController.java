package com.auction.controller;

import com.auction.dto.request.LoginRequest;
import com.auction.dto.request.RegisterRequest;
import com.auction.dto.response.ApiResponse;
import com.auction.dto.response.JwtTokenResponse;
import com.auction.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "Endpoints for registration, login, and token generation")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register new buyer or seller account")
    public ResponseEntity<ApiResponse<JwtTokenResponse>> register(@Valid @RequestBody RegisterRequest request) {
        JwtTokenResponse response = authService.register(request);
        return ResponseEntity.ok(ApiResponse.ok(response, "User registered successfully"));
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user and issue JWT token")
    public ResponseEntity<ApiResponse<JwtTokenResponse>> login(@Valid @RequestBody LoginRequest request) {
        JwtTokenResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.ok(response, "Login successful"));
    }
}
