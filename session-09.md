# Session 9: Kafka Testing and Reactive Programming

## Objectives

- Understand reactive programming using Spring WebFlux and Project Reactor.
- Write unit tests for `Mono` and `Flux` using `StepVerifier`.
- Use `EmbeddedKafka` to test Kafka message flows.
- Build and verify reactive Kafka pipelines.

---

## 1. Reactive Programming with Reactor

Project Reactor provides two main types:
- `Mono<T>` – emits 0 or 1 item
- `Flux<T>` – emits 0 to N items

Example:

```java
Flux<String> names = Flux.just("Alice", "Bob", "Charlie");
Mono<Integer> number = Mono.just(42);
```

---

## 2. Testing with StepVerifier

`StepVerifier` allows precise testing of `Flux` and `Mono`.

```java
@Test
void testMono() {
    Mono<String> mono = Mono.just("Hello");

    StepVerifier.create(mono)
                .expectNext("Hello")
                .verifyComplete();
}

@Test
void testFlux() {
    Flux<Integer> flux = Flux.range(1, 3);

    StepVerifier.create(flux)
                .expectNext(1, 2, 3)
                .verifyComplete();
}
```

---

## 3. Kafka Integration in Spring Boot

Add dependencies:

```xml
<dependency>
  <groupId>org.springframework.kafka</groupId>
  <artifactId>spring-kafka</artifactId>
</dependency>
```

Enable reactive Kafka (optional for reactive consumers):
```xml
<dependency>
  <groupId>org.springframework.kafka</groupId>
  <artifactId>spring-kafka-test</artifactId>
  <scope>test</scope>
</dependency>
```

---

## 4. Embedded Kafka for Testing

Use `@EmbeddedKafka` to simulate a Kafka broker during tests.

```java
@EmbeddedKafka(partitions = 1, topics = { "test-topic" })
@SpringBootTest
class KafkaProducerTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    void shouldSendMessage() {
        kafkaTemplate.send("test-topic", "key", "message");
        // Further validation depends on Kafka consumer logic
    }
}
```

---

## 5. Kafka Consumer with Reactive Processing

Reactive pipeline:

```java
@KafkaListener(topics = "orders")
public void consume(String message) {
    Flux.just(message)
        .map(this::transform)
        .doOnNext(System.out::println)
        .subscribe();
}
```

---

## 6. Exercise: Kafka + Flux Pipeline

### Task

1. Create a producer service that sends messages to `notifications` topic.
2. Build a Kafka listener that consumes and transforms messages using a `Flux` pipeline.
3. Write tests with:
   - `StepVerifier` for the `Flux` processing
   - `@EmbeddedKafka` for message flow

Example processor test:

```java
@Test
void shouldTransformMessage() {
    Flux<String> input = Flux.just("ping");

    StepVerifier.create(input.map(msg -> msg.toUpperCase()))
                .expectNext("PING")
                .verifyComplete();
}
```

---

## Summary

You now know how to:
- Write and test reactive code with Project Reactor
- Test `Mono` and `Flux` using `StepVerifier`
- Simulate Kafka environments using `EmbeddedKafka`
- Integrate Kafka with reactive pipelines

Next up: Session 10 – Final TDD Workshop!
