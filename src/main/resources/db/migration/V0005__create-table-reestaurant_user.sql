CREATE TABLE IF NOT EXISTS product_photo (
    product_id VARCHAR(36) NOT NULL,
    file_name VARCHAR(120) NOT NULL,
    photo_description VARCHAR(120) NOT NULL,
    content_type VARCHAR(20) NOT NULL,
    file_size INTEGER NOT NULL,

    PRIMARY KEY (product_id),
    FOREIGN KEY(product_id) REFERENCES product(id)

)engine=InnoDB default charset=utf8;
