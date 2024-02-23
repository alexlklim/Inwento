DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS subtypes;
DROP TABLE IF EXISTS types;

-- Drop foreign key constraints
ALTER TABLE companies DROP FOREIGN KEY companies_ibfk_1;
ALTER TABLE users DROP FOREIGN KEY fk_company_id;

-- Drop tables
DROP TABLE IF EXISTS companies;
DROP TABLE IF EXISTS users;



DROP TABLE IF EXISTS token;
DROP TABLE IF EXISTS users;

DROP TABLE IF EXISTS company_asset_status;
DROP TABLE IF EXISTS company_kst;
DROP TABLE IF EXISTS company_unit;

DROP TABLE IF EXISTS asset_statuses;
DROP TABLE IF EXISTS ksts;
DROP TABLE IF EXISTS units;

DROP TABLE IF EXISTS branches;
DROP TABLE IF EXISTS mpks;
DROP TABLE IF EXISTS producers;
DROP TABLE IF EXISTS suppliers;

DROP TABLE IF EXISTS companies;



