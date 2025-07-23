# 📘 Bookstore Reactive Project – Kafka Integration

This project enhances the original Bookstore Spring Boot app with **Reactive Programming** and **Kafka integration**, designed for student practice.

---

## ✅ What's Included

- ✅ Reactive services using Project Reactor:
  - `ReactiveBookStatisticsService`: computes average rating
  - `ReactiveBookSearchService`: search books using Flux
  - `ReactiveBookOrderService`: verifies stock availability
- ✅ `InventoryAlertService`: triggers when book stock is low
- ✅ Sends `BookOrderMessage` to Kafka topic (`book-orders`)
- ✅ Kafka configuration (`KafkaConfig.java`)
- ✅ DTO class: `BookOrderMessage`
- ✅ Updated `pom.xml` with Reactor and Kafka dependencies

---

## 🧪 Exercises for Students

1. **Unit Test**: Write tests for `ReactiveBookStatisticsService` and `ReactiveBookOrderServiceTest` using `StepVerifier`
2. **Integration Test**: Simulate Kafka message flow using `@EmbeddedKafka`
3. **Enhance**: Extend `InventoryAlertService` to log alerts or persist data
4. **Reactive Controller**: Add a controller method returning `Flux` or `Mono`. Use `searchBooksReactive` as example
5. **Bonus**: Stream book search results via SSE or WebSocket

---

## 🚀 How to Run

### Prerequisites
- Java 17+
- Maven 3.8+
- Kafka running on localhost:9092 (or use EmbeddedKafka in tests)

### Build & Test

```bash
./mvnw clean install