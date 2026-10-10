package com.auction.service;

import com.auction.dto.request.CreatePaymentRequest;
import com.auction.dto.response.PaymentResponse;
import com.auction.entity.*;
import com.auction.exception.PaymentException;
import com.auction.repository.AuctionRepository;
import com.auction.repository.PaymentRepository;
import com.auction.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;
    private final StripeService stripeService;

    @Transactional
    public PaymentResponse processEscrowPayment(CreatePaymentRequest request, String payerUsername) {
        Auction auction = auctionRepository.findById(request.getAuctionId())
                .orElseThrow(() -> new RuntimeException("Auction not found"));

        User payer = userRepository.findByUsername(payerUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!auction.getCurrentHighestBidder().getId().equals(payer.getId())) {
            throw new PaymentException("Only the winning bidder can make this payment");
        }

        String intentId = stripeService.createCharge(auction.getCurrentHighestBid(), payer.getEmail());

        Payment payment = Payment.builder()
                .auction(auction)
                .payer(payer)
                .seller(auction.getSeller())
                .amount(auction.getCurrentHighestBid())
                .stripePaymentIntentId(intentId)
                .status(PaymentStatus.SUCCESS)
                .build();

        payment = paymentRepository.save(payment);

        return PaymentResponse.builder()
                .id(payment.getId())
                .auctionId(auction.getId())
                .payerId(payer.getId())
                .amount(payment.getAmount())
                .stripePaymentIntentId(intentId)
                .status(payment.getStatus().name())
                .createdAt(payment.getCreatedAt())
                .build();
    }
}
