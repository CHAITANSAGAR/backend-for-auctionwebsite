package com.auction.service;

import com.auction.entity.Auction;
import com.auction.entity.AuctionStatus;
import com.auction.repository.AuctionRepository;
import com.auction.websocket.WebSocketEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionSchedulerService {

    private final AuctionRepository auctionRepository;
    private final WebSocketEventPublisher eventPublisher;

    @Transactional
    public void finalizeExpiredAuctions() {
        List<Auction> expired = auctionRepository.findByStatusAndEndTimeBefore(AuctionStatus.ACTIVE, LocalDateTime.now());
        for (Auction auction : expired) {
            auction.setStatus(AuctionStatus.ENDED);
            auctionRepository.save(auction);
            log.info("Auction #{} finalized. Winning bidder: {}", auction.getId(),
                    auction.getCurrentHighestBidder() != null ? auction.getCurrentHighestBidder().getUsername() : "None");
            eventPublisher.publishAuctionEnded(auction.getId(), "ENDED");
        }
    }
}
