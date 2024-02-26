-- insert default values for AssetStatus, KST, Unit
-- tables will be created and data inserted into db after application is run

INSERT IGNORE INTO asset_statuses (asset_status)
VALUES  ('Nowa'), ('W trakcie przypisania'), ('Przypisana'), ('W trakcie przeglądu'), ('W naprawie'),
    ('W transporcie'), ('Wycofany'),('Utracony lub skradziony'), ('Sprzedany lub przekazany'), ('Zarezerwowany');

INSERT IGNORE INTO units (unit)
VALUES ('PIECE'), ('BOX'), ('PACK'), ('SET'), ('PAIR'), ('DOZEN'), ('BOTTLE'), ('ROLL');

INSERT IGNORE INTO ksts (kst)
VALUES('GRUNTY'),
    ('BUDYNKI I LOKALE ORAZ SPÓŁDZIELCZE PRAWO DO LOKALU UŻYTKOWEGO I SPÓŁDZIELCZE WŁASNOŚCIOWE PRAWO DO LOKALU MIESZKALNEGO'),
    ('OBIEKTY INŻYNIERII LĄDOWEJ I WODNEJ'),  ('KOTŁY I MASZYNY ENERGETYCZNE'),
    ('MASZYNY, URZĄDZENIA I APARATY OGÓLNEGO ZASTOSOWANIA'),  ('MASZYNY, URZĄDZENIA I APARATY SPECJALISTYCZNE'),
    ('URZĄDZENIA TECHNICZNE'), ('ŚRODKI TRANSPORTU'),
    ('NARZĘDZIA, PRZYRZĄDY, RUCHOMOŚCI I WYPOSAŻENIE, GDZIE INDZIEJ NIESKLASYFIKOWANE'), ('INWENTARZ ŻYWY');




-- create default user with ROLE_ADMIN
INSERT IGNORE INTO users (firstname, lastname, email, password, is_enabled, created, updated, roles)
SELECT 'Admin', 'Admin', 'admin@gmail.com', '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2',
       true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ADMIN'
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@gmail.com');

-- Insert a company into the companies table
INSERT IGNORE INTO companies (active, created, updated, company, info, country, city, address,
                              secret_code, owner_id)
VALUES (true, NOW(), NOW(), 'Cyfore Sieci Multimedialne', 'Some information about the company',
        'Poland', 'Stalowa Wola', 'Kwiatkowskiego 1', UNHEX(REPLACE('e00b7c0e9a9044cf8db1f2cd9c80f3eb', '-', '')),
        (SELECT id FROM users WHERE email = 'admin@gmail.com') );

SET @company_name = 'Cyfore Sieci Multimedialne';
SET @user_email = 'admin@gmail.com';
SET @user_id = (SELECT id FROM users WHERE email = @user_email);
SET @company_id = (SELECT id FROM companies WHERE company = @company_name);


UPDATE users
SET company_id = @company_id
WHERE email = @user_email;




-- add units, ksts and asset statuses to company
INSERT IGNORE INTO company_asset_status (company_id, asset_status_id)
SELECT @company_id, id FROM asset_statuses;

INSERT IGNORE INTO company_kst (company_id, kst_id)
SELECT @company_id, id FROM ksts;

INSERT IGNORE INTO company_unit (company_id, unit_id)
SELECT @company_id, id FROM units;

-- add fields to copmapny
INSERT IGNORE INTO branches (active, branch, company_id)
VALUES
    (true, 'IT Department', @company_id),
    (true, 'Production', @company_id),
    (true, 'Directory', @company_id),
    (true, 'Logistics', @company_id),
    (true, 'Financial', @company_id);

INSERT IGNORE INTO mpks (active, mpk, company_id)
VALUES
    (true, 'MPK1', @company_id),
    (true, 'MPK2', @company_id),
    (true, 'MPK3', @company_id),
    (true, 'MPK4', @company_id),
    (true, 'MPK5', @company_id);

INSERT IGNORE INTO producers (active, producer, company_id)
VALUES
    (true, 'Apple Inc.', @company_id),
    (true, 'Procter & Gamble', @company_id),
    (true, 'Samsung Electronics Co., Ltd.', @company_id),
    (true, 'Coca-Cola Company', @company_id),
    (true, 'Tesla, Inc.', @company_id);

INSERT IGNORE INTO suppliers (active, supplier, company_id)
VALUES
    (true, 'Intel Corporation', @company_id),
    (true, 'Oracle Corporation', @company_id),
    (true, 'Amazon.com, Inc.', @company_id),
    (true, 'McKesson Corporation', @company_id),
    (true, 'Walmart Inc.', @company_id);


INSERT IGNORE INTO types (active, type, company_id)
VALUES
    (true, 'Office Equipment', @company_id),
    (true, 'Machinery', @company_id),
    (true, 'Tools and Instruments', @company_id),
    (true, 'Electronics', @company_id),
    (true, 'Furniture', @company_id);


