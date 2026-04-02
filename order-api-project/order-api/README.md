# Order API

Projeto de estudo em Java 17 + Spring Boot 3 para praticar os fundamentos pedidos na vaga:

- API REST
- Spring Data JPA
- banco relacional
- validação
- tratamento global de exceções
- testes unitários e de integração
- logs básicos para observabilidade

## Stack

- Java 17
- Spring Boot 3
- Maven
- PostgreSQL
- H2 para testes
- JUnit 5 / Mockito / MockMvc

## Funcionalidades

- Cadastrar cliente
- Buscar cliente por id
- Cadastrar produto
- Listar produtos
- Buscar produto por id
- Criar pedido
- Listar pedidos
- Buscar pedido por id
- Atualizar status do pedido

## Status do pedido

- CREATED
- PAID
- CANCELLED
- SHIPPED

## Como rodar

### 1. Suba o PostgreSQL

```bash
docker compose up -d
```

### 2. Rode a aplicação

```bash
./mvnw spring-boot:run
```

ou, se estiver com Maven instalado:

```bash
mvn spring-boot:run
```

A API sobe em:

```text
http://localhost:8080
```

## Executando testes

```bash
mvn test
```

## Endpoints

### Customers

#### Criar cliente

```http
POST /customers
Content-Type: application/json
```

```json
{
  "name": "Tony Costa",
  "email": "tony@example.com"
}
```

#### Buscar cliente

```http
GET /customers/1
```

### Products

#### Criar produto

```http
POST /products
Content-Type: application/json
```

```json
{
  "name": "Notebook",
  "price": 3500.00
}
```

#### Listar produtos

```http
GET /products
```

#### Buscar produto por id

```http
GET /products/1
```

### Orders

#### Criar pedido

```http
POST /orders
Content-Type: application/json
```

```json
{
  "customerId": 1,
  "items": [
    {
      "productId": 1,
      "quantity": 2
    }
  ]
}
```

#### Listar pedidos

```http
GET /orders
```

#### Buscar pedido por id

```http
GET /orders/1
```

#### Atualizar status

```http
PATCH /orders/1/status
Content-Type: application/json
```

```json
{
  "status": "PAID"
}
```

## Próximos passos

Esse projeto foi desenhado para servir como base do Projeto 2:

- quebrar em microservices
- adicionar mensageria
- integrar com SQS
- enviar logs para CloudWatch
- subir em EC2
