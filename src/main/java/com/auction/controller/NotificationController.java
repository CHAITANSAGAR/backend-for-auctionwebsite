package com.auction.controller;

import com.auction.dto.response.ApiResponse;
import com.auction.entity.Notification;
import com.auction.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification Controller", description = "User notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    @Operation(summary = "Get notifications for logged in user")
    public ResponseEntity<ApiResponse<List<Notification>>> getNotifications(Principal principal) {
        String username = principal != null ? principal.getName() : "admin";
        return ResponseEntity.ok(ApiResponse.ok(notificationService.getUserNotifications(username), "Notifications retrieved"));
    }
}
