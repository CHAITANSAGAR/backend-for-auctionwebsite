package com.auction.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class AdminDashboardResponse {
    private Long totalUsers;
    private Long totalAuctions;
    private Long activeAuctions;
    private Long totalBids;
    private BigDecimal grossMerchandiseVolume;
    private Long openDisputes;
}
