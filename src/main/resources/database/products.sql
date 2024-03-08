
INSERT IGNORE INTO products (is_active, created, updated, title, description, price,
                             bar_code, rfid_code, inventory_number, serial_number,
                             created_by_id, liable_id, receiver,
                             kst_id, asset_status_id, unit_id,
                             branch_id, mpk_id,
                             type_id, subtype_id,
                             producer, supplier,
                             is_scrapping,
                             document, document_date, warranty_period, inspection_date, last_inventory_date,
                             longitude, latitude)
VALUES
    (true, NOW(), NOW(), 'Lenovo Idea-pad Gaming', 'It is a very good laptop',4000.00,
     'bar123', 'rfid123','inv123', 'serial123',  @user_id, @user_id, 'Tetiana Klimenko',
     1, 2, 4, 1, 1, 1, 1, 'producer1', 'supplier' , false,
     'Sample document content...', '2024-02-19', '2025-02-19', '2024-03-19', '2023-12-31',  40.7128, -74.0060),
    (true, NOW(), NOW(), 'Samsung Galaxy', 'It is a very good laptop',4000.00,
     'bar1234', 'rfid1234','inv1234', 'serial1234',  @user_id, @user_id, 'Tetiana Klimenko',
     1, 2, 4, 1, 1, 1, 1, 'producer1', 'supplier' , false,
     'Sample document content...', '2024-02-19', '2025-02-19', '2024-03-19', '2023-12-31',  40.7128, -74.0060),
    (true, NOW(), NOW(), 'Laptop DELL', 'It is a very good laptop',4000.00,
     'bar1235', 'rfid1235','inv1235', 'serial1235',  @user_id, @user_id, 'Tetiana Klimenko',
     1, 2, 4, 1, 1, 1, 1, 'producer1', 'supplier' , false,
     'Sample document content...', '2024-02-19', '2025-02-19', '2024-03-19', '2023-12-31',  40.7128, -74.0060);



