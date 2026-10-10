-- V3__add_audit_table.sql
-- Audit Trail for Bids, State Transitions, and Security Events

CREATE TABLE IF NOT EXISTS auction_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    auction_id BIGINT NOT NULL,
    action_type VARCHAR(50) NOT NULL,
    performed_by BIGINT,
    old_state JSON,
    new_state JSON,
    ip_address VARCHAR(45),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_history_auction FOREIGN KEY (auction_id) REFERENCES auctions(id) ON DELETE CASCADE
);

CREATE INDEX idx_history_auction ON auction_history(auction_id, created_at DESC);
