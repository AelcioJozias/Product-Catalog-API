-- 1. Tabela SELLER (Obrigatório pois ProductEntity tem @ManyToOne não nulo com Seller)
INSERT INTO seller (name, description, score)
VALUES ('Loja Oficial Tech', 'Especialista em eletrônicos', 100);

-- 2. Tabela PRODUCT
INSERT INTO product (name, description, price, available_quantity, condition, category, seller_id)
VALUES ('iPhone 15 Pro', 'Smartphone Apple Titânio', 6500.00, 50, 'NEW', 'Celulares', 1);

INSERT INTO product (name, description, price, available_quantity, condition, category, seller_id)
VALUES ('Samsung Galaxy S23', 'Smartphone Android', 3500.00, 30, 'NEW', 'Celulares', 1);

-- 3. Tabela PRODUCT_VARIANT
-- Produto 1 (iPhone) tem variantes de Cor e Capacidade
INSERT INTO product_variant (type, sort_order, product_id) VALUES ('Cor', 1, 1);
INSERT INTO product_variant (type, sort_order, product_id) VALUES ('Capacidade', 2, 1);

-- Produto 2 (Samsung) tem variante de Cor
INSERT INTO product_variant (type, sort_order, product_id) VALUES ('color', 1, 2);

-- 4. Tabela PRODUCT_VARIANT_VALUE
-- Valores para iPhone - Cor (Variant 1)
-- Nota: "value" entre aspas duplas para escapar a palavra reservada
INSERT INTO product_variant_value ("value", variant_id) VALUES ('Titânio Natural', 1);
INSERT INTO product_variant_value ("value", variant_id) VALUES ('Titânio Azul', 1);

-- Valores para iPhone - Capacidade (Variant 2)
INSERT INTO product_variant_value ("value", variant_id) VALUES ('256GB', 2);