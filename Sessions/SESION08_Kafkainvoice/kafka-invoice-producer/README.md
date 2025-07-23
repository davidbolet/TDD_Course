# Kafka Invoice Producer

This Spring Boot application exposes a REST endpoint to create invoices and send them to Kafka.

## Endpoint

```http
POST /api/invoices
Content-Type: application/json

{
  "id": "INV-123",
  "amount": 100.50,
  "customer": "John Doe"
}
```

## How to Run

1. Start Kafka on `localhost:9092`
2. Run this app:

```bash
./mvnw spring-boot:run
```

3. Send a POST request (see above)
