package com.auction.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class BidResponse {
    private Long id;
    private Long auctionId;
    private Long bidderId;
    private String bidderUsername;
    private BigDecimal amount;
    private String status;
    private Boolean isAutoBid;
    private LocalDateTime createdAt;
}
