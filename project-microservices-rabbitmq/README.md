# Projeto 2 - Microservices + RabbitMQ (sem Lombok)

Este projeto contém dois microservices Spring Boot:
- order-service: cria pedidos e publica evento no RabbitMQ
- notification-service: consome o evento e registra uma notificação

## Como subir o RabbitMQ

```bash
docker compose up -d
```

RabbitMQ Management:
- http://localhost:15672
- user: guest
- password: guest

## Como rodar

### order-service
```bash
cd order-service
mvn spring-boot:run
```

### notification-service
```bash
cd notification-service
mvn spring-boot:run
```

## Teste

```bash
curl -X POST http://localhost:8081/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "Tony Costa",
    "customerEmail": "tony@example.com",
    "product": "Curso Spring",
    "quantity": 2,
    "amount": 199.90
  }'
```

Depois:
```bash
curl http://localhost:8082/notifications
```
