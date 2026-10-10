-- V2__add_indexes.sql
-- Performance Indexes for High-Frequency Auction & Bidding Queries

CREATE INDEX idx_auctions_status ON auctions(status);
CREATE INDEX idx_auctions_end_time ON auctions(end_time);
CREATE INDEX idx_auctions_seller ON auctions(seller_id);
CREATE INDEX idx_bids_auction ON bids(auction_id, created_at DESC);
CREATE INDEX idx_bids_bidder ON bids(bidder_id);
CREATE INDEX idx_items_category ON items(category);
CREATE INDEX idx_notifications_user_unread ON notifications(user_id, is_read);
CREATE INDEX idx_payments_status ON payments(status);
