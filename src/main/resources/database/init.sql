
INSERT IGNORE INTO asset_statuses (id, is_active, asset_status) VALUES
(1, true, 'w użytkowaniu'),
(2, true, 'w magazynie'),
(3, true, 'w montażu'),
(4, true, 'wycofane z użytkowania'),
(5, true, 'przeznaczone do sprzedaży'),
(6, true, 'sprzedane'),
(7, true, 'zniszczone lub utracone');

INSERT IGNORE INTO units (id, is_active, unit) VALUES
(1, true, 'szt.'),
(2, true, 'm'),
(3, true, 'm2'),
(4, true, 'm3'),
(5, true, 'kg'),
(6, true, 'kpl.'),
(7, true, 'para');



INSERT IGNORE INTO users (id, is_active, firstname, lastname, phone, email, password, last_activity, created, updated, roles)
VALUES
(1, TRUE, 'Alex', 'Klim', '48 887 754 491', 'admin@gmail.com', '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2', NOW(), NOW(), NOW(), 'ADMIN'),
(2, TRUE, 'Den', 'Morgan', '48 887 754 491', 'alex@gmail.com', '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2', NOW(), NOW(), NOW(), 'EMP');


-- Insert a company into the companies table
INSERT IGNORE INTO company (id, created, updated, is_email_configured, company, city, street, zip_code, nip, regon, phone, email)
VALUES
(1,NOW(), NOW(), false, 'Cyfore Sieci Multimedialne','Warsaw', 'Kwiatkowskiego 2', '23-345','33232323', '434343', '4800585885', 'alex@gmail.com');


INSERT IGNORE INTO branches (id, is_active, branch)
VALUES
    (1, true, 'IT Department'),
    (2, true, 'Production'),
    (3, true, 'Directory'),
    (4, true, 'Logistics'),
    (5, true, 'Financial');

INSERT IGNORE INTO mpks (id, is_active, mpk)
VALUES
    (1, true, 'MPK1'),
    (2, true, 'MPK2'),
    (3, true, 'MPK3'),
    (4, true, 'MPK4'),
    (5, true, 'MPK5');


INSERT IGNORE INTO types (id, is_active, type)
VALUES
    (1, true, 'Office Equipment'),
    (2, true, 'Machinery'),
    (3, true, 'Tools and Instruments'),
    (4, true, 'Electronics'),
    (5, true, 'Furniture');


INSERT IGNORE INTO subtypes (id, is_active, subtype, type_id)
VALUES
    (1, true, 'Computers', 1),
    (2, true, 'Printers', 1),
    (3, true, 'Fax machines', 1),

    (4, true, 'Manufacturing equipment', 2),
    (5, true, 'Construction machinery', 2),
    (6, true, 'Industrial robots', 2),

    (7, true, 'Power tools', 3),
    (8, true, 'Hand tools', 3),
    (9, true, 'Laboratory equipment', 3),

    (10, true, 'Televisions', 4),
    (11, true, 'Audiovisual equipment', 4),
    (12, true, 'Cameras', 4),

    (13, true, 'Desks', 5),
    (14, true, 'Chairs', 5),
    (15, true, 'Shelving units', 5);
