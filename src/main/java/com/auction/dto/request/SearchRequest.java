package com.auction.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SearchRequest {
    private String query;
    private String category;
    private String status;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String sortBy; // endingSoon, highestBid, newlyListed
}
