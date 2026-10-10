package com.auction.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    public void sendAuctionWonNotification(String toEmail, String auctionTitle, double winningBid) {
        log.info("Sending Auction Won Email to {}: You won '{}' for ${}", toEmail, auctionTitle, winningBid);
    }

    public void sendOutbidAlert(String toEmail, String auctionTitle, double newHighBid) {
        log.info("Sending Outbid Alert to {}: New high bid on '{}' is ${}", toEmail, auctionTitle, newHighBid);
    }
}
