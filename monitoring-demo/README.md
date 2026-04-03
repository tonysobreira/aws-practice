# Monitoring Demo

Projeto pronto para praticar:
- Spring Boot Actuator
- Prometheus
- Grafana
- Dashboard provisionado automaticamente

## Requisitos
- Java 17
- Maven
- Docker e Docker Compose

## 1. Subir a aplicacao
```bash
mvn spring-boot:run
```

A aplicacao sobe em:
- http://localhost:8081

Endpoints uteis:
- `GET /api/orders/health-check`
- `POST /api/orders?customer=Tony&quantity=2`
- `GET /api/orders/load-test?milliseconds=500`
- `GET /api/orders/error-simulation`
- `GET /actuator/health`
- `GET /actuator/prometheus`
- `GET /actuator/metrics`

## 2. Subir Prometheus e Grafana
Em outro terminal:
```bash
docker compose up -d
```

Acesse:
- Prometheus: http://localhost:9090
- Grafana: http://localhost:3000

Login padrao do Grafana:
- usuario: `admin`
- senha: `admin`

## 3. Gerar metricas para ver no dashboard
```bash
curl -X POST "http://localhost:8081/api/orders?customer=Tony&quantity=1"
curl -X POST "http://localhost:8081/api/orders?customer=Maria&quantity=2"
curl "http://localhost:8081/api/orders/load-test?milliseconds=800"
curl "http://localhost:8081/api/orders/error-simulation"
```

## 4. O que aparece no dashboard
- requisicoes por segundo
- latencia HTTP p95
- total de pedidos criados
- total de falhas simuladas
- fila em processamento
- taxa de erro 5xx
- memoria heap usada

## 5. Observacao
O Prometheus esta configurado para coletar a aplicacao em `host.docker.internal:8081`, ideal para Docker Desktop.
Se estiver usando Linux sem Docker Desktop, ajuste o target no `infra/prometheus/prometheus.yml`.
