package com.auction.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponse {
    private Long id;
    private Long auctionId;
    private Long payerId;
    private BigDecimal amount;
    private String stripePaymentIntentId;
    private String status;
    private LocalDateTime createdAt;
}
