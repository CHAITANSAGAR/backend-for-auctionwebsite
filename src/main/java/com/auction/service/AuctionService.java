package com.auction.service;

import com.auction.dto.request.CreateAuctionRequest;
import com.auction.dto.response.AuctionResponse;
import com.auction.entity.*;
import com.auction.exception.AuctionNotFoundException;
import com.auction.mappers.AuctionMapper;
import com.auction.repository.AuctionRepository;
import com.auction.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;
    private final AuctionMapper auctionMapper;

    public List<AuctionResponse> getAllActiveAuctions() {
        return auctionRepository.findByStatus(AuctionStatus.ACTIVE).stream()
                .map(auctionMapper::toResponse)
                .collect(Collectors.toList());
    }

    public AuctionResponse getAuctionById(Long id) {
        Auction auction = auctionRepository.findById(id)
                .orElseThrow(() -> new AuctionNotFoundException("Auction not found with ID: " + id));
        return auctionMapper.toResponse(auction);
    }

    @Transactional
    public AuctionResponse createAuction(CreateAuctionRequest request, String sellerUsername) {
        User seller = userRepository.findByUsername(sellerUsername)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        Item item = Item.builder()
                .seller(seller)
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .itemCondition(ItemCondition.valueOf(request.getItemCondition().toUpperCase()))
                .startingPrice(request.getStartingPrice())
                .reservePrice(request.getReservePrice() != null ? request.getReservePrice() : BigDecimal.ZERO)
                .minIncrement(request.getMinIncrement())
                .build();

        Auction auction = Auction.builder()
                .item(item)
                .seller(seller)
                .currentHighestBid(request.getStartingPrice())
                .startTime(LocalDateTime.now())
                .endTime(request.getEndTime())
                .status(AuctionStatus.ACTIVE)
                .totalBids(0)
                .build();

        auction = auctionRepository.save(auction);
        return auctionMapper.toResponse(auction);
    }

    public List<AuctionResponse> getAuctionsBySeller(Long sellerId) {
        return auctionRepository.findBySellerId(sellerId).stream()
                .map(auctionMapper::toResponse)
                .collect(Collectors.toList());
    }
}
