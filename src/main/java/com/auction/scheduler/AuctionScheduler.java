package com.auction.scheduler;

import com.auction.service.AuctionSchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuctionScheduler {

    private final AuctionSchedulerService schedulerService;

    @Scheduled(fixedRate = 10000) // check every 10 seconds
    public void processExpiredAuctions() {
        schedulerService.finalizeExpiredAuctions();
    }
}
