package com.auction.controller;

import com.auction.dto.request.PlaceBidRequest;
import com.auction.dto.response.ApiResponse;
import com.auction.dto.response.BidResponse;
import com.auction.service.BidService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bids")
@RequiredArgsConstructor
@Tag(name = "Bid Controller", description = "Place bids and view bid ladder history")
public class BidController {

    private final BidService bidService;

    @PostMapping("/place")
    @Operation(summary = "Place a new bid on an active auction")
    public ResponseEntity<ApiResponse<BidResponse>> placeBid(
            @Valid @RequestBody PlaceBidRequest request,
            Principal principal) {
        String username = principal != null ? principal.getName() : "buyer_user";
        BidResponse response = bidService.placeBid(request, username);
        return ResponseEntity.ok(ApiResponse.ok(response, "Bid placed successfully"));
    }

    @GetMapping("/auction/{auctionId}")
    @Operation(summary = "Get bid history for a specific auction")
    public ResponseEntity<ApiResponse<List<BidResponse>>> getBidsForAuction(@PathVariable Long auctionId) {
        return ResponseEntity.ok(ApiResponse.ok(bidService.getBidsForAuction(auctionId), "Bid history retrieved"));
    }
}
