-- Produkty
-- UWAGA: kolumna category to enum Category. Pole w encji Product MUSI mieć
-- @Enumerated(EnumType.STRING), inaczej Hibernate mapuje na ORDINAL (int)
-- i poniższe wartości tekstowe ('KEYBOARD'...) wywalą start aplikacji.
INSERT INTO products (name, price, stock, category) VALUES ('Keychron K8 Mechanical Keyboard', 349.00, 25, 'KEYBOARD');
INSERT INTO products (name, price, stock, category) VALUES ('Logitech MX Keys', 459.99, 15, 'KEYBOARD');
INSERT INTO products (name, price, stock, category) VALUES ('Logitech MX Master 3S', 399.00, 30, 'MOUSE');
INSERT INTO products (name, price, stock, category) VALUES ('Razer DeathAdder V3', 279.00, 40, 'MOUSE');
INSERT INTO products (name, price, stock, category) VALUES ('Dell UltraSharp U2723QE 27"', 2199.00, 8, 'MONITOR');
INSERT INTO products (name, price, stock, category) VALUES ('LG 27GP850 27" 165Hz', 1599.00, 12, 'MONITOR');
INSERT INTO products (name, price, stock, category) VALUES ('USB-C Hub 8-in-1', 199.00, 50, 'OTHER');
INSERT INTO products (name, price, stock, category) VALUES ('Mousepad XXL', 79.00, 100, 'OTHER');

-- Zamowienia
INSERT INTO orders (customer_name, order_date, status, total_price) VALUES ('Jan Kowalski', '2026-06-01', 'CONFIRMED', 748.00);
INSERT INTO orders (customer_name, order_date, status, total_price) VALUES ('Anna Nowak', '2026-06-10', 'PENDING', 2199.00);

-- Pozycje zamowien
INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES (1, 1, 1, 349.00);
INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES (1, 3, 1, 399.00);
INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES (2, 5, 1, 2199.00);
