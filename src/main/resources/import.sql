insert into food_category (id, name) values ('c516dda8-ff4b-4464-92b6-7fa38bf533e9', 'Tailandesa');
insert into food_category (id, name) values ('cd1b20d6-ed7a-43d7-aa3c-ea910006ceb0', 'Indiana');

insert into restaurant (id, name, shipping_cost, food_category_id) values ('ec3b02ea-6240-4a56-9da3-199f2bdda03d', 'Thai Gourmet', 10, 'c516dda8-ff4b-4464-92b6-7fa38bf533e9');
insert into restaurant (id, name, shipping_cost, food_category_id) values ('f1b460c9-dcc0-4ec3-bb72-deec95803e99', 'Thai Delivery', 9.50, 'c516dda8-ff4b-4464-92b6-7fa38bf533e9');
insert into restaurant (id, name, shipping_cost, food_category_id) values ('b7a08d8d-95cd-11f1-9aa3-564fb7a4cb2a', 'Tuk Tuk Comida Indiana', 15, 'cd1b20d6-ed7a-43d7-aa3c-ea910006ceb0');

insert into state (id, name) values (1, 'Minas Gerais');
insert into state (id, name) values (2, 'São Paulo');
insert into state (id, name) values (3, 'Rio Grande do Sul');
insert into state (id, name) values (4, 'Santa Catarina');
insert into state (id, name) values (5, 'Mato Grossodo Sul');
insert into state (id, name) values (6, 'Goias');
insert into state (id, name) values (7, 'Espirito Santo');
insert into state (id, name) values (8, 'Paraná');
insert into state (id, name) values (9, 'Mato Grosso');
insert into state (id, name) values (10, 'Tocantins');
insert into state (id, name) values (11, 'Distrito Federal');
insert into state (id, name) values (12, 'Bahia');
insert into state (id, name) values (13, 'Rondônia');
insert into state (id, name) values (14, 'Acre');
insert into state (id, name) values (15, 'Amazonas');
insert into state (id, name) values (16, 'Roraima');
insert into state (id, name) values (17, 'Pará');
insert into state (id, name) values (18, 'Amapá');
insert into state (id, name) values (19, 'Ceará');
insert into state (id, name) values (20, 'Pernambuco');
insert into state (id, name) values (21, 'Sergipe');
insert into state (id, name) values (22, 'Maranhão');
insert into state (id, name) values (23, 'Piauí');
insert into state (id, name) values (24, 'RioGrade do Norte');
insert into state (id, name) values (25, 'Paraiba');
insert into state (id, name) values (26, 'Alagoas');

insert into city (id, name, state_id) values (1, 'Uberlândia', 1);
insert into city (id, name, state_id) values (2, 'Belo Horizonte', 1);
insert into city (id, name, state_id) values (3, 'São Paulo', 2);
insert into city (id, name, state_id) values (4, 'Campinas', 2);
insert into city (id, name, state_id) values (5, 'Fortaleza', 3);

insert into payment_method (id, description) values (1, 'Cartão de crédito');
insert into payment_method (id, description) values (2, 'Cartão de débito');
insert into payment_method (id, description) values (3, 'Dinheiro');

insert into permission (id, name, description) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permission (id, name, description) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');