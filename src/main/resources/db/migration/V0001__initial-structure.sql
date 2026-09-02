CREATE TABLE IF NOT EXISTS state (
    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `name` VARCHAR(100) NOT NULL

)engine=InnoDB default charset=utf8;


CREATE TABLE IF NOT EXISTS city (
    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `name` VARCHAR(100) NOT NULL,
    state_id BIGINT,

    FOREIGN KEY (state_id) REFERENCES state(id)

)engine=InnoDB default charset=utf8;

CREATE TABLE IF NOT EXISTS permission (
    id VARCHAR(36) PRIMARY KEY NOT NULL,
    `name` VARCHAR(100) NOT NULL,
    `description` VARCHAR(255) NOT NULL

)engine=InnoDB default charset=utf8;

CREATE TABLE IF NOT EXISTS `user` (
    id VARCHAR(36) PRIMARY KEY NOT NULL,
    `name` VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    `password` VARCHAR(60) NOT NULL,
    create_at DATETIME(6)  NOT NULL

)engine=InnoDB default charset=utf8;

CREATE TABLE IF NOT EXISTS `user_group` (
     id VARCHAR(36) PRIMARY KEY NOT NULL,
    `name` VARCHAR(100) NOT NULL

)engine=InnoDB default charset=utf8;

CREATE TABLE IF NOT EXISTS `user_user_group` (
    user_id VARCHAR(36) NOT NULL,
    user_group_id VARCHAR(36) NOT NULL,

    FOREIGN KEY (user_id) REFERENCES `user`(id),
    FOREIGN KEY (user_group_id) REFERENCES `user_group`(id)

)engine=InnoDB default charset=utf8;

CREATE TABLE IF NOT EXISTS `user_group_permission` (
    user_group_id VARCHAR(36) NOT NULL,
    permission_id VARCHAR(36) NOT NULL,

    FOREIGN KEY (user_group_id) REFERENCES `user_group`(id),
    FOREIGN KEY (permission_id) REFERENCES permission(id)

)engine=InnoDB default charset=utf8;


CREATE TABLE IF NOT EXISTS `payment_method` (
    id VARCHAR(36) PRIMARY KEY NOT NULL,
    `description` VARCHAR(100) NOT NULL

)engine=InnoDB default charset=utf8;

CREATE TABLE IF NOT EXISTS `food_category` (
    id VARCHAR(36) PRIMARY KEY NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255)

)engine=InnoDB default charset=utf8;

CREATE TABLE IF NOT EXISTS restaurant (
    id VARCHAR(36) PRIMARY KEY NOT NULL,
    `name` VARCHAR(100) NOT NULL,
    shipping_cost DECIMAL(38,2) NOT NULL,
    food_category_id VARCHAR(100) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,

    `address_neighborhood` VARCHAR(150) NOT NULL,
    `address_zip_code` VARCHAR(8) NOT NULL,
    `address_street_name` VARCHAR(255) NOT NULL,
    `address_street_number` SMALLINT NOT NULL,
    `address_city_id` BIGINT NOT NULL,

    FOREIGN KEY (food_category_id) REFERENCES food_category(id),
    FOREIGN KEY (address_city_id) REFERENCES city(id)


)engine=InnoDB default charset=utf8;

CREATE TABLE IF NOT EXISTS `restaurant_payment_method` (
                                                           restaurant_id VARCHAR(36) NOT NULL,
    payment_method_id VARCHAR(36) NOT NULL,

    FOREIGN KEY (restaurant_id) REFERENCES restaurant(id),
    FOREIGN KEY (payment_method_id) REFERENCES payment_method(id)

)engine=InnoDB default charset=utf8;

CREATE TABLE IF NOT EXISTS product (
    id VARCHAR(36) PRIMARY KEY NOT NULL,
    `name` VARCHAR(100) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    price DECIMAL(38,2) NOT NULL,
    is_active BIT NOT NULL,
    restaurant_id VARCHAR(36) NOT NULL,

    FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)

)engine=InnoDB default charset=utf8;