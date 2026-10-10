package com.auction.service;

import com.auction.dto.request.RatingRequest;
import com.auction.dto.response.RatingResponse;
import com.auction.entity.Auction;
import com.auction.entity.Rating;
import com.auction.entity.User;
import com.auction.repository.AuctionRepository;
import com.auction.repository.RatingRepository;
import com.auction.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;

    @Transactional
    public RatingResponse addRating(RatingRequest request, String reviewerUsername) {
        User reviewer = userRepository.findByUsername(reviewerUsername)
                .orElseThrow(() -> new RuntimeException("Reviewer not found"));
        User reviewee = userRepository.findById(request.getRevieweeId())
                .orElseThrow(() -> new RuntimeException("Reviewee not found"));
        Auction auction = auctionRepository.findById(request.getAuctionId())
                .orElseThrow(() -> new RuntimeException("Auction not found"));

        Rating rating = Rating.builder()
                .auction(auction)
                .reviewer(reviewer)
                .reviewee(reviewee)
                .score(request.getScore())
                .comment(request.getComment())
                .build();

        rating = ratingRepository.save(rating);

        return RatingResponse.builder()
                .id(rating.getId())
                .auctionId(auction.getId())
                .reviewerUsername(reviewer.getUsername())
                .score(rating.getScore())
                .comment(rating.getComment())
                .createdAt(rating.getCreatedAt())
                .build();
    }
}
