package com.auction.mappers;

import com.auction.dto.response.AuctionResponse;
import com.auction.entity.Auction;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class AuctionMapper {

    public AuctionResponse toResponse(Auction auction) {
        boolean reserveMet = auction.getItem().getReservePrice() != null &&
                auction.getCurrentHighestBid().compareTo(auction.getItem().getReservePrice()) >= 0;

        return AuctionResponse.builder()
                .id(auction.getId())
                .itemId(auction.getItem().getId())
                .title(auction.getItem().getTitle())
                .description(auction.getItem().getDescription())
                .category(auction.getItem().getCategory())
                .itemCondition(auction.getItem().getItemCondition().name())
                .startingPrice(auction.getItem().getStartingPrice())
                .reservePrice(auction.getItem().getReservePrice())
                .currentHighestBid(auction.getCurrentHighestBid())
                .currentHighestBidderUsername(auction.getCurrentHighestBidder() != null ? auction.getCurrentHighestBidder().getUsername() : null)
                .minIncrement(auction.getItem().getMinIncrement())
                .startTime(auction.getStartTime())
                .endTime(auction.getEndTime())
                .status(auction.getStatus().name())
                .totalBids(auction.getTotalBids())
                .sellerId(auction.getSeller().getId())
                .sellerUsername(auction.getSeller().getUsername())
                .reserveMet(reserveMet)
                .build();
    }
}
