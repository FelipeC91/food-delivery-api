ALTER TABLE restaurant ADD COLUMN is_active BIT(1) NOT NULL;

SET SQL_SAFE_UPDATES = 0;

UPDATE restaurant SET is_active = true;

SET SQL_SAFE_UPDATES = 1;