package com.auction.service;

import com.auction.dto.request.PlaceBidRequest;
import com.auction.dto.response.BidResponse;
import com.auction.entity.*;
import com.auction.exception.AuctionNotFoundException;
import com.auction.exception.InvalidBidException;
import com.auction.repository.AuctionRepository;
import com.auction.repository.BidRepository;
import com.auction.repository.UserRepository;
import com.auction.util.ConstantsUtil;
import com.auction.websocket.WebSocketEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BidService {

    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;
    private final WebSocketEventPublisher eventPublisher;

    @Transactional
    public BidResponse placeBid(PlaceBidRequest request, String bidderUsername) {
        Auction auction = auctionRepository.findById(request.getAuctionId())
                .orElseThrow(() -> new AuctionNotFoundException("Auction not found: " + request.getAuctionId()));

        if (auction.getStatus() != AuctionStatus.ACTIVE) {
            throw new InvalidBidException("Cannot place bid on non-active auction");
        }

        if (LocalDateTime.now().isAfter(auction.getEndTime())) {
            auction.setStatus(AuctionStatus.ENDED);
            auctionRepository.save(auction);
            throw new InvalidBidException("Auction has already expired");
        }

        User bidder = userRepository.findByUsername(bidderUsername)
                .orElseThrow(() -> new RuntimeException("Bidder not found"));

        if (auction.getSeller().getId().equals(bidder.getId())) {
            throw new InvalidBidException("Sellers cannot bid on their own auctions");
        }

        BigDecimal minRequired = auction.getCurrentHighestBid().add(auction.getItem().getMinIncrement());
        if (request.getAmount().compareTo(minRequired) < 0) {
            throw new InvalidBidException("Bid amount must be at least $" + minRequired);
        }

        // Anti-Sniping rule: if bid in last 2 minutes, extend by 3 minutes
        if (auction.getEndTime().minusMinutes(ConstantsUtil.ANTI_SNIPING_WINDOW_MINUTES).isBefore(LocalDateTime.now())) {
            auction.setEndTime(auction.getEndTime().plusMinutes(ConstantsUtil.ANTI_SNIPING_EXTENSION_MINUTES));
        }

        // Save Bid
        Bid bid = Bid.builder()
                .auction(auction)
                .bidder(bidder)
                .amount(request.getAmount())
                .status(BidStatus.PLACED)
                .isAutoBid(Boolean.TRUE.equals(request.getIsAutoBid()))
                .build();
        bid = bidRepository.save(bid);

        // Update Auction
        auction.setCurrentHighestBid(request.getAmount());
        auction.setCurrentHighestBidder(bidder);
        auction.setTotalBids(auction.getTotalBids() + 1);
        auctionRepository.save(auction);

        BidResponse response = BidResponse.builder()
                .id(bid.getId())
                .auctionId(auction.getId())
                .bidderId(bidder.getId())
                .bidderUsername(bidder.getUsername())
                .amount(bid.getAmount())
                .status(bid.getStatus().name())
                .isAutoBid(bid.getIsAutoBid())
                .createdAt(bid.getCreatedAt())
                .build();

        // Broadcast to WebSocket clients
        eventPublisher.publishBidUpdate(auction.getId(), response);

        return response;
    }

    public List<BidResponse> getBidsForAuction(Long auctionId) {
        return bidRepository.findByAuctionIdOrderByAmountDesc(auctionId).stream()
                .map(b -> BidResponse.builder()
                        .id(b.getId())
                        .auctionId(b.getAuction().getId())
                        .bidderId(b.getBidder().getId())
                        .bidderUsername(b.getBidder().getUsername())
                        .amount(b.getAmount())
                        .status(b.getStatus().name())
                        .isAutoBid(b.getIsAutoBid())
                        .createdAt(b.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
