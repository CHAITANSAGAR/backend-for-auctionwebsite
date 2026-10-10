package com.auction.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePaymentRequest {
    @NotNull(message = "Auction ID is required")
    private Long auctionId;

    private String paymentMethodId;
}
