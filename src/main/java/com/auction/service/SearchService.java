package com.auction.service;

import com.auction.dto.request.SearchRequest;
import com.auction.dto.response.AuctionResponse;
import com.auction.mappers.AuctionMapper;
import com.auction.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final AuctionRepository auctionRepository;
    private final AuctionMapper auctionMapper;

    public List<AuctionResponse> searchAuctions(SearchRequest criteria) {
        return auctionRepository.findActiveAuctionsEndingSoonest().stream()
                .filter(a -> criteria.getCategory() == null || criteria.getCategory().equalsIgnoreCase("All") || a.getItem().getCategory().equalsIgnoreCase(criteria.getCategory()))
                .filter(a -> criteria.getQuery() == null || criteria.getQuery().isBlank() || a.getItem().getTitle().toLowerCase().contains(criteria.getQuery().toLowerCase()))
                .map(auctionMapper::toResponse)
                .collect(Collectors.toList());
    }
}
