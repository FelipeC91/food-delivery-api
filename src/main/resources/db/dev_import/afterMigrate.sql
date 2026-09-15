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

INSERT IGNORE INTO restaurant (id, name, is_active, shipping_cost, food_category_id, address_neighborhood, address_zip_code, address_street_name, address_street_number, address_city_id, created_at, updated_at) VALUES ('f1b460c9-dcc0-4ec3-bb72-deec95803e99', 'Thai Delivery', true, 9.50, 'c516dda8-ff4b-4464-92b6-7fa38bf533e9', 'Saraiva', '38408250', 'Rua Goitacazes', 850, 2, '2026-08-14 17:36:26', NOW());
INSERT IGNORE INTO restaurant (id, name, is_active, shipping_cost, food_category_id, address_neighborhood, address_zip_code, address_street_name, address_street_number, address_city_id, created_at, updated_at) VALUES ('ec3b02ea-6240-4a56-9da3-199f2bdda03d', 'Thai Gourmet', true, 10, 'c516dda8-ff4b-4464-92b6-7fa38bf533e9', 'Centro', '38400100', 'Avenida Getúlio Vargas', 1500, 1, '2026-08-14 16:36:26', NOW());
INSERT IGNORE INTO restaurant (id, name, is_active, shipping_cost, food_category_id, address_neighborhood, address_zip_code, address_street_name, address_street_number, address_city_id, created_at, updated_at) VALUES ('b7a08d8d-95cd-11f1-9aa3-564fb7a4cb2a', 'Tuk Tuk Comida Indiana', true, 15, 'cd1b20d6-ed7a-43d7-aa3c-ea910006ceb0', 'Consolação', '01301100', 'Avenida Paulista', 2000, 3, '2026-08-10 17:36:26', NOW());

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

INSERT IGNORE INTO product (id, name, description, price, is_active, restaurant_id) VALUES ('91b553d2-819c-beaf-667b-888888888888', 'Frango Manteiga', 'Frango cremoso ao molho de tomate e especiarias', 30.00, TRUE, 'b7a08d8d-95cd-11f1-9aa3-564fb7a4cb2a');
INSERT IGNORE INTO product (id, name, description, price, is_active, restaurant_id) VALUES ('a2c664e3-92ad-cfb0-7a8c-999999999999', 'Pão Tandoori', 'Pão achatado assado na chapa com leve tostado', 5.00, TRUE, 'b7a08d8d-95cd-11f1-9aa3-564fb7a4cb2a');
INSERT IGNORE INTO product (id, name, description, price, is_active, restaurant_id) VALUES ('b3d775f4-a3be-d0c1-819d-aaaaaaaaaaaa', 'Biryani', 'Arroz basmati aromático cozido com especiarias e carne', 26.00, TRUE, 'b7a08d8d-95cd-11f1-9aa3-564fb7a4cb2a');

