CREATE TABLE IF NOT EXISTS order_model (
    id VARCHAR(36) PRIMARY KEY NOT NULL,
    restaurant_id VARCHAR(36) NOT NULL,
    payment_method_id VARCHAR(36) NOT NULL,
    customer_id VARCHAR(36) NOT NULL,

    customer_address_neighborhood VARCHAR(150) NOT NULL,
    customer_address_zip_code VARCHAR(8) NOT NULL,
    customer_address_street_name VARCHAR(255) NOT NULL,
    customer_address_street_number SMALLINT NOT NULL,
    customer_address_city_id BIGINT NOT NULL,

    shipping_cost DECIMAL(38,2) NOT NULL,
    order_status VARCHAR(10) NOT NULL,
    subtotal DECIMAL(38,2) NOT NULL,
    total_price DECIMAL(38,2) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    confirmed_at DATETIME(6),
    cancelled_at  DATETIME(6),
    delivered_in  DATETIME(6),

    FOREIGN KEY (restaurant_id) REFERENCES restaurant(id),
    FOREIGN KEY (payment_method_id) REFERENCES payment_method(id),
    FOREIGN KEY (customer_id) REFERENCES `user`(id)

)engine=InnoDB default charset=utf8;

CREATE TABLE IF NOT EXISTS order_item (
--     id VARCHAR(36) PRIMARY KEY NOT NULL,
    order_id VARCHAR(36) NOT NULL  PRIMARY KEY,
    product_id VARCHAR(36) NOT NULL,
    quantity INTEGER NOT NULL,
    unit_price DECIMAL(38,2) NOT NULL,
    total_price DECIMAL(38,2) NOT NULL,
    note VARCHAR(255),


    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (order_id) REFERENCES order_model(id)

    )engine=InnoDB default charset=utf8;
