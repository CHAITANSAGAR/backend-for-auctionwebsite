package com.auction.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RatingRequest {
    @NotNull(message = "Auction ID is required")
    private Long auctionId;

    @NotNull(message = "Reviewee ID is required")
    private Long revieweeId;

    @NotNull(message = "Score is required")
    @Min(1) @Max(5)
    private Integer score;

    private String comment;
}
