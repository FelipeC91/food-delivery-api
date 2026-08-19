INSERT INTO food_category (id, name) VALUES ('c516dda8-ff4b-4464-92b6-7fa38bf533e9', 'Tailandesa');
INSERT INTO food_category (id, name) VALUES ('cd1b20d6-ed7a-43d7-aa3c-ea910006ceb0', 'Indiana');

INSERT INTO state (id, name) VALUES (1, 'Minas Gerais');
INSERT INTO state (id, name) VALUES (2, 'São Paulo');
INSERT INTO state (id, name) VALUES (3, 'Rio Grande do Sul');
INSERT INTO state (id, name) VALUES (4, 'Santa Catarina');
INSERT INTO state (id, name) VALUES (5, 'Mato Grossodo Sul');
INSERT INTO state (id, name) VALUES (6, 'Goias');
INSERT INTO state (id, name) VALUES (7, 'Espirito Santo');
INSERT INTO state (id, name) VALUES (8, 'Paraná');
INSERT INTO state (id, name) VALUES (9, 'Mato Grosso');
INSERT INTO state (id, name) VALUES (10, 'Tocantins');
INSERT INTO state (id, name) VALUES (11, 'Distrito Federal');
INSERT INTO state (id, name) VALUES (12, 'Bahia');
INSERT INTO state (id, name) VALUES (13, 'Rondônia');
INSERT INTO state (id, name) VALUES (14, 'Acre');
INSERT INTO state (id, name) VALUES (15, 'Amazonas');
INSERT INTO state (id, name) VALUES (16, 'Roraima');
INSERT INTO state (id, name) VALUES (17, 'Pará');
INSERT INTO state (id, name) VALUES (18, 'Amapá');
INSERT INTO state (id, name) VALUES (19, 'Ceará');
INSERT INTO state (id, name) VALUES (20, 'Pernambuco');
INSERT INTO state (id, name) VALUES (21, 'Sergipe');
INSERT INTO state (id, name) VALUES (22, 'Maranhão');
INSERT INTO state (id, name) VALUES (23, 'Piauí');
INSERT INTO state (id, name) VALUES (24, 'RioGrade do Norte');
INSERT INTO state (id, name) VALUES (25, 'Paraiba');
INSERT INTO state (id, name) VALUES (26, 'Alagoas');

INSERT INTO city (id, name, state_id) VALUES (1, 'Uberlândia', 1);
INSERT INTO city (id, name, state_id) VALUES (2, 'Belo Horizonte', 1);
INSERT INTO city (id, name, state_id) VALUES (3, 'São Paulo', 2);
INSERT INTO city (id, name, state_id) VALUES (4, 'Campinas', 2);
INSERT INTO city (id, name, state_id) VALUES (5, 'Fortaleza', 3);

INSERT INTO restaurant (id, name, shipping_cost, food_category_id, neighborhood, address_zip_code, address_street_name, address_street_number, address_city_id) VALUES ('f1b460c9-dcc0-4ec3-bb72-deec95803e99', 'Thai Delivery', 9.50, 'c516dda8-ff4b-4464-92b6-7fa38bf533e9', 'Saraiva', '38408-250', 'Rua Goitacazes', '850', 2);
INSERT INTO restaurant (id, name, shipping_cost, food_category_id, neighborhood, address_zip_code, address_street_name, address_street_number, address_city_id) VALUES ('ec3b02ea-6240-4a56-9da3-199f2bdda03d', 'Thai Gourmet', 10, 'c516dda8-ff4b-4464-92b6-7fa38bf533e9', 'Centro', '38400-100', 'Avenida Getúlio Vargas', '1500', 1);
INSERT INTO restaurant (id, name, shipping_cost, food_category_id, neighborhood, address_zip_code, address_street_name, address_street_number, address_city_id) VALUES ('b7a08d8d-95cd-11f1-9aa3-564fb7a4cb2a', 'Tuk Tuk Comida Indiana', 15, 'cd1b20d6-ed7a-43d7-aa3c-ea910006ceb0', 'Consolação', '01301-100', 'Avenida Paulista', '2000', 3);

INSERT INTO payment_method (id, description) VALUES ('d53d294e-9b40-11f1-b8aa-a2d63dc49d10', 'Cartão de crédito');
INSERT INTO payment_method (id, description) VALUES ('9d5e46bf-89c5-427f-b535-4f497a7e131a', 'Cartão de débito');
INSERT INTO payment_method (id, description) VALUES ('d9adeb5e-2b12-4939-a22c-31092f536e92', 'Dinheiro');

INSERT INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES ('f1b460c9-dcc0-4ec3-bb72-deec95803e99', 'd53d294e-9b40-11f1-b8aa-a2d63dc49d10');
INSERT INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES ('f1b460c9-dcc0-4ec3-bb72-deec95803e99', '9d5e46bf-89c5-427f-b535-4f497a7e131a');
INSERT INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES ('f1b460c9-dcc0-4ec3-bb72-deec95803e99', 'd9adeb5e-2b12-4939-a22c-31092f536e92');
INSERT INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES ('ec3b02ea-6240-4a56-9da3-199f2bdda03d', 'd53d294e-9b40-11f1-b8aa-a2d63dc49d10');
INSERT INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES ('ec3b02ea-6240-4a56-9da3-199f2bdda03d', '9d5e46bf-89c5-427f-b535-4f497a7e131a');
INSERT INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES ('b7a08d8d-95cd-11f1-9aa3-564fb7a4cb2a', 'd53d294e-9b40-11f1-b8aa-a2d63dc49d10');
INSERT INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES ('b7a08d8d-95cd-11f1-9aa3-564fb7a4cb2a', 'd9adeb5e-2b12-4939-a22c-31092f536e92');

INSERT INTO permission (id, name, description) VALUES (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
INSERT INTO permission (id, name, description) VALUES (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

