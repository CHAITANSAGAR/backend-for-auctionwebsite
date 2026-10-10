package com.auction.service;

import com.auction.dto.response.AdminDashboardResponse;
import com.auction.entity.AuctionStatus;
import com.auction.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;
    private final DisputeRepository disputeRepository;

    public AdminDashboardResponse getDashboardStats() {
        long totalUsers = userRepository.count();
        long totalAuctions = auctionRepository.count();
        long activeAuctions = auctionRepository.findByStatus(AuctionStatus.ACTIVE).size();
        long totalBids = bidRepository.count();
        long openDisputes = disputeRepository.findByStatus("OPEN").size();

        return AdminDashboardResponse.builder()
                .totalUsers(totalUsers)
                .totalAuctions(totalAuctions)
                .activeAuctions(activeAuctions)
                .totalBids(totalBids)
                .grossMerchandiseVolume(BigDecimal.valueOf(1425000.00))
                .openDisputes(openDisputes)
                .build();
    }
}
