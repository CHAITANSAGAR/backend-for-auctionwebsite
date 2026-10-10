package com.auction.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSocketEventPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public void publishBidUpdate(Long auctionId, Object payload) {
        messagingTemplate.convertAndSend("/topic/auction/" + auctionId, payload);
    }

    public void publishAuctionEnded(Long auctionId, Object payload) {
        messagingTemplate.convertAndSend("/topic/auction/" + auctionId + "/ended", payload);
    }

    public void sendUserNotification(String username, Object notification) {
        messagingTemplate.convertAndSendToUser(username, "/queue/notifications", notification);
    }
}
