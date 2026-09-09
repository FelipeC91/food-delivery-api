INSERT IGNORE INTO food_category (id, name) VALUES ('c516dda8-ff4b-4464-92b6-7fa38bf533e9', 'Tailandesa');
INSERT IGNORE INTO food_category (id, name) VALUES ('cd1b20d6-ed7a-43d7-aa3c-ea910006ceb0', 'Indiana');

INSERT IGNORE INTO state (id, name) VALUES (1, 'Minas Gerais');
INSERT IGNORE INTO state (id, name) VALUES (2, 'São Paulo');
INSERT IGNORE INTO state (id, name) VALUES (3, 'Rio Grande do Sul');
INSERT IGNORE INTO state (id, name) VALUES (4, 'Santa Catarina');
INSERT IGNORE INTO state (id, name) VALUES (5, 'Mato Grossodo Sul');
INSERT IGNORE INTO state (id, name) VALUES (6, 'Goias');
INSERT IGNORE INTO state (id, name) VALUES (19, 'Ceará');


INSERT IGNORE INTO city (id, name, state_id) VALUES (1, 'Uberlândia', 1);
INSERT IGNORE INTO city (id, name, state_id) VALUES (2, 'Belo Horizonte', 1);
INSERT IGNORE INTO city (id, name, state_id) VALUES (3, 'São Paulo', 2);
INSERT IGNORE INTO city (id, name, state_id) VALUES (4, 'Campinas', 2);
INSERT IGNORE INTO city (id, name, state_id) VALUES (5, 'Fortaleza', 3);

INSERT IGNORE INTO restaurant (id, name, shipping_cost, food_category_id, address_neighborhood, address_zip_code, address_street_name, address_street_number, address_city_id, created_at, updated_at) VALUES ('f1b460c9-dcc0-4ec3-bb72-deec95803e99', 'Thai Delivery', 9.50, 'c516dda8-ff4b-4464-92b6-7fa38bf533e9', 'Saraiva', '38408250', 'Rua Goitacazes', 850, 2, '2026-08-14 17:36:26', NOW());
INSERT IGNORE INTO restaurant (id, name, shipping_cost, food_category_id, address_neighborhood, address_zip_code, address_street_name, address_street_number, address_city_id, created_at, updated_at) VALUES ('ec3b02ea-6240-4a56-9da3-199f2bdda03d', 'Thai Gourmet', 10, 'c516dda8-ff4b-4464-92b6-7fa38bf533e9', 'Centro', '38400100', 'Avenida Getúlio Vargas', 1500, 1, '2026-08-14 16:36:26', NOW());
INSERT IGNORE INTO restaurant (id, name, shipping_cost, food_category_id, address_neighborhood, address_zip_code, address_street_name, address_street_number, address_city_id, created_at, updated_at) VALUES ('b7a08d8d-95cd-11f1-9aa3-564fb7a4cb2a', 'Tuk Tuk Comida Indiana', 15, 'cd1b20d6-ed7a-43d7-aa3c-ea910006ceb0', 'Consolação', '01301100', 'Avenida Paulista', 2000, 3, '2026-08-10 17:36:26', NOW());

INSERT IGNORE INTO payment_method (id, description) VALUES ('d53d294e-9b40-11f1-b8aa-a2d63dc49d10', 'Cartão de crédito');
INSERT IGNORE INTO payment_method (id, description) VALUES ('9d5e46bf-89c5-427f-b535-4f497a7e131a', 'Cartão de débito');
INSERT IGNORE INTO payment_method (id, description) VALUES ('d9adeb5e-2b12-4939-a22c-31092f536e92', 'Dinheiro');

INSERT IGNORE INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES ('f1b460c9-dcc0-4ec3-bb72-deec95803e99', 'd53d294e-9b40-11f1-b8aa-a2d63dc49d10');
INSERT IGNORE INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES ('f1b460c9-dcc0-4ec3-bb72-deec95803e99', '9d5e46bf-89c5-427f-b535-4f497a7e131a');
INSERT IGNORE INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES ('f1b460c9-dcc0-4ec3-bb72-deec95803e99', 'd9adeb5e-2b12-4939-a22c-31092f536e92');
INSERT IGNORE INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES ('ec3b02ea-6240-4a56-9da3-199f2bdda03d', 'd53d294e-9b40-11f1-b8aa-a2d63dc49d10');
INSERT IGNORE INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES ('ec3b02ea-6240-4a56-9da3-199f2bdda03d', '9d5e46bf-89c5-427f-b535-4f497a7e131a');
INSERT IGNORE INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES ('b7a08d8d-95cd-11f1-9aa3-564fb7a4cb2a', 'd53d294e-9b40-11f1-b8aa-a2d63dc49d10');
INSERT IGNORE INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES ('b7a08d8d-95cd-11f1-9aa3-564fb7a4cb2a', 'd9adeb5e-2b12-4939-a22c-31092f536e92');

