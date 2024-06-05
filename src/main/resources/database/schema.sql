-- Create users table
CREATE TABLE IF NOT EXISTS users
(
    id            BIGINT                NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created       DATETIME,
    updated       DATETIME,
    last_activity DATETIME              NOT NULL,
    is_active     BOOLEAN               NOT NULL,
    firstname     VARCHAR(255)          NOT NULL,
    lastname      VARCHAR(255)          NOT NULL,
    phone         VARCHAR(255)          NOT NULL,
    email         VARCHAR(255) UNIQUE   NOT NULL,
    password      VARCHAR(255)          NOT NULL,
    roles         ENUM ('ADMIN', 'EMP') NOT NULL
);

-- Create companies table with foreign key reference to users table
CREATE TABLE IF NOT EXISTS company
(
    id                  BIGINT              NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created             DATETIME,
    updated             DATETIME,
    company             VARCHAR(255) UNIQUE NOT NULL,
    city                VARCHAR(255),
    street              VARCHAR(255),
    zip_code            VARCHAR(255),
    nip                 VARCHAR(255),
    regon               VARCHAR(255),
    phone               VARCHAR(255),
    email               VARCHAR(255),


    label_width         DECIMAL(8, 2),
    label_height        DECIMAL(8, 2),
    label_type          VARCHAR(255),


    rfid_length         INTEGER,
    rfid_length_max     INTEGER,
    rfid_length_min     INTEGER,
    rfid_prefix         VARCHAR(255),
    rfid_suffix         VARCHAR(255),
    rfid_postfix        VARCHAR(255),

    bar_code_length     INTEGER,
    bar_code_length_max INTEGER,
    bar_code_length_min INTEGER,
    bar_code_prefix     VARCHAR(255),
    bar_code_suffix     VARCHAR(255),
    bar_code_postfix    VARCHAR(255),


    is_email_configured BOOLEAN             NOT NULL,
    host                VARCHAR(255),
    port                VARCHAR(255),
    username            VARCHAR(255),
    password            VARCHAR(255),
    protocol            VARCHAR(255)


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

CREATE TABLE IF NOT EXISTS locations
(
    id        BIGINT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_active BOOLEAN NOT NULL,
    location  VARCHAR(255),
    branch_id BIGINT,
    UNIQUE (location, branch_id),
    FOREIGN KEY (branch_id) REFERENCES branches (id)
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
    location_id      BIGINT,
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
    FOREIGN KEY (location_id) REFERENCES locations (id),
    FOREIGN KEY (mpk_id) REFERENCES mpks (id)
);


CREATE TABLE IF NOT EXISTS product_history
(
    id         BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created    DATETIME,
    user_id    BIGINT,
    product_id BIGINT,
    activity   ENUM ('VISIBILITY', 'PRODUCT_WAS_CREATED', 'TITLE', 'PRICE', 'BAR_CODE', 'RFID_CODE', 'INVENTORY_NUMBER',
        'SERIAL_NUMBER', 'LIABLE', 'LOCATION',
        'RECEIVER', 'KST', 'ASSET_STATUS', 'UNIT', 'BRANCH', 'MPK', 'TYPE', 'SUBTYPE', 'PRODUCER',
        'SUPPLIER', 'SCRAPPING', 'DOCUMENT', 'DOCUMENT_DATE', 'WARRANTY_PERIOD', 'INSPECTION_DATE', 'GPS', 'COMMENT',
        'SERVICE'),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE TABLE IF NOT EXISTS logs
(
    id      BIGINT                              NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created DATETIME,
    user_id BIGINT,
    action  ENUM ('CREATE', 'UPDATE', 'DELETE') NOT NULL,
    section ENUM ('USERS', 'COMPANY',
        'INVENTORY', 'EVENT',
        'NOTIFICATION', 'PRODUCT',
        'TYPE', 'SUBTYPE', 'LOCATION',
        'UNIT', 'ASSET_STATUS', 'KST',
        'MPK', 'BRANCH')                        NOT NULL,
    text    VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users (id)
);


CREATE TABLE IF NOT EXISTS notifications
(
    id         BIGINT                                                          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_active  BOOLEAN                                                         NOT NULL,
    created    DATETIME,
    updated    DATETIME,
    is_viewed  BOOLEAN                                                         NOT NULL,
    reason     ENUM ('COMPANY_WAS_UPDATED', 'PASSWORD_WAS_CHANGED', 'USER_WAS_CREATED', 'NEW_USER',
        'USER_WAS_DISABLED', 'YOU_WERE_DISABLED', 'USER_WAS_ENABLED', 'YOU_WERE_ENABLED', 'USER_WAS_UPDATED', 'YOU_WERE_UPDATED',
        'INVENTORY_IS_START', 'INVENTORY_IS_FINISHED', 'PLANNED_INVENTORY',
        'PRODUCT_WAS_DISABLED', 'PRODUCT_WAS_ENABLED', 'PRODUCT_WAS_SCRAPPED') NOT NULL,
    message    VARCHAR(255),
    to_user_id BIGINT,
    created_by BIGINT,
    FOREIGN KEY (to_user_id) REFERENCES users (id),
    FOREIGN KEY (created_by) REFERENCES users (id)
);



CREATE TABLE IF NOT EXISTS inventories
(
    id          BIGINT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_active   BOOLEAN NOT NULL,
    created     DATETIME,
    updated     DATETIME,
    start_date  DATETIME,
    finish_date DATETIME,
    is_finished BOOLEAN,
    info        VARCHAR(255)
);


CREATE TABLE IF NOT EXISTS events
(
    id           BIGINT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_active    BOOLEAN NOT NULL,
    created      DATETIME,
    updated      DATETIME,
    info         VARCHAR(255),
    inventory_id BIGINT,
    user_id      BIGINT,
    branch_id    BIGINT,
    FOREIGN KEY (inventory_id) REFERENCES inventories (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (branch_id) REFERENCES branches (id),
    CONSTRAINT unique_branch_inventory UNIQUE (branch_id, inventory_id)
);

CREATE TABLE IF NOT EXISTS scanned_products
(
    id         BIGINT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created    DATETIME,
    updated    DATETIME,
    product_id BIGINT  NOT NULL,
    event_id   BIGINT  NOT NULL,
    user_id    BIGINT,
    is_scanned BOOLEAN NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products (id),
    FOREIGN KEY (event_id) REFERENCES events (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT unique_event_product UNIQUE (product_id, event_id)
);



CREATE TABLE IF NOT EXISTS unknown_products
(
    id        BIGINT                         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created   DATETIME,
    code      VARCHAR(255) UNIQUE            NOT NULL,
    type_code ENUM ('RFID_CODE', 'BAR_CODE') NOT NULL,
    event_id  BIGINT                         NOT NULL,
    user_id   BIGINT,
    FOREIGN KEY (event_id) REFERENCES events (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);


CREATE TABLE IF NOT EXISTS comments
(
    id         BIGINT              NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created    DATETIME,
    comment    VARCHAR(255) UNIQUE NOT NULL,
    user_id    BIGINT,
    product_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
);


CREATE TABLE IF NOT EXISTS service_provider
(
    id        BIGINT              NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_active BOOLEAN             NOT NULL,
    created   DATETIME,
    updated   DATETIME,
    company   VARCHAR(255) UNIQUE NOT NULL,
    nip       VARCHAR(255) UNIQUE NOT NULL,
    address   VARCHAR(255) UNIQUE NOT NULL
);
CREATE TABLE IF NOT EXISTS contact_person
(
    id                  BIGINT              NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_active           BOOLEAN             NOT NULL,
    created             DATETIME,
    updated             DATETIME,
    firstname           VARCHAR(255) UNIQUE NOT NULL,
    lastname            VARCHAR(255) UNIQUE NOT NULL,
    phone_number        VARCHAR(255) UNIQUE NOT NULL,
    email               VARCHAR(255) UNIQUE NOT NULL,
    service_provider_id BIGINT              NOT NULL,
    FOREIGN KEY (service_provider_id) REFERENCES service_provider (id)
);


CREATE TABLE IF NOT EXISTS serviced_assets
(
    id                     BIGINT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_active              BOOLEAN NOT NULL,
    created                DATETIME,
    updated                DATETIME,
    product_id             BIGINT  NOT NULL,
    service_start_date     DATETIME,
    service_end_date       DATETIME,
    planned_service_period INTEGER,
    service_provider_id    BIGINT  NOT NULL,
    contact_person_id      BIGINT  NOT NULL,
    send_by_id             BIGINT  NOT NULL,
    received_by_id         BIGINT,
    delivery               ENUM ('kurier', 'odbiór osobisty'),
    FOREIGN KEY (product_id) REFERENCES products (id),
    FOREIGN KEY (service_provider_id) REFERENCES service_provider (id),
    FOREIGN KEY (contact_person_id) REFERENCES contact_person (id),
    FOREIGN KEY (send_by_id) REFERENCES users (id),
    FOREIGN KEY (received_by_id) REFERENCES users (id)
);
select bar_code from products;