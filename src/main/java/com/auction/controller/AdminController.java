package com.auction.controller;

import com.auction.dto.response.AdminDashboardResponse;
import com.auction.dto.response.ApiResponse;
import com.auction.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Controller", description = "Admin dashboard and platform operations")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/dashboard")
    @Operation(summary = "Retrieve admin system dashboard metrics")
    public ResponseEntity<ApiResponse<AdminDashboardResponse>> getDashboardStats() {
        return ResponseEntity.ok(ApiResponse.ok(adminService.getDashboardStats(), "Admin dashboard metrics retrieved"));
    }
}
