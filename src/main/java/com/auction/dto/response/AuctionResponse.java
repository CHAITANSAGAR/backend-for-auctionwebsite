package com.auction.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class AuctionResponse {
    private Long id;
    private Long itemId;
    private String title;
    private String description;
    private String category;
    private String itemCondition;
    private String primaryImageUrl;
    private BigDecimal startingPrice;
    private BigDecimal reservePrice;
    private BigDecimal currentHighestBid;
    private String currentHighestBidderUsername;
    private BigDecimal minIncrement;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private Integer totalBids;
    private Long sellerId;
    private String sellerUsername;
    private Boolean reserveMet;
}
