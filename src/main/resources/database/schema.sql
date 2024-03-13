-- Create users table
CREATE TABLE IF NOT EXISTS users
(
    id            BIGINT                NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created       DATETIME,
    updated       DATETIME,
    is_active     BOOLEAN               NOT NULL,
    firstname     VARCHAR(255)          NOT NULL,
    lastname      VARCHAR(255)          NOT NULL,
    phone         VARCHAR(255)          NOT NULL,
    email         VARCHAR(255) UNIQUE   NOT NULL,
    password      VARCHAR(255)          NOT NULL,
    last_activity DATETIME              NOT NULL,
    roles         ENUM ('ADMIN', 'EMP') NOT NULL
);

-- Create companies table with foreign key reference to users table
CREATE TABLE IF NOT EXISTS company
(
    id       BIGINT              NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created  DATETIME,
    updated  DATETIME,
    company  VARCHAR(255) UNIQUE NOT NULL,
    city     VARCHAR(255),
    street   VARCHAR(255),
    zip_code VARCHAR(255),
    nip      VARCHAR(255),
    regon    VARCHAR(255),
    phone    VARCHAR(255),
    email    VARCHAR(255),


    label_width INTEGER,
    label_height INTEGER,
    label_type VARCHAR(255),


    is_email_configured BOOLEAN NOT NULL,
    host VARCHAR(255),
    port VARCHAR(255),
    username VARCHAR(255),
    password VARCHAR(255),
    protocol VARCHAR(255)


);



CREATE TABLE IF NOT EXISTS token
(
    id      BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    token   BINARY(16),
    created DATETIME,
    expired DATETIME,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (id)
);


-- for AssetStatus, KST, Units
CREATE TABLE IF NOT EXISTS asset_statuses
(
    id           BIGINT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_active    BOOLEAN NOT NULL,
    asset_status VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS ksts
(
    id        BIGINT            NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_active BOOLEAN           NOT NULL,
    num       VARCHAR(5) UNIQUE NOT NULL,
    kst       VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS units
(
    id        BIGINT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_active BOOLEAN NOT NULL,
    unit      VARCHAR(255) UNIQUE
);


-- configured tables
CREATE TABLE IF NOT EXISTS branches
(
    id        BIGINT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_active BOOLEAN NOT NULL,
    branch    VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS mpks
(
    id        BIGINT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_active BOOLEAN NOT NULL,
    mpk       VARCHAR(255) UNIQUE
);


-- for types and subtypes
CREATE TABLE IF NOT EXISTS types
(
    id        BIGINT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_active BOOLEAN NOT NULL,
    type      VARCHAR(255) UNIQUE
);
CREATE TABLE IF NOT EXISTS subtypes
(
    id        BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_active BOOLEAN      NOT NULL,
    subtype   VARCHAR(255) NOT NULL,
    type_id   BIGINT,
    UNIQUE (subtype, type_id),
    FOREIGN KEY (type_id) REFERENCES types (id)
);


-- for product
CREATE TABLE IF NOT EXISTS products
(
    id               BIGINT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_active        BOOLEAN NOT NULL,
    created          DATETIME,
    updated          DATETIME,
    title            VARCHAR(255),
    description      TEXT,
    price            DOUBLE,
    bar_code         VARCHAR(255) UNIQUE,
    rfid_code        VARCHAR(255) UNIQUE,
    inventory_number VARCHAR(255) UNIQUE,
    serial_number    VARCHAR(255) UNIQUE,
    created_by_id    BIGINT,
    liable_id        BIGINT,
    receiver         VARCHAR(255),
    kst_id           BIGINT,
    asset_status_id  BIGINT,
    unit_id          BIGINT,
    branch_id        BIGINT,
    mpk_id           BIGINT,
    type_id          BIGINT,
    subtype_id       BIGINT,
    producer         VARCHAR(255),
    supplier         VARCHAR(255),

    is_scrapping     BOOLEAN,
    scrapping_date   DATETIME,
    scrapping_reason VARCHAR(255),

    document         TEXT,
    document_date    DATE,
    warranty_period  DATE,
    inspection_date  DATE,

    longitude        DOUBLE,
    latitude         DOUBLE,

    FOREIGN KEY (created_by_id) REFERENCES users (id),
    FOREIGN KEY (liable_id) REFERENCES users (id),
    FOREIGN KEY (kst_id) REFERENCES ksts (id),
    FOREIGN KEY (asset_status_id) REFERENCES asset_statuses (id),
    FOREIGN KEY (unit_id) REFERENCES units (id),
    FOREIGN KEY (type_id) REFERENCES types (id),
    FOREIGN KEY (subtype_id) REFERENCES subtypes (id),
    FOREIGN KEY (branch_id) REFERENCES branches (id),
    FOREIGN KEY (mpk_id) REFERENCES mpks (id)
);

CREATE TABLE IF NOT EXISTS logs
(
    id      BIGINT        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created DATETIME,
    user_id BIGINT,
    action  ENUM ('CREATE', 'UPDATE', 'DELETE')                       NOT NULL,
    section    ENUM ('USERS', 'COMPANY', 'INVENT', 'EVENT', 'TYPE', 'SUBTYPE', 'UNIT', 'ASSET_STATUS', 'MPK', 'KST', 'BRANCH') NOT NULL,
    text    VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users (id)
);


CREATE TABLE IF NOT EXISTS notifications (
    id          BIGINT        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_active   BOOLEAN       NOT NULL,
    created     DATETIME,
    updated     DATETIME,
    is_viewed     BOOLEAN       NOT NULL,
    reason      ENUM ('COMPANY_WAS_UPDATED', 'PASSWORD_WAS_CHANGED', 'USER_WAS_CREATED', 'NEW_USER',
        'USER_WAS_DISABLED', 'YOU_WERE_DISABLED', 'USER_WAS_ENABLED', 'YOU_WERE_ENABLED', 'USER_WAS_UPDATED', 'YOU_WERE_UPDATED',
        'INVENTORY_IS_START', 'INVENTORY_IS_FINISHED', 'PLANNED_INVENTORY',
        'PRODUCT_WAS_DISABLED', 'PRODUCT_WAS_ENABLED', 'PRODUCT_WAS_SCRAPPED') NOT NULL,
    message     VARCHAR(255),
    to_user_id  BIGINT,
    created_by  BIGINT,
    FOREIGN KEY (to_user_id) REFERENCES users (id),
    FOREIGN KEY (created_by) REFERENCES users (id)
);


