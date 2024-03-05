
-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created DATETIME, updated DATETIME,
    is_active BOOLEAN  NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    last_activity DATETIME NOT NULL,
    roles ENUM('ADMIN', 'EMP') NOT NULL
);

-- Create companies table with foreign key reference to users table
CREATE TABLE IF NOT EXISTS company (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created DATETIME, updated DATETIME,
    company VARCHAR(255) UNIQUE NOT NULL,
    city VARCHAR(255),
    street VARCHAR(255),
    zip_code VARCHAR(255),
    nip VARCHAR(255),
    regon VARCHAR(255),
    phone VARCHAR(255),
    email VARCHAR(255)
);




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
    is_active BOOLEAN  NOT NULL,
    asset_status VARCHAR(255) UNIQUE);

CREATE TABLE IF NOT EXISTS ksts (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_active BOOLEAN  NOT NULL,
    num VARCHAR(5) UNIQUE NOT NULL,
    kst VARCHAR(255));

CREATE TABLE IF NOT EXISTS units (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_active BOOLEAN  NOT NULL,
    unit VARCHAR(255) UNIQUE);


-- configured tables
CREATE TABLE IF NOT EXISTS branches (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_active BOOLEAN  NOT NULL,
    branch VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS mpks (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_active BOOLEAN  NOT NULL,
    mpk VARCHAR(255) UNIQUE
);


-- for types and subtypes
CREATE TABLE IF NOT EXISTS types (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_active BOOLEAN  NOT NULL,
    type VARCHAR(255) UNIQUE
);
CREATE TABLE IF NOT EXISTS subtypes (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_active BOOLEAN  NOT NULL,
    subtype VARCHAR(255) NOT NULL,
    type_id BIGINT,
    UNIQUE(subtype, type_id),
    FOREIGN KEY (type_id) REFERENCES types(id)
);


-- for product
CREATE TABLE IF NOT EXISTS products (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_active BOOLEAN  NOT NULL, created DATETIME, updated DATETIME,
    title VARCHAR(255), description TEXT, price DOUBLE,
    bar_code VARCHAR(255) UNIQUE, rfid_code VARCHAR(255) UNIQUE,
    inventory_number VARCHAR(255),
    created_by_id BIGINT, liable_id BIGINT, receiver VARCHAR(255),
    kst_id BIGINT, asset_status_id BIGINT, unit_id BIGINT,
    branch_id BIGINT, mpk_id BIGINT,
    type_id BIGINT, subtype_id BIGINT,
    producer VARCHAR(255), supplier VARCHAR(255),

    is_scrapping BOOLEAN,
    scrapping_date DATETIME,
    scrapping_reason VARCHAR(255),

    document TEXT, document_date DATE, warranty_period DATE, inspection_date DATE,
    last_inventory_date DATE,
    longitude DOUBLE, latitude DOUBLE,

    FOREIGN KEY (created_by_id) REFERENCES users(id),
    FOREIGN KEY (liable_id) REFERENCES users(id),
    FOREIGN KEY (kst_id) REFERENCES ksts(id),
    FOREIGN KEY (asset_status_id) REFERENCES asset_statuses(id),
    FOREIGN KEY (unit_id) REFERENCES units(id),
    FOREIGN KEY (type_id) REFERENCES types(id),
    FOREIGN KEY (subtype_id) REFERENCES subtypes(id),
    FOREIGN KEY (branch_id) REFERENCES branches(id),
    FOREIGN KEY (mpk_id) REFERENCES mpks(id));




# for inventarization
CREATE TABLE IF NOT EXISTS inventarization (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_active BOOLEAN  NOT NULL, created DATETIME, updated DATETIME,
    start_date DATE, finish_date DATE,
    info VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS event (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_active BOOLEAN  NOT NULL, created DATETIME, updated DATETIME,
    user_id BIGINT,
    branch_id BIGINT
);

CREATE TABLE IF NOT EXISTS event_product_shortage (
    event_id BIGINT,
    product_id BIGINT,
    PRIMARY KEY (event_id, product_id)
);
CREATE TABLE IF NOT EXISTS event_product_ok (
    event_id BIGINT,
    product_id BIGINT,
    PRIMARY KEY (event_id, product_id)
);