INSERT IGNORE INTO product (id, name, description, price, is_active, restaurant_id) VALUES ('2a8f7c6e-1a2b-4d3e-9f0a-111111111111', 'Pad Thai', 'Macarrão frito com molho de tamarindo, camarão, tofu e amendoim', 25.00, TRUE, 'f1b460c9-dcc0-4ec3-bb72-deec95803e99');
INSERT IGNORE INTO product (id, name, description, price, is_active, restaurant_id) VALUES ('3b9f8d7c-2b3c-5e4f-0a1b-222222222222', 'Curry Verde', 'Curry de leite de coco com frango, berinjela e manjericão', 28.50, TRUE, 'f1b460c9-dcc0-4ec3-bb72-deec95803e99');
INSERT IGNORE INTO product (id, name, description, price, is_active, restaurant_id) VALUES ('4c0a9e8d-3c4d-6f5a-1b2c-333333333333', 'Rolinhos Primavera', 'Rolinhos primavera crocantes de legumes servidos com molho agridoce', 12.00, TRUE, 'f1b460c9-dcc0-4ec3-bb72-deec95803e99');
INSERT IGNORE INTO product (id, name, description, price, is_active, restaurant_id) VALUES ('5d1b0f9e-4d5e-7a6b-2c3d-444444444444', 'Arroz Doce com Manga', 'Arroz doce pegajoso com manga madura e leite de coco', 15.00, FALSE, 'f1b460c9-dcc0-4ec3-bb72-deec95803e99');

INSERT IGNORE INTO product (id, name, description, price, is_active, restaurant_id) VALUES ('6e2c20af-5e6f-8b7c-3d4e-555555555555', 'Sopa Tom Yum', 'Sopa picante e azeda com camarão, capim-limão e limão', 22.00, TRUE, 'ec3b02ea-6240-4a56-9da3-199f2bdda03d');
INSERT IGNORE INTO product (id, name, description, price, is_active, restaurant_id) VALUES ('7f3d31b0-6f7a-9c8d-4e5f-666666666666', 'Curry Vermelho', 'Curry vermelho com brotos de bambu e opção de proteína', 27.00, TRUE, 'ec3b02ea-6240-4a56-9da3-199f2bdda03d');
INSERT IGNORE INTO product (id, name, description, price, is_active, restaurant_id) VALUES ('80a442c1-708b-ad9e-5f6a-777777777777', 'Salada Tailandesa', 'Salada fresca com ervas, molho de limão e amendoim triturado', 14.50, TRUE, 'ec3b02ea-6240-4a56-9da3-199f2bdda03d');

INSERT IGNORE INTO product (id, name, description, price, is_active, restaurant_id) VALUES ('91b553d2-819c-beaf-6g7b-888888888888', 'Frango Manteiga', 'Frango cremoso ao molho de tomate e especiarias', 30.00, TRUE, 'b7a08d8d-95cd-11f1-9aa3-564fb7a4cb2a');
INSERT IGNORE INTO product (id, name, description, price, is_active, restaurant_id) VALUES ('a2c664e3-92ad-cfb0-7h8c-999999999999', 'Pão Tandoori', 'Pão achatado assado na chapa com leve tostado', 5.00, TRUE, 'b7a08d8d-95cd-11f1-9aa3-564fb7a4cb2a');
INSERT IGNORE INTO product (id, name, description, price, is_active, restaurant_id) VALUES ('b3d775f4-a3be-d0c1-8i9d-aaaaaaaaaaaa', 'Biryani', 'Arroz basmati aromático cozido com especiarias e carne', 26.00, TRUE, 'b7a08d8d-95cd-11f1-9aa3-564fb7a4cb2a');

INSERT IGNORE INTO permission (id, name, description) VALUES (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
INSERT IGNORE INTO permission (id, name, description) VALUES (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');
