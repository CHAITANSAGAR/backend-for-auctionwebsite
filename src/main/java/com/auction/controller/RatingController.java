package com.auction.controller;

import com.auction.dto.request.RatingRequest;
import com.auction.dto.response.ApiResponse;
import com.auction.dto.response.RatingResponse;
import com.auction.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/ratings")
@RequiredArgsConstructor
@Tag(name = "Rating Controller", description = "User ratings and feedback")
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    @Operation(summary = "Submit a rating for completed auction")
    public ResponseEntity<ApiResponse<RatingResponse>> addRating(
            @Valid @RequestBody RatingRequest request,
            Principal principal) {
        String username = principal != null ? principal.getName() : "buyer";
        return ResponseEntity.ok(ApiResponse.ok(ratingService.addRating(request, username), "Rating recorded"));
    }
}
