CREATE TABLE IF NOT EXISTS restaurant_user (
    restaurant_id VARCHAR(36) NOT NULL,
    user_id VARCHAR(36) NOT NULL,

    PRIMARY KEY (restaurant_id, user_id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurant(id),
    FOREIGN KEY (user_id) REFERENCES `user`(id)

)engine=InnoDB default charset=utf8;