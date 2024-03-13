
# for inventory
CREATE TABLE IF NOT EXISTS inventories
(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_active BOOLEAN NOT NULL,
    created      DATETIME,
    updated      DATETIME,
    start_date   DATETIME,
    finish_date  DATETIME,
    is_finished  BOOLEAN,
    info         VARCHAR(255),
    user_id      BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (id)
);




CREATE TABLE IF NOT EXISTS events (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_active BOOLEAN NOT NULL,
    created DATETIME,
    updated DATETIME,
    info VARCHAR(255),
    inventory_id BIGINT,
    user_id BIGINT,
    branch_id BIGINT,
    FOREIGN KEY (inventory_id) REFERENCES inventories (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (branch_id) REFERENCES branches (id)
);

CREATE TABLE IF NOT EXISTS event_products
(
    event_id   BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    status ENUM ('EXIST', 'NOT_EXIST')NOT NULL,
    FOREIGN KEY (event_id) REFERENCES events (id),
    FOREIGN KEY (product_id) REFERENCES products (id),
    PRIMARY KEY (event_id, product_id)
);



CREATE TABLE IF NOT EXISTS unknown_products (
    event_id BIGINT NOT NULL,
    code VARCHAR(255) NOT NULL,
    FOREIGN KEY (event_id) REFERENCES events (id)
);
