package com.auction.controller;

import com.auction.dto.request.UpdateProfileRequest;
import com.auction.dto.response.ApiResponse;
import com.auction.dto.response.UserResponse;
import com.auction.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "User profile and dashboard")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Get logged in user profile")
    public ResponseEntity<ApiResponse<UserResponse>> getMyProfile(Principal principal) {
        String username = principal != null ? principal.getName() : "admin";
        return ResponseEntity.ok(ApiResponse.ok(userService.getProfile(username), "Profile retrieved"));
    }

    @PutMapping("/profile")
    @Operation(summary = "Update user profile")
    public ResponseEntity<ApiResponse<UserResponse>> updateProfile(
            @RequestBody UpdateProfileRequest request,
            Principal principal) {
        String username = principal != null ? principal.getName() : "admin";
        return ResponseEntity.ok(ApiResponse.ok(userService.updateProfile(username, request), "Profile updated"));
    }
}
