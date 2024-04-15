
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


INSERT IGNORE INTO users (id, is_active, firstname, lastname, phone, email, password,
                          last_activity, created, updated, roles)
VALUES
(1, TRUE, 'Rafał', 'Kusz', '48 887 754 491', 'rafal@gmail.com', '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2', NOW(), NOW(), NOW(), 'ADMIN'),
(2, TRUE, 'Łukasz', 'Boryś', '48 887 754 491', 'lukasz@gmail.com', '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2', NOW(), NOW(), NOW(), 'ADMIN'),
(3, TRUE, 'Tetiana', 'Klimenko', '48 887 754 491', 'tetiana@gmail.com', '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2', NOW(), NOW(), NOW(), 'ADMIN'),
(4, TRUE, 'Gabriella', 'Krawiec', '48 887 754 491', 'gaby@gmail.com', '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2', NOW(), NOW(), NOW(), 'ADMIN'),
(5, TRUE, 'Gabriella', 'Walczyna', '48 887 754 491', 'gabi@gmail.com', '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2', NOW(), NOW(), NOW(), 'ADMIN'),
(6, TRUE, 'Przemysław', 'Chciuk', '48 887 754 491', 'przemek@gmail.com', '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2', NOW(), NOW(), NOW(), 'ADMIN'),
(7, TRUE, 'Aneta', 'Ziarno', '48 887 754 491', 'aneta@gmail.com', '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2', NOW(), NOW(), NOW(), 'EMP'),
(8, TRUE, 'Jakub', 'Pelczar', '48 887 754 491', 'jakub@gmail.com', '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2', NOW(), NOW(), NOW(), 'EMP'),
(9, TRUE, 'Julita', 'Niebudkowska-Szczoczarz', '48 887 754 491', 'julita@gmail.com', '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2', NOW(), NOW(), NOW(), 'EMP'),
(10, TRUE, 'Katarzyna', 'Bielak', '48 887 754 491', 'katarzyna@gmail.com', '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2', NOW(), NOW(), NOW(), 'EMP'),
(11, TRUE, 'Natatia', 'Olejarczyk', '48 887 754 491', 'natalia@gmail.com', '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2', NOW(), NOW(), NOW(), 'EMP'),
(12, TRUE, 'Patrycja', 'Skabińska', '48 887 754 491', 'patrycjia@gmail.com', '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2', NOW(), NOW(), NOW(), 'EMP'),
(13, TRUE, 'Renate', 'Cebula', '48 887 754 491', 'renate@gmail.com', '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2', NOW(), NOW(), NOW(), 'EMP'),
(14, TRUE, 'Alex', 'Klim', '48 887 754 491', 'admin@gmail.com', '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2', NOW(), NOW(), NOW(), 'ADMIN');


-- Insert a company into the companies table
INSERT IGNORE INTO company (
    id, created, updated, is_email_configured, company,
    city, street, zip_code, nip, regon, phone, email)
VALUES
    (1,NOW(), NOW(), false, 'Cyfore Sieci Multimedialne',
    'Warsaw', 'Kwiatkowskiego 2', '23-345','33232323', '434343', '4800585885', 'alex@gmail.com');


INSERT IGNORE INTO branches (id, is_active, branch)
VALUES
    (1, true, 'Stalowa Wola'),
    (2, true, 'Katowice');


INSERT IGNORE INTO locations (id, is_active, location, branch_id)
VALUES
    (1, true, 'pokój Anety', 1),
    (2, true, 'pokój Prezesa', 1),
    (3, true, 'pokój Łukasza', 1),
    (4, true, 'Marketing', 1),
    (4, true, 'Sekretariat', 1),
    (4, true, 'Sekretariat 2', 1),
    (4, true, 'sala konferencyjna', 1),
    (4, true, 'socjalny', 1),
    (4, true, 'księgołość', 1);

INSERT IGNORE INTO mpks (id, is_active, mpk)
VALUES
    (1, true, 'CSMM'),
    (2, true, 'DELEGATE'),
    (3, true, 'GLOBLOGIC');


INSERT IGNORE INTO types (id, is_active, type)
VALUES
    (1, true, 'Sprzęt biurowy'),
    (2, true, 'Elektronika'),
    (3, true, 'Meble');


INSERT IGNORE INTO subtypes (id, is_active, subtype, type_id)
VALUES
    (1, true, 'Komputery', 1),
    (2, true, 'Drukarki', 1),
    (3, true, 'Telefony stacjonarne', 1),
    (4, true, 'Monitory', 1),

    (5, true, 'Laptop', 2),
    (6, true, 'Smartphone', 2),

    (13, true, 'Biurko', 3),
    (14, true, 'Fotel', 3),
    (15, true, 'Szafa', 3),
    (15, true, 'Sprzęt kuchenny', 3);


select * from users;