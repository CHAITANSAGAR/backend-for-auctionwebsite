package com.auction.controller;

import com.auction.dto.request.CreateAuctionRequest;
import com.auction.dto.response.ApiResponse;
import com.auction.dto.response.AuctionResponse;
import com.auction.service.AuctionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auctions")
@RequiredArgsConstructor
@Tag(name = "Auction Controller", description = "Browse, list, and create auctions")
public class AuctionController {

    private final AuctionService auctionService;

    @GetMapping("/public/active")
    @Operation(summary = "Get all active auctions")
    public ResponseEntity<ApiResponse<List<AuctionResponse>>> getActiveAuctions() {
        return ResponseEntity.ok(ApiResponse.ok(auctionService.getAllActiveAuctions(), "Active auctions retrieved"));
    }

    @GetMapping("/public/{id}")
    @Operation(summary = "Get auction details by ID")
    public ResponseEntity<ApiResponse<AuctionResponse>> getAuctionById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(auctionService.getAuctionById(id), "Auction details retrieved"));
    }

    @PostMapping("/create")
    @Operation(summary = "Create and list a new auction")
    public ResponseEntity<ApiResponse<AuctionResponse>> createAuction(
            @Valid @RequestBody CreateAuctionRequest request,
            Principal principal) {
        String username = principal != null ? principal.getName() : "seller_user";
        AuctionResponse response = auctionService.createAuction(request, username);
        return ResponseEntity.ok(ApiResponse.ok(response, "Auction created successfully"));
    }
}
