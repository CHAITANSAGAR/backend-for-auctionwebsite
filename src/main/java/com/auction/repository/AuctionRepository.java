package com.auction.repository;

import com.auction.entity.Auction;
import com.auction.entity.AuctionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<Auction> findByStatus(AuctionStatus status);
    List<Auction> findBySellerId(Long sellerId);
    List<Auction> findByStatusAndEndTimeBefore(AuctionStatus status, LocalDateTime dateTime);

    @Query("SELECT a FROM Auction a WHERE a.status = 'ACTIVE' ORDER BY a.endTime ASC")
    List<Auction> findActiveAuctionsEndingSoonest();

    @Query("SELECT a FROM Auction a WHERE a.item.category = :category AND a.status = 'ACTIVE'")
    List<Auction> findByCategory(String category);
}