SET @type1 = (SELECT id FROM types WHERE type = 'Office Equipment');
SET @type2 = (SELECT id FROM types WHERE type = 'Machinery');
SET @type3 = (SELECT id FROM types WHERE type = 'Tools and Instruments');
SET @type4 = (SELECT id FROM types WHERE type = 'Electronics');
SET @type5 = (SELECT id FROM types WHERE type = 'Furniture');



INSERT IGNORE INTO subtypes (active, subtype, company_id, type_id)
VALUES
    (true, 'Computers', @company_id, @type1),
    (true, 'Printers', @company_id, @type1),
    (true, 'Fax machines', @company_id, @type1);

INSERT IGNORE INTO subtypes (active, subtype, company_id, type_id)
VALUES
    (true, 'Manufacturing equipment', @company_id, @type2),
    (true, 'Construction machinery', @company_id, @type2),
    (true, 'Industrial robots', @company_id, @type2);


INSERT IGNORE INTO subtypes (active, subtype, company_id, type_id)
VALUES
    (true, 'Power tools', @company_id, @type3),
    (true, 'Hand tools', @company_id, @type3),
    (true, 'Laboratory equipment', @company_id, @type3);

INSERT IGNORE INTO subtypes (active, subtype, company_id, type_id)
VALUES
    (true, 'Televisions', @company_id, @type4),
    (true, 'Audiovisual equipment', @company_id, @type4),
    (true, 'Cameras', @company_id, @type4);

INSERT IGNORE INTO subtypes (active, subtype, company_id, type_id)
VALUES
    (true, 'Desks', @company_id, @type5),
    (true, 'Chairs', @company_id, @type5),
    (true, 'Shelving units', @company_id, @type5);


INSERT IGNORE INTO products (active, created, updated,title, description, price, bar_code, rfid_code,created_by_id, liable_id, receiver,
                             asset_status_id, kst_id, unit_id, type_id, subtype_id,producer_id, supplier_id, branch_id, mpk_id,
                             document, document_date, warranty_period, inspection_date, last_inventory_date, longitude, latitude,company_id)
VALUES
    (true, NOW(), NOW(), 'Lenove Ideapad Gaming', 'It is a very good laptop',4000.00,'INV123456', '12345678',  @user_id, @user_id, 'Tetiana Klimenko',
     1, 2, 4, 1, 1, 1, 1, 1, 1,'Sample document content...', '2024-02-19', '2025-02-19', '2024-03-19', '2023-12-31',  40.7128, -74.0060,@company_id),
    (true, NOW(), NOW(), 'Samsung Galaxy', 'It is a very good phone',2000.00,'INV123455', '12345638',  @user_id, @user_id, 'Tetiana Klimenko',
     1, 2, 4, 1, 1, 1, 1, 1, 1,'Sample document content...', '2024-02-19', '2025-02-19', '2024-03-19', '2023-12-31',  40.7128, -74.0060,@company_id),
    (true, NOW(), NOW(), 'ThinkPad', 'It is a very good laptop with sensor screen',5000.00,'INV123355', '12145638',  @user_id, @user_id, 'Łukasz Boryś',
     1, 2, 4, 1, 1, 1, 1, 1, 1,'Sample document content...', '2024-02-19', '2025-02-19', '2024-03-19', '2023-12-31',  40.7128, -74.0060,@company_id),
    (true, NOW(), NOW(), 'Biurko', 'It is a very good biurko',5000.00,'INV145456', '12346578',  @user_id, @user_id, 'Gabriella Walczyna',
     1, 2, 4, 1, 4, 1, 1, 1, 1,'Sample document content...', '2024-02-19', '2025-02-19', '2024-03-19', '2023-12-31',  40.7128, -74.0060,@company_id),
    (true, NOW(), NOW(), 'Hammer Cobnstruction', 'It is a very good phone with IP69',1500.00,'INV133456', '12445678',  @user_id, @user_id, 'Tetiana Klimenko',
     1, 2, 4, 1, 4, 1, 1, 1, 1,'Sample document content...', '2024-02-19', '2025-02-19', '2024-03-19', '2023-12-31',  40.7128, -74.0060,@company_id),
    (true, NOW(), NOW(), 'Tablica do pisania', 'It is a very good tabliza do pisania',5000.00,'INV123456', '12345678',  @user_id, @user_id, 'Tetiana Klimenko',
     1, 2, 4, 1, 4, 1, 1, 1, 1,'Sample document content...', '2024-02-19', '2025-02-19', '2024-03-19', '2023-12-31',  40.7128, -74.0060,@company_id);





#
# INSERT INTO token (created, expired, user_id)
# VALUES ( NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY ), @user_id);