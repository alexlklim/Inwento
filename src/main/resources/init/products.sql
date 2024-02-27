
INSERT IGNORE INTO products (is_active, created, updated, title, description, price, bar_code, rfid_code,
                             created_by_id, liable_id, receiver,
                             kst_id, asset_status_id, unit_id, type_id, subtype_id,
                             producer, supplier, is_scrapping,
                             branch_id, mpk_id,
                             document, document_date, warranty_period, inspection_date, last_inventory_date,
                             longitude, latitude)
VALUES
    (true, NOW(), NOW(), 'Lenovo Idea-pad Gaming', 'It is a very good laptop',4000.00,'INV123ee', '12345678',  @user_id, @user_id, 'Tetiana Klimenko',
     1, 2, 4, 1, 1, 'producer1', 'supplier1', false, 1, 1,'Sample document content...', '2024-02-19', '2025-02-19', '2024-03-19', '2023-12-31',  40.7128, -74.0060),
    (true, NOW(), NOW(), 'Samsung Galaxy', 'It is a very good phone',2000.00,'INV123455', '12345638',  @user_id, @user_id, 'Tetiana Klimenko',
     1, 2, 4, 1, 1, 'producer1', 'supplier1', false, 1, 1,'Sample document content...', '2024-02-19', '2025-02-19', '2024-03-19', '2023-12-31',  40.7128, -74.0060),
    (true, NOW(), NOW(), 'ThinkPad', 'It is a very good laptop with sensor screen',5000.00,'INV123355', '12145638',  @user_id, @user_id, 'Łukasz Boryś',
     1, 2, 4, 1, 1, 'producer1', 'supplier1', false, 1, 1,'Sample document content...', '2024-02-19', '2025-02-19', '2024-03-19', '2023-12-31',  40.7128, -74.0060),
    (true, NOW(), NOW(), 'Biurko', 'It is a very good biurko',5000.00,'INV145456', '12346578',  @user_id, @user_id, 'Gabriella Walczyna',
     1, 2, 4, 1, 4, 'producer1', 'supplier1', false, 1, 1,'Sample document content...', '2024-02-19', '2025-02-19', '2024-03-19', '2023-12-31',  40.7128, -74.0060),
    (true, NOW(), NOW(), 'Hammer Construction', 'It is a very good phone with IP69',1500.00,'INV133456', '12445678',  @user_id, @user_id, 'Tetiana Klimenko',
     1, 2, 4, 1, 4, 'producer1', 'supplier1', false, 1, 1,'Sample document content...', '2024-02-19', '2025-02-19', '2024-03-19', '2023-12-31',  40.7128, -74.0060),
    (true, NOW(), NOW(), 'Tablica do pisania', 'It is a very good tablica do pisania',5000.00,'INV123456', '12345678',  @user_id, @user_id, 'Tetiana Klimenko',
     1, 2, 4, 1, 4, 'producer1', 'supplier1', false, 1, 1,'Sample document content...', '2024-02-19', '2025-02-19', '2024-03-19', '2023-12-31',  40.7128, -74.0060);



# select * from users;
# select * from ksts;
#
#
# INSERT INTO products (
#     is_active, created, updated,
#     title, description, price,
#     bar_code, rfid_code,
#
#     created_by_id, liable_id, receiver, kst_id, asset_status_id, unit_id,
#     branch_id, mpk_id, type_id, subtype_id, producer, supplier,
#     is_scrapping, scrapping_date, scrapping_reason,
#     document, document_date, warranty_period, inspection_date,
#     last_inventory_date, longitude, latitude
# ) VALUES (
#     true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
#     'Product Title', 'Product Description', 99.99,
#     '123456789', 'RFID123',
#
#     2, 2, 'Tetiana Klimenko', 3, 4, 5,
#     6, 7, 8, 9, 'Producer Name', 'Supplier Name',
#     false, NULL, NULL,
#     'Document Text', '2024-02-27', '2024-12-31', '2024-06-30',
#     '2024-02-27', 0.0, 0.0
# );
#
#
#
#
#
# -- for product
# CREATE TABLE IF NOT EXISTS products (
#     id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
#     is_active BOOLEAN  NOT NULL, created DATETIME, updated DATETIME,
#     title VARCHAR(255), description TEXT, price DOUBLE,
#     bar_code VARCHAR(255) UNIQUE, rfid_code VARCHAR(255) UNIQUE,
#     created_by_id BIGINT, liable_id BIGINT, receiver VARCHAR(255),
#     kst_id BIGINT, asset_status_id BIGINT, unit_id BIGINT,
#     branch_id BIGINT, mpk_id BIGINT, type_id BIGINT, subtype_id BIGINT,
#     producer VARCHAR(255), supplier VARCHAR(255),
#     is_scrapping BOOLEAN,
#     scrapping_date DATETIME,
#     scrapping_reason VARCHAR(255),
#     document TEXT, document_date DATE, warranty_period DATE, inspection_date DATE,
#     last_inventory_date DATE,
#     longitude DOUBLE, latitude DOUBLE,
#     FOREIGN KEY (created_by_id) REFERENCES users(id),
#     FOREIGN KEY (liable_id) REFERENCES users(id),
#     FOREIGN KEY (kst_id) REFERENCES ksts(id),
#     FOREIGN KEY (asset_status_id) REFERENCES asset_statuses(id),
#     FOREIGN KEY (unit_id) REFERENCES units(id),
#     FOREIGN KEY (type_id) REFERENCES types(id),
#     FOREIGN KEY (subtype_id) REFERENCES subtypes(id),
#     FOREIGN KEY (branch_id) REFERENCES branches(id),
#     FOREIGN KEY (mpk_id) REFERENCES mpks(id));
