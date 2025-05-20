--CREATE DATABASE order_database;
GRANT ALL PRIVILEGES ON DATABASE order_database TO postgres;

CREATE TABLE td_order (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    amount NUMERIC(19, 2),
    signature BYTEA,
    create_date TIMESTAMP WITHOUT TIME ZONE,
    update_date TIMESTAMP WITHOUT TIME ZONE,
    remove_date TIMESTAMP WITHOUT TIME ZONE,
    deleted BOOLEAN,
    user_id BIGINT
);

CREATE TABLE td_product (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    product_id BIGINT,
    product_name VARCHAR(255),
    create_date TIMESTAMP WITHOUT TIME ZONE,
    update_date TIMESTAMP WITHOUT TIME ZONE,
    remove_date TIMESTAMP WITHOUT TIME ZONE,
    deleted BOOLEAN,
    user_id BIGINT
);

CREATE TABLE tj_orders_products (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    order_id BIGINT REFERENCES td_order(id),
    product_id BIGINT REFERENCES td_product(id),
    price NUMERIC(19, 2),
    quantity INTEGER,
    create_date TIMESTAMP WITHOUT TIME ZONE,
    update_date TIMESTAMP WITHOUT TIME ZONE,
    remove_date TIMESTAMP WITHOUT TIME ZONE,
    deleted BOOLEAN,
    user_id BIGINT
);