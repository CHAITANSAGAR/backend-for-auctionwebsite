-- V4__add_constraints.sql
-- Integrity Constraints & Anti-Sniping Protection Rules

ALTER TABLE bids ADD CONSTRAINT chk_bid_positive CHECK (amount > 0);
ALTER TABLE items ADD CONSTRAINT chk_starting_price CHECK (starting_price >= 0);
