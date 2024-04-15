INSERT IGNORE INTO products (id, is_active, title, description, price, bar_code, rfid_code, inventory_number, serial_number,
                      created_by_id, liable_id, receiver, kst_id, asset_status_id, unit_id, branch_id, location_id, mpk_id,
                      type_id, subtype_id, producer, supplier, is_scrapping, scrapping_date, scrapping_reason,
                      document, document_date, warranty_period, inspection_date, longitude, latitude)
VALUES
    (1, TRUE, 'Laptop - Dell XPS 15', 'High-performance laptop with a sleek design', 1500.00,
     '123456789g0123', 'RFID1323', 'INV123', 'SERIAL123',
     1, 2, 'John Doe', 1, 1, 1, 1, 1, 1,
     1, 1, 'Dell', 'Dell Inc.', FALSE, NULL, NULL,
     'Purchase Invoice #1234', '2023-01-15', '2024-01-15', '2025-01-15', 40.7128, -74.0060);


