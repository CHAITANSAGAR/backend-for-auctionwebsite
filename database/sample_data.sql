-- Sample Data Seeding for Auction Platform
USE auction_db;

-- 1. Users (Passwords hashed for: 'Password123!')
INSERT INTO users (id, username, email, password_hash, full_name, phone_number, role, is_verified, is_active) VALUES
(1, 'admin', 'admin@auctionplatform.com', '$2a$10$wK1L8o9Qk9Vl8y3d8s7jveoU3WvA9r1sP0kZ2M4N7v9Q1xY8zT7q.', 'System Administrator', '+1-555-0100', 'ADMIN', TRUE, TRUE),
(2, 'victor_seller', 'seller@luxuryvault.io', '$2a$10$wK1L8o9Qk9Vl8y3d8s7jveoU3WvA9r1sP0kZ2M4N7v9Q1xY8zT7q.', 'Victor Vance (Verified Dealer)', '+1-555-0101', 'SELLER', TRUE, TRUE),
(3, 'elena_rolex', 'elena@chronocollector.com', '$2a$10$wK1L8o9Qk9Vl8y3d8s7jveoU3WvA9r1sP0kZ2M4N7v9Q1xY8zT7q.', 'Elena Rostova', '+1-555-0102', 'SELLER', TRUE, TRUE),
(4, 'marcus_buyer', 'marcus@investor.co', '$2a$10$wK1L8o9Qk9Vl8y3d8s7jveoU3WvA9r1sP0kZ2M4N7v9Q1xY8zT7q.', 'Marcus Chen', '+1-555-0103', 'BUYER', TRUE, TRUE),
(5, 'sarah_art', 'sarah@artgalleria.org', '$2a$10$wK1L8o9Qk9Vl8y3d8s7jveoU3WvA9r1sP0kZ2M4N7v9Q1xY8zT7q.', 'Sarah Jenkins', '+1-555-0104', 'BUYER', TRUE, TRUE);

-- 2. Items
INSERT INTO items (id, seller_id, title, description, category, item_condition, starting_price, reserve_price, min_increment) VALUES
(1, 2, '1967 Shelby GT500 Fastback - 428 Cobra Jet', 'Pristine concours-restored 1967 Shelby GT500 in Nightmist Blue with white Le Mans stripes. 428 Police Interceptor V8 engine, 4-speed manual Toploader transmission. Full Marti Report and SAAC Registry documentation included.', 'Vehicles', 'LIKE_NEW', 120000.00, 165000.00, 2500.00),
(2, 3, 'Rolex Daytona "Paul Newman" Ref. 6239 (1969)', 'Extremely rare vintage Rolex Cosmograph Daytona Ref. 6239 featuring the iconic exotic white dial with black step sub-dials. Caliber 722 manual-wind movement, original riveted Oyster bracelet with box and papers.', 'Watches & Jewelry', 'LIKE_NEW', 95000.00, 140000.00, 1000.00),
(3, 2, 'Claude Monet - "Bassin aux Nympheas" (Oil Study, 1917)', 'Certified museum-grade oil on canvas study with documented provenance through Durand-Ruel Paris. Beautiful violet, azure, and emerald impressionist brushwork with archival UV-protective museum glazing.', 'Art & Antiques', 'GOOD', 240000.00, 310000.00, 5000.00),
(4, 3, '1st Edition Shadowless Charizard Holo #4 PSA 10 Gem Mint', 'The holy grail of modern pop culture collectibles. 1999 Base Set 1st Edition Shadowless Charizard graded PSA 10 Gem Mint with pristine centering, flawless foil surface, and zero holo scratches.', 'Collectibles', 'NEW', 150000.00, 200000.00, 2000.00),
(5, 2, 'Leica MP "Anthracite" Special Edition 35mm Rangefinder #042/100', 'One of only 100 ever crafted by Leica Camera AG Wetzlar. Features anthracite lacquered titanium-grade top and bottom plates paired with a matching Summilux-M 35mm f/1.4 ASPH lens.', 'Electronics', 'LIKE_NEW', 18500.00, 24000.00, 500.00),
(6, 3, 'Roman Emperor Marcus Aurelius Gold Aureus (161-180 AD) NGC Ch AU', 'Exceptional ancient Roman Imperial gold coinage. Strike 5/5, Surface 5/5 with Fine Style portrait of the Stoic Philosopher Emperor Marcus Aurelius draped in military cuirass.', 'Collectibles', 'GOOD', 32000.00, 42000.00, 750.00);

-- 3. Auctions
INSERT INTO auctions (id, item_id, seller_id, current_highest_bid, current_highest_bidder_id, start_time, end_time, status, total_bids) VALUES
(1, 1, 2, 172500.00, 4, NOW() - INTERVAL 2 DAY, NOW() + INTERVAL 1 DAY + INTERVAL 4 HOUR, 'ACTIVE', 14),
(2, 2, 3, 148000.00, 5, NOW() - INTERVAL 1 DAY, NOW() + INTERVAL 2 HOUR + INTERVAL 45 MINUTE, 'ACTIVE', 28),
(3, 3, 2, 295000.00, 4, NOW() - INTERVAL 3 DAY, NOW() + INTERVAL 18 HOUR, 'ACTIVE', 9),
(4, 4, 3, 212000.00, 5, NOW() - INTERVAL 12 HOUR, NOW() + INTERVAL 2 DAY, 'ACTIVE', 19),
(5, 5, 2, 24500.00, 4, NOW() - INTERVAL 4 DAY, NOW() + INTERVAL 6 HOUR, 'ACTIVE', 8),
(6, 6, 3, 44250.00, 5, NOW() - INTERVAL 1 DAY, NOW() + INTERVAL 3 DAY, 'ACTIVE', 11);

-- 4. Bids
INSERT INTO bids (auction_id, bidder_id, amount, status, is_auto_bid, created_at) VALUES
(1, 5, 130000.00, 'OUTBID', FALSE, NOW() - INTERVAL 36 HOUR),
(1, 4, 145000.00, 'OUTBID', FALSE, NOW() - INTERVAL 24 HOUR),
(1, 5, 160000.00, 'OUTBID', FALSE, NOW() - INTERVAL 12 HOUR),
(1, 4, 172500.00, 'PLACED', TRUE, NOW() - INTERVAL 2 HOUR),
(2, 4, 110000.00, 'OUTBID', FALSE, NOW() - INTERVAL 18 HOUR),
(2, 5, 125000.00, 'OUTBID', FALSE, NOW() - INTERVAL 10 HOUR),
(2, 4, 138000.00, 'OUTBID', FALSE, NOW() - INTERVAL 4 HOUR),
(2, 5, 148000.00, 'PLACED', FALSE, NOW() - INTERVAL 15 MINUTE);

-- 5. Notifications
INSERT INTO notifications (user_id, title, message, type, related_auction_id) VALUES
(4, 'Outbid Alert', 'You have been outbid on "Rolex Daytona Paul Newman Ref. 6239". New high bid is $148,000.', 'OUTBID', 2),
(5, 'High Bidder Notice', 'You are currently the leading bidder on "Rolex Daytona Paul Newman Ref. 6239" at $148,000.', 'BID_PLACED', 2),
(2, 'Reserve Met!', 'The reserve price for "1967 Shelby GT500 Fastback" ($165,000) has been met!', 'RESERVE_MET', 1);
