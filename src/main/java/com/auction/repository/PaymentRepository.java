package com.auction.repository;

import com.auction.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByAuctionId(Long auctionId);
    Optional<Payment> findByStripePaymentIntentId(String intentId);
}
