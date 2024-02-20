

CREATE TABLE IF NOT EXISTS users (
    id BINARY(16) NOT NULL PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    company_name VARCHAR(255),
    company_id BINARY(16),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    is_enabled BOOLEAN,
    created DATETIME,
    updated DATETIME,
    roles ENUM('ADMIN', 'CLIENT', 'EMP')
);


CREATE TABLE IF NOT EXISTS token (
    id BINARY(16) NOT NULL PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    created DATETIME,
    expired DATETIME,
    user_id BINARY(16) ,
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

CREATE TABLE IF NOT EXISTS companies (
    id BINARY(16) NOT NULL PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    active BOOLEAN, created DATETIME, updated DATETIME,
    company VARCHAR(255) UNIQUE, info TEXT,
    country VARCHAR(255), city VARCHAR(255), address TEXT,
    secret_code BINARY(16) UNIQUE,
    product_counter INT,
    owner_id BINARY(16)
);


CREATE TABLE IF NOT EXISTS company_asset_status (
    company_id BINARY(16),
    asset_status_id BIGINT NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE,
    FOREIGN KEY (asset_status_id) REFERENCES asset_statuses(id),
    PRIMARY KEY (company_id, asset_status_id)
);

CREATE TABLE IF NOT EXISTS company_kst (
    company_id BINARY(16),
    kst_id BIGINT NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies(id),
    FOREIGN KEY (kst_id) REFERENCES ksts(id),
    PRIMARY KEY (company_id, kst_id)
);

CREATE TABLE IF NOT EXISTS company_unit (
    company_id BINARY(16),
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
    company_id BINARY(16),
    asset_status_id BIGINT NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies(id),
    FOREIGN KEY (asset_status_id) REFERENCES asset_statuses(id),
    PRIMARY KEY (company_id, asset_status_id)
);

CREATE TABLE IF NOT EXISTS company_kst (
    company_id BINARY(16),
    kst_id BIGINT NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies(id),
    FOREIGN KEY (kst_id) REFERENCES ksts(id),
    PRIMARY KEY (company_id, kst_id)
);

CREATE TABLE IF NOT EXISTS company_unit (
    company_id BINARY(16),
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
    company_id BINARY(16),
    FOREIGN KEY (company_id) REFERENCES companies(id),
    CONSTRAINT unique_per_company UNIQUE (company_id, branch)
);

CREATE TABLE IF NOT EXISTS mpks (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    active BOOLEAN,
    mpk VARCHAR(255),
    company_id BINARY(16),
    FOREIGN KEY (company_id) REFERENCES companies(id),
    CONSTRAINT unique_per_company UNIQUE (company_id, mpk)
);

CREATE TABLE IF NOT EXISTS producers (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    active BOOLEAN,
    producer VARCHAR(255),
    company_id BINARY(16),
    FOREIGN KEY (company_id) REFERENCES companies(id),
    CONSTRAINT unique_per_company UNIQUE (company_id, producer)
);

CREATE TABLE IF NOT EXISTS suppliers (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    active BOOLEAN,
    supplier VARCHAR(255),
    company_id BINARY(16),
    FOREIGN KEY (company_id) REFERENCES companies(id),
    CONSTRAINT unique_per_company UNIQUE (company_id, supplier)
);


-- for types and subtypes
CREATE TABLE IF NOT EXISTS types (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    active BOOLEAN,
    type VARCHAR(255),
    company_id BINARY(16),
    FOREIGN KEY (company_id) REFERENCES companies(id),
    CONSTRAINT unique_per_company UNIQUE (company_id, type)
);
CREATE TABLE IF NOT EXISTS subtypes (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    active BOOLEAN,
    subtype VARCHAR(255),
    company_id BINARY(16),
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
    inventory_number VARCHAR(255) , code VARCHAR(255) ,
    created_by BINARY(16), liable BINARY(16), receiver VARCHAR(255),
    asset_status_id BIGINT, kst_id BIGINT, unit_id BIGINT,
    type_id BIGINT, subtype_id BIGINT,
    producer_id BIGINT, supplier_id BIGINT, branch_id BIGINT, mpk_id BIGINT,
    document TEXT,
    document_date DATE, warranty_period DATE, inspection_date DATE, last_inventory_date DATE,
    longitude DOUBLE, latitude DOUBLE,
    company_id BINARY(16),

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

    CONSTRAINT unique_per_inventory_number UNIQUE (company_id, inventory_number),
    CONSTRAINT unique_per_code UNIQUE (company_id, code)
);




select * from users;