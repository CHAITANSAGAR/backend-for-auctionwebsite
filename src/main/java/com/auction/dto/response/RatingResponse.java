package com.auction.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class RatingResponse {
    private Long id;
    private Long auctionId;
    private String reviewerUsername;
    private Integer score;
    private String comment;
    private LocalDateTime createdAt;
}
