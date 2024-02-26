
-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    is_enabled BOOLEAN  NOT NULL,
    created DATETIME NOT NULL,
    updated DATETIME NOT NULL,
    roles ENUM('ADMIN', 'CLIENT', 'EMP') NOT NULL,
    company_id BIGINT
);

-- Create companies table with foreign key reference to users table
CREATE TABLE IF NOT EXISTS companies (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    active BOOLEAN, created DATETIME, updated DATETIME,
    company VARCHAR(255) UNIQUE,
    info TEXT,
    country VARCHAR(255),
    city VARCHAR(255),
    address TEXT,
    secret_code BINARY(16) UNIQUE,
    owner_id BIGINT,
    FOREIGN KEY (owner_id) REFERENCES users(id)
);


ALTER TABLE users DROP FOREIGN KEY fk_company_id;

ALTER TABLE users
    ADD CONSTRAINT fk_company_id FOREIGN KEY (company_id) REFERENCES companies(id);




CREATE TABLE IF NOT EXISTS token (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    token BINARY(16),
    created DATETIME,
    expired DATETIME,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);



-- for AssetStatus, KST, Units
CREATE TABLE IF NOT EXISTS asset_statuses (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    asset_status VARCHAR(255) UNIQUE);

CREATE TABLE IF NOT EXISTS ksts (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    kst VARCHAR(255)UNIQUE );

CREATE TABLE IF NOT EXISTS units (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    unit VARCHAR(255) UNIQUE);



CREATE TABLE IF NOT EXISTS company_asset_status (
    company_id BIGINT NOT NULL,
    asset_status_id BIGINT NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE,
    FOREIGN KEY (asset_status_id) REFERENCES asset_statuses(id),
    PRIMARY KEY (company_id, asset_status_id)
);

CREATE TABLE IF NOT EXISTS company_kst (
    company_id BIGINT NOT NULL,
    kst_id BIGINT NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies(id),
    FOREIGN KEY (kst_id) REFERENCES ksts(id),
    PRIMARY KEY (company_id, kst_id)
);

CREATE TABLE IF NOT EXISTS company_unit (
    company_id BIGINT NOT NULL,
    unit_id BIGINT NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies(id),
    FOREIGN KEY (unit_id) REFERENCES units(id),
    PRIMARY KEY (company_id, unit_id)
);


-- for AssetStatus, KST, Units
CREATE TABLE IF NOT EXISTS asset_statuses (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    asset_status VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS ksts (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    kst VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS units (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    unit VARCHAR(255)
);

-- connection tables
CREATE TABLE IF NOT EXISTS company_asset_status (
    company_id BIGINT NOT NULL,
    asset_status_id BIGINT NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies(id),
    FOREIGN KEY (asset_status_id) REFERENCES asset_statuses(id),
    PRIMARY KEY (company_id, asset_status_id)
);

CREATE TABLE IF NOT EXISTS company_kst (
    company_id BIGINT NOT NULL,
    kst_id BIGINT NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies(id),
    FOREIGN KEY (kst_id) REFERENCES ksts(id),
    PRIMARY KEY (company_id, kst_id)
);

CREATE TABLE IF NOT EXISTS company_unit (
    company_id BIGINT NOT NULL,
    unit_id BIGINT NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies(id),
    FOREIGN KEY (unit_id) REFERENCES units(id),
    PRIMARY KEY (company_id, unit_id)
);




-- configured tables
CREATE TABLE IF NOT EXISTS branches (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    active BOOLEAN,
    branch VARCHAR(255),
    company_id BIGINT NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies(id),
    CONSTRAINT unique_per_company UNIQUE (company_id, branch)
);

CREATE TABLE IF NOT EXISTS mpks (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    active BOOLEAN,
    mpk VARCHAR(255),
    company_id BIGINT NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies(id),
    CONSTRAINT unique_per_company UNIQUE (company_id, mpk)
);

CREATE TABLE IF NOT EXISTS producers (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    active BOOLEAN,
    producer VARCHAR(255),
    company_id BIGINT NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies(id),
    CONSTRAINT unique_per_company UNIQUE (company_id, producer)
);

CREATE TABLE IF NOT EXISTS suppliers (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    active BOOLEAN,
    supplier VARCHAR(255),
    company_id BIGINT NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies(id),
    CONSTRAINT unique_per_company UNIQUE (company_id, supplier)
);


-- for types and subtypes
CREATE TABLE IF NOT EXISTS types (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    active BOOLEAN,
    type VARCHAR(255),
    company_id BIGINT NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies(id),
    CONSTRAINT unique_per_company UNIQUE (company_id, type)
);
CREATE TABLE IF NOT EXISTS subtypes (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    active BOOLEAN,
    subtype VARCHAR(255),
    company_id BIGINT NOT NULL,
    type_id BIGINT,
    FOREIGN KEY (company_id) REFERENCES companies(id),
    FOREIGN KEY (type_id) REFERENCES types(id),
    CONSTRAINT unique_per_company UNIQUE (company_id, type_id, subtype)
);


-- for product
CREATE TABLE IF NOT EXISTS products (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    active BOOLEAN, created DATETIME, updated DATETIME,
    title VARCHAR(255), description TEXT,
    price DOUBLE,
    bar_code VARCHAR(255) , rfid_code VARCHAR(255) ,
    created_by_id BIGINT, liable_id BIGINT,

    receiver VARCHAR(255),
    asset_status_id BIGINT, kst_id BIGINT, unit_id BIGINT,
    type_id BIGINT, subtype_id BIGINT,
    producer_id BIGINT, supplier_id BIGINT, branch_id BIGINT, mpk_id BIGINT,
    document TEXT,
    document_date DATE, warranty_period DATE, inspection_date DATE, last_inventory_date DATE,
    longitude DOUBLE, latitude DOUBLE,
    company_id BIGINT NOT NULL,

    FOREIGN KEY (created_by_id) REFERENCES users(id),
    FOREIGN KEY (liable_id) REFERENCES users(id),

    FOREIGN KEY (asset_status_id) REFERENCES asset_statuses(id),
    FOREIGN KEY (kst_id) REFERENCES ksts(id),
    FOREIGN KEY (unit_id) REFERENCES units(id),

    FOREIGN KEY (type_id) REFERENCES types(id),
    FOREIGN KEY (subtype_id) REFERENCES subtypes(id),

    FOREIGN KEY (producer_id) REFERENCES producers(id),
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id),
    FOREIGN KEY (branch_id) REFERENCES branches(id),
    FOREIGN KEY (mpk_id) REFERENCES mpks(id),
    FOREIGN KEY (company_id) REFERENCES companies(id),

    CONSTRAINT unique_per_inventory_number UNIQUE (company_id, bar_code),
    CONSTRAINT unique_per_code UNIQUE (company_id, rfid_code)
);
