-- insert default values for AssetStatus, KST, Unit

INSERT INTO asset_statuses (asset_status)
VALUES('Nowa'),('W trakcie przypisania'),('Przypisana'),('W trakcie przeglądu'),('W naprawie'),
      ('W transporcie'),('Wycofany'),('Utracony lub skradziony'),('Sprzedany lub przekazany'),
      ('Zarezerwowany');


INSERT INTO ksts (kst)
VALUES ('GRUNTY'),
    ('BUDYNKI I LOKALE ORAZ SPÓŁDZIELCZE PRAWO DO LOKALU UŻYTKOWEGO I SPÓŁDZIELCZE WŁASNOŚCIOWE PRAWO DO LOKALU MIESZKALNEGO'),
    ('OBIEKTY INŻYNIERII LĄDOWEJ I WODNEJ'),  ('KOTŁY I MASZYNY ENERGETYCZNE'),
    ('MASZYNY, URZĄDZENIA I APARATY OGÓLNEGO ZASTOSOWANIA'),  ('MASZYNY, URZĄDZENIA I APARATY SPECJALISTYCZNE'),
    ('URZĄDZENIA TECHNICZNE'), ('ŚRODKI TRANSPORTU'),
    ('NARZĘDZIA, PRZYRZĄDY, RUCHOMOŚCI I WYPOSAŻENIE, GDZIE INDZIEJ NIESKLASYFIKOWANE'),
    ('INWENTARZ ŻYWY');


INSERT INTO units (unit)
VALUES ('PIECE'), ('BOX'), ('PACK'), ('SET'), ('PAIR'), ('DOZEN'), ('BOTTLE'), ('ROLL');


-- create default user with ROLE_ADMIN
INSERT INTO users (firstname, lastname, email, password, isEnabled, createdAt, updatedAt, roles)
VALUES('Alex', 'Klim', 'alex@google.com', '$2a$10$G7/RXIL6FTjldvXU60lM9OkZNH/DeniXHbskTUyQ7lVpU/C..Aeb2',
       true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ADMIN');


