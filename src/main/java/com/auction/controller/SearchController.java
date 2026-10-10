package com.auction.controller;

import com.auction.dto.request.SearchRequest;
import com.auction.dto.response.ApiResponse;
import com.auction.dto.response.AuctionResponse;
import com.auction.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
@Tag(name = "Search Controller", description = "Search and filter auctions")
public class SearchController {

    private final SearchService searchService;

    @PostMapping("/auctions")
    @Operation(summary = "Search auctions with filter criteria")
    public ResponseEntity<ApiResponse<List<AuctionResponse>>> searchAuctions(@RequestBody SearchRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(searchService.searchAuctions(request), "Search results retrieved"));
    }
}
