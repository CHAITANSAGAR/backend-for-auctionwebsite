package com.auction.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auction_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id", nullable = false)
    private Auction auction;

    @Column(nullable = false, length = 50)
    private String actionType;

    private Long performedBy;

    @Column(columnDefinition = "JSON")
    private String oldState;

    @Column(columnDefinition = "JSON")
    private String newState;

    private String ipAddress;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
