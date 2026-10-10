package com.auction.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PlaceBidRequest {
    @NotNull(message = "Auction ID is required")
    private Long auctionId;

    @NotNull(message = "Bid amount is required")
    @DecimalMin(value = "0.01", message = "Bid amount must be positive")
    private BigDecimal amount;

    private Boolean isAutoBid;
}
