-- Script SQL para criar as tabelas 'order', 'product_' e a tabela de junção 'orders_products' (corrigido)

-- Tabela para Order
CREATE TABLE td_order (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    amount DECIMAL(19, 2),
    signature BINARY(32),
    create_date TIMESTAMP,
    update_date TIMESTAMP,
    remove_date TIMESTAMP,
    deleted BOOLEAN,
    user_id BIGINT
);

-- Tabela para Product
CREATE TABLE td_product (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    product_id BIGINT,
    product_name VARCHAR(255),
    create_date TIMESTAMP,
    update_date TIMESTAMP,
    remove_date TIMESTAMP,
    deleted BOOLEAN,
    user_id BIGINT
);

-- Tabela de junção para a relação ManyToMany entre Order e Product
CREATE TABLE tj_orders_products (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    order_id BIGINT,
    product_id BIGINT,
    price DECIMAL(19, 2),
    quantity INTEGER,
    create_date TIMESTAMP,
    update_date TIMESTAMP,
    remove_date TIMESTAMP,
    deleted BOOLEAN,
    user_id BIGINT,
    FOREIGN KEY (order_id) REFERENCES td_order(id),
    FOREIGN KEY (product_id) REFERENCES td_product(id)
);