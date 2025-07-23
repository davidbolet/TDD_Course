# Kafka Invoice Consumer

This Spring Boot app listens to the Kafka topic `received-invoices` and logs received invoices.

## How to Run

1. Ensure Kafka is running on `localhost:9092`
2. Run this app:

```bash
./mvnw spring-boot:run
```

## What it Does

When an invoice is received from the Kafka topic `received-invoices`, it's printed to the console log.
