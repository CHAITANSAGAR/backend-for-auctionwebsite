package com.auction.websocket;

import com.auction.dto.request.PlaceBidRequest;
import com.auction.dto.response.BidResponse;
import com.auction.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class BidWebSocketController {

    private final BidService bidService;

    @MessageMapping("/auction/{auctionId}/bid")
    public void handleLiveBid(@DestinationVariable Long auctionId, @Payload PlaceBidRequest request, Principal principal) {
        String username = principal != null ? principal.getName() : "guest";
        bidService.placeBid(request, username);
    }
}
