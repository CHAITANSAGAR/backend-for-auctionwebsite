package com.auction.controller;

import com.auction.dto.request.CreatePaymentRequest;
import com.auction.dto.response.ApiResponse;
import com.auction.dto.response.PaymentResponse;
import com.auction.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Tag(name = "Payment Controller", description = "Stripe escrow payments and settlement")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    @Operation(summary = "Process Stripe payment for a won auction")
    public ResponseEntity<ApiResponse<PaymentResponse>> processPayment(
            @Valid @RequestBody CreatePaymentRequest request,
            Principal principal) {
        String username = principal != null ? principal.getName() : "winner_user";
        PaymentResponse response = paymentService.processEscrowPayment(request, username);
        return ResponseEntity.ok(ApiResponse.ok(response, "Escrow payment processed successfully"));
    }
}
