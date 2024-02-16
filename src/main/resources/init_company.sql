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

-- for company
CREATE TABLE IF NOT EXISTS companies (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    active BOOLEAN, created DATETIME, updated DATETIME,
    company VARCHAR(255), info TEXT,
    country VARCHAR(255), city VARCHAR(255), address TEXT,
    secret_code VARCHAR(255),
    product_counter INT,
    owner_id BINARY(16)
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
    active BOOLEAN, created DATETIME, updated DATETIME,
    branch VARCHAR(255),
    company_id BIGINT,
    FOREIGN KEY (company_id) REFERENCES companies(id)
);

CREATE TABLE IF NOT EXISTS mpks (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    active BOOLEAN, created DATETIME, updated DATETIME,
    mpk VARCHAR(255),
    company_id BIGINT,
    FOREIGN KEY (company_id) REFERENCES companies(id)
);

CREATE TABLE IF NOT EXISTS producers (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    active BOOLEAN, created DATETIME, updated DATETIME,
    producer VARCHAR(255),
    company_id BIGINT,
    FOREIGN KEY (company_id) REFERENCES companies(id)
);

CREATE TABLE IF NOT EXISTS suppliers (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    active BOOLEAN, created DATETIME, updated DATETIME,
    supplier VARCHAR(255),
    company_id BIGINT,
    FOREIGN KEY (company_id) REFERENCES companies(id)
);


-- for types and subtypes
CREATE TABLE IF NOT EXISTS types (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    active BOOLEAN, created DATETIME, updated DATETIME,
    types VARCHAR(255),
    company_id BIGINT,
    FOREIGN KEY (company_id) REFERENCES companies(id)
);
CREATE TABLE IF NOT EXISTS subtypes (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    active BOOLEAN, created DATETIME, updated DATETIME,
    subtypes VARCHAR(255),
    company_id BIGINT,
    type_id BIGINT,
    FOREIGN KEY (company_id) REFERENCES companies(id),
    FOREIGN KEY (type_id) REFERENCES types(id)
);


-- for product
CREATE TABLE IF NOT EXISTS products (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    active BOOLEAN, created DATETIME, updated DATETIME,
    title VARCHAR(255), description TEXT,
    price DOUBLE,
    inventory_number VARCHAR(255), code VARCHAR(255),
    created_by BINARY(16), liable BINARY(16), receiver VARCHAR(255),
    asset_status_id BIGINT, kst_id BIGINT, unit_id BIGINT,
    type_id BIGINT, subtype_id BIGINT
    producer_id BIGINT, supplier_id BIGINT, branch_id BIGINT, mpk_id BIGINT,
    document TEXT,
    document_date DATE, warranty_period DATE, inspection_date DATE, last_inventory_date DATE,
    longitude DOUBLE, latitude DOUBLE,
    company_id BIGINT,

    FOREIGN KEY (asset_status_id) REFERENCES asset_statuses(id),
    FOREIGN KEY (kst_id) REFERENCES ksts(id),
    FOREIGN KEY (unit_id) REFERENCES units(id),
    FOREIGN KEY (type_id) REFERENCES types(id),
    FOREIGN KEY (subtype_id) REFERENCES subtypes(id),
    FOREIGN KEY (producer_id) REFERENCES producers(id),
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id),
    FOREIGN KEY (branch_id) REFERENCES branches(id),
    FOREIGN KEY (mpk_id) REFERENCES mpks(id),
    FOREIGN KEY (company_id) REFERENCES companies(id)
);

