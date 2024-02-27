
INSERT IGNORE INTO products (is_active, created, updated, title, description, price, bar_code, rfid_code,
                             created_by_id, liable_id, receiver,
                             kst_id, asset_status_id, unit_id, type_id, subtype_id,
                             producer, supplier,
                             branch_id, mpk_id,
                             document, document_date, warranty_period, inspection_date, last_inventory_date,
                             longitude, latitude)
VALUES
    (true, NOW(), NOW(), 'Lenovo Idea-pad Gaming', 'It is a very good laptop',4000.00,'INV123ee', '12345678',  @user_id, @user_id, 'Tetiana Klimenko',
     1, 2, 4, 1, 1, 'producer1', 'supplier1', 1, 1,'Sample document content...', '2024-02-19', '2025-02-19', '2024-03-19', '2023-12-31',  40.7128, -74.0060),
    (true, NOW(), NOW(), 'Samsung Galaxy', 'It is a very good phone',2000.00,'INV123455', '12345638',  @user_id, @user_id, 'Tetiana Klimenko',
     1, 2, 4, 1, 1, 'producer1', 'supplier1', 1, 1,'Sample document content...', '2024-02-19', '2025-02-19', '2024-03-19', '2023-12-31',  40.7128, -74.0060),
    (true, NOW(), NOW(), 'ThinkPad', 'It is a very good laptop with sensor screen',5000.00,'INV123355', '12145638',  @user_id, @user_id, 'Łukasz Boryś',
     1, 2, 4, 1, 1, 'producer1', 'supplier1', 1, 1,'Sample document content...', '2024-02-19', '2025-02-19', '2024-03-19', '2023-12-31',  40.7128, -74.0060),
    (true, NOW(), NOW(), 'Biurko', 'It is a very good biurko',5000.00,'INV145456', '12346578',  @user_id, @user_id, 'Gabriella Walczyna',
     1, 2, 4, 1, 4, 'producer1', 'supplier1', 1, 1,'Sample document content...', '2024-02-19', '2025-02-19', '2024-03-19', '2023-12-31',  40.7128, -74.0060),
    (true, NOW(), NOW(), 'Hammer Construction', 'It is a very good phone with IP69',1500.00,'INV133456', '12445678',  @user_id, @user_id, 'Tetiana Klimenko',
     1, 2, 4, 1, 4, 'producer1', 'supplier1', 1, 1,'Sample document content...', '2024-02-19', '2025-02-19', '2024-03-19', '2023-12-31',  40.7128, -74.0060),
    (true, NOW(), NOW(), 'Tablica do pisania', 'It is a very good tablica do pisania',5000.00,'INV123456', '12345678',  @user_id, @user_id, 'Tetiana Klimenko',
     1, 2, 4, 1, 4, 'producer1', 'supplier1', 1, 1,'Sample document content...', '2024-02-19', '2025-02-19', '2024-03-19', '2023-12-31',  40.7128, -74.0060);



