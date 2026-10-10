package com.auction.dto.request;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UpdateAuctionRequest {
    private String title;
    private String description;
    private BigDecimal reservePrice;
    private LocalDateTime endTime;
}
