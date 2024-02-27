-- insert default values for AssetStatus, KST, Unit
-- tables will be created and data inserted into db after application is run

INSERT IGNORE INTO asset_statuses (is_active, asset_status)
VALUES  (true, 'w użytkowaniu'), (true, 'w magazynie'), (true, 'w montażu'), (true, 'wycofane z użytkowania'),
        (true, 'przeznaczone do sprzedaży'), (true, 'sprzedane'), (true, 'zniszczone lub utracone');

INSERT IGNORE INTO units (is_active, unit)
VALUES (true, 'szt.'), (true, 'm'), (true, 'm2'), (true, 'm3'), (true, 'kg'), (true, 'kpl.'), (true, 'para');





INSERT IGNORE INTO users (is_active, firstname, lastname, email, password, last_activity, created, updated, roles)
VALUES
    (TRUE, 'Admin', 'Admin', 'admin@gmail.com', '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2', NOW(), NOW(), NOW(), 'ADMIN'),
    (TRUE, 'Alex', 'Klim', 'alex@gmail.com', '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2', NOW(), NOW(), NOW(), 'EMP');



-- Insert a company into the companies table
INSERT IGNORE INTO company (created, updated, company, info, country, city, address, owner_id)
VALUES (NOW(), NOW(), 'Cyfore Sieci Multimedialne', 'Some information about the company',
        'Poland', 'Stalowa Wola', 'Kwiatkowskiego 1', (SELECT id FROM users WHERE email = 'admin@gmail.com') );

SET @company_name = 'Cyfore Sieci Multimedialne';
SET @user_email = 'admin@gmail.com';
SET @user_id = (SELECT id FROM users WHERE email = @user_email);

-- add fields to copmapny
INSERT IGNORE INTO branches (is_active, branch)
VALUES
    (true, 'IT Department'),
    (true, 'Production'),
    (true, 'Directory'),
    (true, 'Logistics'),
    (true, 'Financial');

INSERT IGNORE INTO mpks (is_active, mpk)
VALUES
    (true, 'MPK1'),
    (true, 'MPK2'),
    (true, 'MPK3'),
    (true, 'MPK4'),
    (true, 'MPK5');



INSERT IGNORE INTO types (is_active, type)
VALUES
    (true, 'Office Equipment'),
    (true, 'Machinery'),
    (true, 'Tools and Instruments'),
    (true, 'Electronics'),
    (true, 'Furniture');


SET @type1 = (SELECT id FROM types WHERE type = 'Office Equipment');
SET @type2 = (SELECT id FROM types WHERE type = 'Machinery');
SET @type3 = (SELECT id FROM types WHERE type = 'Tools and Instruments');
SET @type4 = (SELECT id FROM types WHERE type = 'Electronics');
SET @type5 = (SELECT id FROM types WHERE type = 'Furniture');



INSERT IGNORE INTO subtypes (is_active, subtype, type_id)
VALUES
    (true, 'Computers', @type1),
    (true, 'Printers', @type1),
    (true, 'Fax machines', @type1);

INSERT IGNORE INTO subtypes (is_active, subtype, type_id)
VALUES
    (true, 'Manufacturing equipment', @type2),
    (true, 'Construction machinery', @type2),
    (true, 'Industrial robots', @type2);


INSERT IGNORE INTO subtypes (is_active, subtype, type_id)
VALUES
    (true, 'Power tools', @type3),
    (true, 'Hand tools', @type3),
    (true, 'Laboratory equipment', @type3);

INSERT IGNORE INTO subtypes (is_active, subtype, type_id)
VALUES
    (true, 'Televisions', @type4),
    (true, 'Audiovisual equipment', @type4),
    (true, 'Cameras', @type4);

INSERT IGNORE INTO subtypes (is_active, subtype, type_id)
VALUES
    (true, 'Desks', @type5),
    (true, 'Chairs', @type5),
    (true, 'Shelving units', @type5);

