package com.auction.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.UUID;

@Service
public class StripeService {

    public String createCharge(BigDecimal amount, String customerEmail) {
        // Mock Stripe PaymentIntent charge generator
        return "pi_" + UUID.randomUUID().toString().replace("-", "").substring(0, 24);
    }
}
