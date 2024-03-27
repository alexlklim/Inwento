INSERT IGNORE INTO products (id, is_active, title, description, price, bar_code, rfid_code, inventory_number, serial_number,
                      created_by_id, liable_id, receiver, kst_id, asset_status_id, unit_id, branch_id, mpk_id,
                      type_id, subtype_id, producer, supplier, is_scrapping, scrapping_date, scrapping_reason,
                      document, document_date, warranty_period, inspection_date, longitude, latitude)
VALUES
    (1, TRUE, 'Laptop - Dell XPS 15', 'High-performance laptop with a sleek design', 1500.00, '123456789g0123', 'RFID1323', 'INV123',
     'SERIAL123', 1, 2, 'John Doe', 1, 1, 1, 1, 1,
     1, 1, 'Dell', 'Dell Inc.', FALSE, NULL, NULL,
     'Purchase Invoice #1234', '2023-01-15', '2024-01-15', '2025-01-15', 40.7128, -74.0060),

    (2, TRUE, 'Printer - HP LaserJet Pro', 'High-speed laser printer for office use', 500.00, '234f678901234', 'RFID2334r4', 'INV234',
     'SERIAL234', 2, 1, 'Jane Smith', 2, 2, 2, 2,
     2, 2, 2, 'HP', 'HP Inc.', FALSE, NULL, NULL,
     'Purchase Invoice #5678', '2023-02-20', '2024-02-20', '2025-02-20', 34.0522, -118.2437),

    (3, TRUE, 'Power Drill - Bosch Professional', 'Cordless power drill with multiple attachments', 200.00, '345a6789f012345', 'RFID343rt5', 'INV345',
     'SERIAL345', 1, 1, 'Michael Johnson', 3, 3, 3, 3,
     3, 3, 3, 'Bosch', 'Bosch GmbH', FALSE, NULL, NULL,
     'Purchase Invoice #91011', '2023-03-25', '2024-03-25', '2025-03-25', 51.5074, -0.1278),
    (4, TRUE, 'Television - Samsung QLED 4K', 'High-definition smart TV with excellent picture quality', 1000.00, '456789012f345a6', 'RFID45tr36', 'INV456',
     'SERIAL456', 1, 2, 'Emma Davis', 4, 4, 4, 4,
     4, 4, 4, 'Samsung', 'Samsung Electronics', FALSE, NULL, NULL,
     'Purchase Invoice #121314', '2023-04-30', '2024-04-30', '2025-04-30', 35.6895, 139.6917),
    (5, TRUE, 'Chair - Herman Miller Aeron', 'Ergonomic office chair designed for comfort and support', 800.00, '5678s9012f34567', 'RFID563we7', 'INV567',
     'SERIAL567', 1, 2, 'William Brown', 5, 5, 5, 5,
     5, 5, 5, 'Herman Miller', 'Herman Miller Inc.', FALSE, NULL, NULL,
     'Purchase Invoice #151617', '2023-05-10', '2024-05-10', '2025-05-10', 40.7128, -74.0060),
    (6, TRUE, 'Smartphone - iPhone 13 Pro', 'Latest model of the iPhone series with advanced features', 1200.00, '6789f01g2345678', 'RFID673yw8', 'INV678',
     'SERIAL678', 1, 2, 'Sophia Wilson', 1, 1, 1, 1,
     1, 1, 1, 'Apple', 'Apple Inc.', FALSE, NULL, NULL,
     'Purchase Invoice #181920', '2023-06-15', '2024-06-15', '2025-06-15', 34.0522, -118.2437),
    (7, TRUE, 'Desk - IKEA MALM', 'Stylish and functional desk for home or office use', 300.00, '789012sf3456789', 'RFID7yy9', 'INV7839',
     'SERIAL789', 2, 1, 'Oliver Jones', 2, 2, 2, 2,
     2, 2, 2, 'IKEA', 'IKEA Group', FALSE, NULL, NULL,
     'Purchase Invoice #212223', '2023-07-20', '2024-07-20', '2025-07-20', 51.5074, -0.1278),
    (8, TRUE, 'Camera - Canon EOS R5', 'Professional mirrorless camera with high-resolution sensor', 2500.00, '890f1234f567890', 'RFID89t0', 'INV8930',
     'SERIAL890', 1, 1, 'Ava Taylor', 3, 3, 3, 3,
     3, 3, 3, 'Canon', 'Canon Inc.', FALSE, NULL, NULL,
     'Purchase Invoice #242526', '2023-08-25', '2024-08-25', '2025-08-25', 35.6895, 139.6917),
    (9, TRUE, 'Shelving Unit - Elfa Classic', 'Versatile shelving system for organizing storage spaces', 400.00, '9s0123456f78901', 'RFIDe901', 'INV901',
     'SERIAL901', 1, 2, 'Noah Clark', 4, 4, 4, 4,
     4, 4, 4, 'Elfa', 'Elfa International AB', FALSE, NULL, NULL,
     'Purchase Invoice #272829', '2023-09-30', '2024-09-30', '2025-09-30', 40.7128, -74.0060),
    (10, TRUE, 'Drone - DJI Mavic Air 2', 'Compact and powerful drone with intelligent flight features', 1500.00, '012345678e9012', 'RFID0r12', 'INV012',
     'SERIAL012', 1, 1, 'Liam Martinez', 5, 5, 5, 5,
     5, 5, 5, 'DJI', 'DJI Technology Co. Ltd.', FALSE, NULL, NULL,
     'Purchase Invoice #303132', '2023-10-15', '2024-10-15', '2025-10-15', 34.0522, -118.2437),

    (11, TRUE, 'Herbata', 'Compact and powerful drone with intelligent flight features', 1500.00, '5900396029898', 'RFID03r12', 'INV0172',
     'SERIAL0123', 1, 1, 'Liam Martinez', 5, 5, 5, 5,
     5, 5, 5, 'DJI', 'DJI Technology Co. Ltd.', FALSE, NULL, NULL,
     'Purchase Invoice #303132', '2023-10-15', '2024-10-15', '2025-10-15', 34.0522, -118.2437);



select * from products;

