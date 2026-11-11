# FOOD DELIVERY API

---

### Sobre o projeto:
Esta aplicação tem como objetivo emular um MVP de um app de gestão de Delivery (como Ifood, 99 e Keeta), atendendo requisições de: tanto (1) agente consumidor final (cadastro, navegação entre restaurantes e seus cardápios e geração de  pedidos) quanto o (2) agente restaurante (gestão de cadastros gerais, cardápio e processamento de pedidos).

---
### Objetivo:

O projeto é uma prova de conceito que tem como objetivo atestar meus conhecimentos e domínio de boas praticas de escrita de código (SOLID, Clean Code DDD e Clean Architecture), engenharia de software e domínio de framework, em confornidade com padões de mercado. 

Estimo que 94% deste projeto foi construído artesanalmente, sem o auxílio de IA generativa.

+ ***Conceitos de de DDD e design patterns relacionados aplicados neste projeto:*** Aggregate Root, Domain Entity, Value Object e Domain Events, Repository Domain Services, Factory.


+ ***Framework***: Projetos Spring Boot (4.0.3) utilizados nesta aplicação: Spring Security (7), Authorization Server, Resource Server, Spring Data JPA, HATEOAS, SpringDoc-OpenApi, Spring Actuator e Java Mail Sender


+ ***Outras integrações***: Loggly para armazenamento de logs em nuvem, Mailgun como serividor SMTP, JasperReports para geração de relatórios em PDF e Bucket4j e hazelcast para implementação ed rate limit.


+ ***AWS***: Serviços utilizados na hospedagem deste projeto: S3, IAM, EC2, ECS


+ ***Ferramentas de CI/CD***: Docker, Docker-Compose, Git, GitHub

---

### Hipóteses de expansão/evolução futura deste projeto:

+ Separar servidor de autorização do servidor de recursos - hoje atuam como monolito


+ Possibilidade de avaliação de pedidos por parte do agente consumidor final


+ Subscrição de eventos de pedido em fila (apache Kafka) simulando o processamento destes eventos por outros microserviços, bem como a inscrição (kafka listeners) em topicos de eventos de outros micoserviços


+ Propagação de Server Sent Events para notificação de clients subscritos via via Webhooks (agente restaurante, agente cliente final).


+ Utilização de banco de dados chave/valor (Redis) para compartilhar Webhooks em múltiplas instancias da aplicação

+ Possibilidade de integração com Google Maps para validação de raio de entrega interações com agente entregador


+ Remodelagem da base de dados de produtos para banco de dados orientado a documentos (Mongo DB) para flexibilização dos tipos de produto


+ Amazon SNS para disparo de SMS de notificação