INSERT IGNORE INTO permission (id, name, description) VALUES ('f4a7c9e1-2b6d-4f80-9a13-001122334455', 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
INSERT IGNORE INTO permission (id, name, description) VALUES ('a8d2e6b4-7c10-4f93-b521-112233445566', 'EDITAR_COZINHAS', 'Permite editar cozinhas');
INSERT IGNORE INTO permission (id, name, description) VALUES ('c3e9f1a7-5b2d-4d68-8f04-223344556677', 'CONSULTAR_RESTAURANTES', 'Permite consultar restaurantes');
INSERT IGNORE INTO permission (id, name, description) VALUES ('b6f0d4c8-9a31-4e75-82ab-334455667788', 'EDITAR_RESTAURANTES', 'Permite editar restaurantes');
INSERT IGNORE INTO permission (id, name, description) VALUES ('d1a5e7f3-6c20-4b89-9d42-445566778899', 'CONSULTAR_PEDIDOS', 'Permite consultar pedidos');
INSERT IGNORE INTO permission (id, name, description) VALUES ('e7b3c9a1-4d56-4f02-8e73-556677889900', 'GERENCIAR_PEDIDOS', 'Permite gerenciar pedidos');
INSERT IGNORE INTO permission (id, name, description) VALUES ('9c2f6a8e-1b47-4d30-a965-667788990011', 'GERENCIAR_USUARIOS', 'Permite gerenciar usuários');
INSERT IGNORE INTO permission (id, name, description) VALUES ('8e4b1d7f-3a69-4c25-b802-778899001122', 'GERENCIAR_PERMISSOES', 'Permite gerenciar permissões');
INSERT IGNORE INTO permission (id, name, description) VALUES ('7a5c3e1f-8b24-4d96-9017-889900112233', 'CONSULTAR_RELATORIOS', 'Permite consultar relatórios');
INSERT IGNORE INTO permission (id, name, description) VALUES ('6d9f2b4e-7c13-4a85-be60-990011223344', 'GERENCIAR_PRODUTOS', 'Permite gerenciar produtos');

INSERT IGNORE INTO user_group (id, name) VALUES ('11111111-2222-4333-8444-555555555555', 'Administradores');
INSERT IGNORE INTO user_group (id, name) VALUES ('22222222-3333-4444-8555-666666666666', 'Gerente_restaurante');
INSERT IGNORE INTO user_group (id, name) VALUES ('33333333-4444-4555-8666-777777777777', 'Atendentes');
INSERT IGNORE INTO user_group (id, name) VALUES ('44444444-5555-4666-8777-888888888888', 'Cozinheiros');

INSERT IGNORE INTO user_group_permission (user_group_id, permission_id) VALUES ('11111111-2222-4333-8444-555555555555', 'f4a7c9e1-2b6d-4f80-9a13-001122334455');
INSERT IGNORE INTO user_group_permission (user_group_id, permission_id) VALUES ('11111111-2222-4333-8444-555555555555', 'a8d2e6b4-7c10-4f93-b521-112233445566');
INSERT IGNORE INTO user_group_permission (user_group_id, permission_id) VALUES ('11111111-2222-4333-8444-555555555555', 'c3e9f1a7-5b2d-4d68-8f04-223344556677');
INSERT IGNORE INTO user_group_permission (user_group_id, permission_id) VALUES ('11111111-2222-4333-8444-555555555555', 'b6f0d4c8-9a31-4e75-82ab-334455667788');
INSERT IGNORE INTO user_group_permission (user_group_id, permission_id) VALUES ('11111111-2222-4333-8444-555555555555', 'd1a5e7f3-6c20-4b89-9d42-445566778899');
INSERT IGNORE INTO user_group_permission (user_group_id, permission_id) VALUES ('11111111-2222-4333-8444-555555555555', 'e7b3c9a1-4d56-4f02-8e73-556677889900');
INSERT IGNORE INTO user_group_permission (user_group_id, permission_id) VALUES ('11111111-2222-4333-8444-555555555555', '9c2f6a8e-1b47-4d30-a965-667788990011');
INSERT IGNORE INTO user_group_permission (user_group_id, permission_id) VALUES ('11111111-2222-4333-8444-555555555555', '8e4b1d7f-3a69-4c25-b802-778899001122');
INSERT IGNORE INTO user_group_permission (user_group_id, permission_id) VALUES ('11111111-2222-4333-8444-555555555555', '7a5c3e1f-8b24-4d96-9017-889900112233');
INSERT IGNORE INTO user_group_permission (user_group_id, permission_id) VALUES ('11111111-2222-4333-8444-555555555555', '6d9f2b4e-7c13-4a85-be60-990011223344');

INSERT IGNORE INTO user_group_permission (user_group_id, permission_id) VALUES ('22222222-3333-4444-8555-666666666666', 'f4a7c9e1-2b6d-4f80-9a13-001122334455');
INSERT IGNORE INTO user_group_permission (user_group_id, permission_id) VALUES ('22222222-3333-4444-8555-666666666666', 'a8d2e6b4-7c10-4f93-b521-112233445566');
INSERT IGNORE INTO user_group_permission (user_group_id, permission_id) VALUES ('22222222-3333-4444-8555-666666666666', 'c3e9f1a7-5b2d-4d68-8f04-223344556677');
INSERT IGNORE INTO user_group_permission (user_group_id, permission_id) VALUES ('22222222-3333-4444-8555-666666666666', 'b6f0d4c8-9a31-4e75-82ab-334455667788');
INSERT IGNORE INTO user_group_permission (user_group_id, permission_id) VALUES ('22222222-3333-4444-8555-666666666666', 'e7b3c9a1-4d56-4f02-8e73-556677889900');
INSERT IGNORE INTO user_group_permission (user_group_id, permission_id) VALUES ('22222222-3333-4444-8555-666666666666', '7a5c3e1f-8b24-4d96-9017-889900112233');
INSERT IGNORE INTO user_group_permission (user_group_id, permission_id) VALUES ('22222222-3333-4444-8555-666666666666', '6d9f2b4e-7c13-4a85-be60-990011223344');

INSERT IGNORE INTO user_group_permission (user_group_id, permission_id) VALUES ('33333333-4444-4555-8666-777777777777', 'f4a7c9e1-2b6d-4f80-9a13-001122334455');
INSERT IGNORE INTO user_group_permission (user_group_id, permission_id) VALUES ('33333333-4444-4555-8666-777777777777', 'c3e9f1a7-5b2d-4d68-8f04-223344556677');
INSERT IGNORE INTO user_group_permission (user_group_id, permission_id) VALUES ('33333333-4444-4555-8666-777777777777', 'd1a5e7f3-6c20-4b89-9d42-445566778899');
INSERT IGNORE INTO user_group_permission (user_group_id, permission_id) VALUES ('33333333-4444-4555-8666-777777777777', 'e7b3c9a1-4d56-4f02-8e73-556677889900');

INSERT IGNORE INTO user_group_permission (user_group_id, permission_id) VALUES ('44444444-5555-4666-8777-888888888888', 'd1a5e7f3-6c20-4b89-9d42-445566778899');
INSERT IGNORE INTO user_group_permission (user_group_id, permission_id) VALUES ('44444444-5555-4666-8777-888888888888', '6d9f2b4e-7c13-4a85-be60-990011223344');

INSERT IGNORE INTO `user` (id, name, email, password, create_at) VALUES ('55555555-6666-4777-8888-999999999999', 'Ana Souza', 'ana.souza@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2026-08-15 10:00:00');
INSERT IGNORE INTO `user` (id, name, email, password, create_at) VALUES ('66666666-7777-4888-9999-aaaaaaaaaaaa', 'Bruno Lima', 'bruno.lima@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2026-08-15 10:05:00');
INSERT IGNORE INTO `user` (id, name, email, password, create_at) VALUES ('77777777-8888-4999-aaaa-bbbbbbbbbbbb', 'Carla Mendes', 'carla.mendes@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2026-08-15 10:10:00');
INSERT IGNORE INTO `user` (id, name, email, password, create_at) VALUES ('88888888-9999-4aaa-bbbb-cccccccccccc', 'Diego Alves', 'diego.alves@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2026-08-15 10:15:00');
INSERT IGNORE INTO `user` (id, name, email, password, create_at) VALUES ('99999999-aaaa-4bbb-cccc-dddddddddddd', 'Eva Martins', 'eva.martins@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2026-08-15 10:20:00');

INSERT IGNORE INTO order_model (id, restaurant_id, payment_method_id, customer_id, customer_address_neighborhood, customer_address_zip_code, customer_address_street_name, customer_address_street_number, customer_address_city_id, order_status, subtotal, total_price, shipping_cost, created_at, confirmed_at) VALUES ('a1111111-2222-4333-8444-555555555555', 'f1b460c9-dcc0-4ec3-bb72-deec95803e99', 'd53d294e-9b40-11f1-b8aa-a2d63dc49d10', '55555555-6666-4777-8888-999999999999', 'Centro', '38400000', 'Rua das Flores', 101, 2, 'CONFIRMADO', 53.50, 63.00, 5.00,  '2026-08-16 12:00:00', '2026-08-16 12:05:00');
INSERT IGNORE INTO order_model (id, restaurant_id, payment_method_id, customer_id, customer_address_neighborhood, customer_address_zip_code, customer_address_street_name, customer_address_street_number, customer_address_city_id, order_status, subtotal, total_price, shipping_cost, created_at, delivered_in) VALUES ('b2222222-3333-4444-8555-666666666666', 'ec3b02ea-6240-4a56-9da3-199f2bdda03d', '9d5e46bf-89c5-427f-b535-4f497a7e131a', '66666666-7777-4888-9999-aaaaaaaaaaaa', 'Saraiva', '38408000', 'Avenida Brasil', 202, 1, 'ENTREGUE', 41.50, 51.50, 12.00,  '2026-08-16 12:20:00', '2026-08-16 13:10:00');
INSERT IGNORE INTO order_model (id, restaurant_id, payment_method_id, customer_id, customer_address_neighborhood, customer_address_zip_code, customer_address_street_name, customer_address_street_number, customer_address_city_id, order_status, subtotal, total_price, shipping_cost, created_at) VALUES ('c3333333-4444-4555-8666-777777777777', 'b7a08d8d-95cd-11f1-9aa3-564fb7a4cb2a', 'd9adeb5e-2b12-4939-a22c-31092f536e92', '77777777-8888-4999-aaaa-bbbbbbbbbbbb', 'Consolação', '01301100', 'Rua Augusta', 303, 3, 'CRIADO', 40.00, 55.00,  5.00, '2026-08-16 13:00:00');
INSERT IGNORE INTO order_model (id, restaurant_id, payment_method_id, customer_id, customer_address_neighborhood, customer_address_zip_code, customer_address_street_name, customer_address_street_number, customer_address_city_id, order_status, subtotal, total_price, shipping_cost, created_at, cancelled_at) VALUES ('d4444444-5555-4666-8777-888888888888', 'f1b460c9-dcc0-4ec3-bb72-deec95803e99', 'd53d294e-9b40-11f1-b8aa-a2d63dc49d10', '88888888-9999-4aaa-bbbb-cccccccccccc', 'Lídice', '38400010', 'Rua Carajás', 404, 2, 'CANCELADO', 37.00, 46.50, 5.00, '2026-08-16 13:30:00', '2026-08-16 13:40:00');
INSERT IGNORE INTO order_model (id, restaurant_id, payment_method_id, customer_id, customer_address_neighborhood, customer_address_zip_code, customer_address_street_name, customer_address_street_number, customer_address_city_id, order_status, subtotal, total_price, shipping_cost, created_at, confirmed_at) VALUES ('e5555555-6666-4777-8888-999999999999', 'ec3b02ea-6240-4a56-9da3-199f2bdda03d', '9d5e46bf-89c5-427f-b535-4f497a7e131a', '99999999-aaaa-4bbb-cccc-dddddddddddd', 'Centro', '38400100', 'Avenida Getúlio Vargas', 505, 1, 'CONFIRMADO', 56.00, 66.00, 6.00,  '2026-08-16 14:00:00', '2026-08-16 14:05:00');

INSERT IGNORE INTO order_item (product_id, quantity, unit_price, total_price, note, order_id) VALUES ('3b9f8d7c-2b3c-5e4f-0a1b-222222222222', 1, 28.50, 28.50, NULL, 'a1111111-2222-4333-8444-555555555555');
INSERT IGNORE INTO order_item (product_id, quantity, unit_price, total_price, note, order_id) VALUES ('2a8f7c6e-1a2b-4d3e-9f0a-111111111111', 1, 25.00, 25.00, 'Pouco picante', 'a1111111-2222-4333-8444-555555555555');
INSERT IGNORE INTO order_item (product_id, quantity, unit_price, total_price, note, order_id) VALUES ('6e2c20af-5e6f-8b7c-3d4e-555555555555', 1, 22.00, 22.00, 'Adicionar limão', 'b2222222-3333-4444-8555-666666666666');
INSERT IGNORE INTO order_item (product_id, quantity, unit_price, total_price, note, order_id) VALUES ('7f3d31b0-6f7a-9c8d-4e5f-666666666666', 1, 27.00, 27.00, NULL, 'b2222222-3333-4444-8555-666666666666');
INSERT IGNORE INTO order_item (product_id, quantity, unit_price, total_price, note, order_id) VALUES ('91b553d2-819c-beaf-667b-888888888888', 1, 30.00, 30.00, 'Sem pimenta', 'c3333333-4444-4555-8666-777777777777');
INSERT IGNORE INTO order_item (product_id, quantity, unit_price, total_price, note, order_id) VALUES ('a2c664e3-92ad-cfb0-7a8c-999999999999', 2, 5.00, 10.00, NULL, 'c3333333-4444-4555-8666-777777777777');
INSERT IGNORE INTO order_item (product_id, quantity, unit_price, total_price, note, order_id) VALUES ('4c0a9e8d-3c4d-6f5a-1b2c-333333333333', 1, 12.00, 12.00, NULL, 'd4444444-5555-4666-8777-888888888888');
INSERT IGNORE INTO order_item (product_id, quantity, unit_price, total_price, note, order_id) VALUES ('3b9f8d7c-2b3c-5e4f-0a1b-222222222222', 1, 28.50, 28.50, 'Entregar rápido', 'd4444444-5555-4666-8777-888888888888');
INSERT IGNORE INTO order_item (product_id, quantity, unit_price, total_price, note, order_id) VALUES ('6e2c20af-5e6f-8b7c-3d4e-555555555555', 1, 22.00, 22.00, NULL, 'e5555555-6666-4777-8888-999999999999');
INSERT IGNORE INTO order_item (product_id, quantity, unit_price, total_price, note, order_id) VALUES ('7f3d31b0-6f7a-9c8d-4e5f-666666666666', 1, 27.00, 27.00, 'Sem cebola', 'e5555555-6666-4777-8888-999999999999');
