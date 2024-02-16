create schema if not exists csmm;

CREATE TABLE IF NOT EXISTS users (
    id BINARY(16) NOT NULL PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    companyName VARCHAR(255),
    companyId BIGINT,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    isEnabled BOOLEAN,
    createdAt DATETIME,
    updatedAt DATETIME,
    roles ENUM('ADMIN', 'CLIENT', 'EMP')
);

CREATE TABLE IF NOT EXISTS token (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) UNIQUE,
    created DATETIME,
    expired DATETIME,
    user_id VARCHAR(36),
    FOREIGN KEY (user_id) REFERENCES users(id)
    );

ALTER TABLE users CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


-- for AssetStatus, KST, Units
CREATE TABLE IF NOT EXISTS asset_statuses (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    active BOOLEAN, created DATETIME, updated DATETIME,
    asset_status VARCHAR(255));

CREATE TABLE IF NOT EXISTS ksts (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    active BOOLEAN, created DATETIME, updated DATETIME,
    kst VARCHAR(255));

CREATE TABLE IF NOT EXISTS units (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    active BOOLEAN, created DATETIME, updated DATETIME,
    unit VARCHAR(255));

CREATE TABLE IF NOT EXISTS companies (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    active BOOLEAN, created DATETIME, updated DATETIME,
    company VARCHAR(255), info TEXT,
    country VARCHAR(255), city VARCHAR(255), address TEXT,
    secret_code VARCHAR(255),
    product_counter INT,
    owner_id VARCHAR(255)
);


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