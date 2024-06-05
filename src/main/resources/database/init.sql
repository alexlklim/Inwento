INSERT IGNORE INTO asset_statuses (id, is_active, asset_status)
VALUES (1, true, 'w użytkowaniu'),
       (2, true, 'w magazynie'),
       (3, true, 'w montażu'),
       (4, true, 'wycofane z użytkowania'),
       (5, true, 'przeznaczone do sprzedaży'),
       (6, true, 'sprzedane'),
       (7, true, 'zniszczone lub utracone'),
       (8, true, 'w serwisie');

INSERT IGNORE INTO units (id, is_active, unit)
VALUES (1, true, 'szt.'),
       (2, true, 'm'),
       (3, true, 'm2'),
       (4, true, 'm3'),
       (5, true, 'kg'),
       (6, true, 'kpl.'),
       (7, true, 'para');


INSERT IGNORE INTO users (id, is_active, firstname, lastname, phone, email, password,
                          last_activity, created, updated, roles)
VALUES (1, TRUE, 'Rafał', 'Kusz', '48 887 754 491', 'r.kusz@csmm.pl',
        '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2', NOW(), NOW(), NOW(), 'ADMIN'),
       (2, TRUE, 'Łukasz', 'Boryś', '48 887 754 491', 'l.borys@csmm.pl',
        '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2', NOW(), NOW(), NOW(), 'ADMIN'),
       (3, TRUE, 'Tetiana', 'Klimenko', '48 887 754 491', 't.klimenko@csmm.pl',
        '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2', NOW(), NOW(), NOW(), 'ADMIN'),
       (4, TRUE, 'Gabriella', 'Krawiec', '48 887 754 491', 'g.krawiec@csmm.pl',
        '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2', NOW(), NOW(), NOW(), 'ADMIN'),
       (5, TRUE, 'Gabriella', 'Walczyna', '48 887 754 491', 'g.walczyna@csmm.pl',
        '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2', NOW(), NOW(), NOW(), 'ADMIN'),
       (7, TRUE, 'Aneta', 'Ziarno', '48 887 754 491', 'a.ziarno@csmm.pl',
        '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2', NOW(), NOW(), NOW(), 'EMP'),
       (8, TRUE, 'Jakub', 'Pelczar', '48 887 754 491', 'j.pelczar@csmm.pl',
        '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2', NOW(), NOW(), NOW(), 'EMP'),
       (10, TRUE, 'Katarzyna', 'Bielak', '48 887 754 491', 'k.bielak@delegate.pl',
        '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2', NOW(), NOW(), NOW(), 'EMP'),
       (14, TRUE, 'Alex', 'Klim', '48 887 754 491', 'admin@gmail.com',
        '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2', NOW(), NOW(), NOW(), 'ADMIN');


-- Insert a company into the companies table
INSERT IGNORE INTO company (id, created, updated, is_email_configured, company,
                            city, street, zip_code, nip, regon, phone, email)
VALUES (1, NOW(), NOW(), false, 'Cyfore Sieci Multimedialne',
        'Warsaw', 'Kwiatkowskiego 2', '23-345', '33232323', '434343', '4800585885', 'csmm@gmail.com');


INSERT IGNORE INTO branches (id, is_active, branch)
VALUES (1, true, 'CSMM'),
       (2, true, 'DELEGATE'),
       (3, true, 'GLOBLOGIC');


INSERT IGNORE INTO locations (id, is_active, location, branch_id)
VALUES (1, true, 'conference', 1),
       (2, true, 'elektomobilność', 1),
       (3, true, 'socjalny', 1),
       (4, true, 'marketing', 1),
       (5, true, 'prezes', 1),
       (6, true, 'księgołość', 1),
       (7, true, 'lukasz', 1),
       (8, true, 'sekretariat', 1),
       (9, true, 'pusty', 1),
       (10, true, 'kokoko', 2);

INSERT IGNORE INTO mpks (id, is_active, mpk)
VALUES (1, true, 'CSMM'),
       (2, true, 'DELEGATE'),
       (3, true, 'GLOBLOGIC');


INSERT IGNORE INTO types (id, is_active, type)
VALUES (1, true, 'Sprzęt biurowy'),
       (2, true, 'Elektronika'),
       (3, true, 'Meble');


INSERT IGNORE INTO subtypes (id, is_active, subtype, type_id)
VALUES (1, true, 'Komputery', 1),
       (2, true, 'Drukarki', 1),
       (3, true, 'Telefony stacjonarne', 1),
       (4, true, 'Monitory', 1),

       (5, true, 'Laptop', 2),
       (6, true, 'Smartphone', 2),

       (13, true, 'Biurko', 3),
       (14, true, 'Fotel', 3),
       (15, true, 'Szafa', 3),
       (15, true, 'Sprzęt kuchenny', 3);


-- Insert data into service_provider table
INSERT IGNORE INTO service_provider (id, is_active, created, updated, company, nip, address)
VALUES (1, true, NOW(), NOW(), 'Tech Solutions', '1234567890', '123 Tech Street'),
       (2, true, NOW(), NOW(), 'Business Corp', '0987654321', '456 Business Ave'),
       (3, true, NOW(), NOW(), 'IT Services', '1122334455', '789 IT Blvd');

-- Insert data into contact_person table
INSERT IGNORE INTO contact_person (id, is_active, created, updated, firstname, lastname, phone_number, email, service_provider_id)
VALUES
    (1, true, NOW(), NOW(), 'John', 'Doe', '555-1234', 'john.doe@example.com', 1),
    (2, true, NOW(), NOW(), 'Jane', 'Smith', '555-5678', 'jane.smith@example.com', 2),
    (3, true, NOW(), NOW(), 'Alice', 'Johnson', '555-9012', 'alice.johnson@example.com', 3),
    (4, true, NOW(), NOW(), 'Bob', 'Brown', '555-3456', 'bob.brown@example.com', 3),
    (5, true, NOW(), NOW(), 'Eva', 'Garcia', '555-7890', 'eva.garcia@example.com', 3),
    (6, true, NOW(), NOW(), 'Michael', 'Lee', '555-2345', 'michael.lee@example.com', 2),
    (7, true, NOW(), NOW(), 'Samantha', 'Taylor', '555-6789', 'samantha.taylor@example.com', 2);



select * from contact_person;