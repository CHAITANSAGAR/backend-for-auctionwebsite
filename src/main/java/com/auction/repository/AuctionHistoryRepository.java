package com.auction.repository;

import com.auction.entity.AuctionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuctionHistoryRepository extends JpaRepository<AuctionHistory, Long> {
    List<AuctionHistory> findByAuctionIdOrderByCreatedAtDesc(Long auctionId);
}
