# Session 8: Contract Testing with Spring Cloud Contract and Pact

## Objectives

- Understand what Contract Testing is and when to use it.
- Differentiate between integration testing and contract testing.
- Use Spring Cloud Contract to generate stubs and verify producers.
- Use Pact to define consumer-driven contracts.
- Integrate contract tests into CI/CD.

---

## 1. What is Contract Testing?

**Contract Testing** ensures that services (producers) and their clients (consumers) agree on the structure of exchanged messages.

It replaces heavy integration tests with fast and precise contract verifications.

---

## 2. Consumer-Driven Contracts

- Consumers define the expectations (contracts).
- Producers verify and conform to these contracts.
- Helps avoid broken APIs in distributed systems.

---

## 3. Spring Cloud Contract – Producer Perspective

### Setup (Producer Side)

```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-contract-verifier</artifactId>
  <version>4.0.0</version>
</dependency>
```

### Sample Contract (Groovy DSL)

`src/test/resources/contracts/customer_contract.groovy`:

```groovy
org.springframework.cloud.contract.spec.Contract.make {
  request {
    method 'GET'
    url '/customers/1'
  }
  response {
    status 200
    body([
      id: 1,
      name: "Alice"
    ])
    headers {
      contentType(applicationJson())
    }
  }
}
```

### Generated Stub Test

```java
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureStubRunner(ids = "com.example:producer:+:stubs:8080", stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class ContractVerificationTest {

  @Test
  public void shouldReturnStubbedCustomer() {
    ResponseEntity<String> response = new RestTemplate().getForEntity("http://localhost:8080/customers/1", String.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }
}
```

---

## 4. Pact – Consumer Perspective

### Setup

Add dependencies:

```xml
<dependency>
  <groupId>au.com.dius.pact.consumer</groupId>
  <artifactId>junit5</artifactId>
  <version>4.5.3</version>
</dependency>
```

### Example Consumer Test

```java
@Pact(consumer = "customer-client")
public RequestResponsePact customerPact(PactDslWithProvider builder) {
  return builder
    .given("Customer with ID 1 exists")
    .uponReceiving("A request for customer")
      .path("/customers/1").method("GET")
    .willRespondWith()
      .status(200)
      .body("{"id":1,"name":"Alice"}")
    .toPact();
}

@Test
@PactTestFor(providerName = "customer-service")
void verifyPact(MockServer mockServer) {
  String url = mockServer.getUrl() + "/customers/1";
  String response = new RestTemplate().getForObject(url, String.class);
  assertTrue(response.contains("Alice"));
}
```

---

## 5. Pact Broker and CI Integration

Use Pact Broker to:
- Publish pact files
- Share contracts with producer teams

Automate verification:
```bash
./gradlew pactPublish
./gradlew pactVerify
```

CI tools like GitHub Actions and Jenkins can include pact steps in the build.

---

## 6. Exercise: Build and Test a Contract

### Scenario

- Build a Spring Boot REST producer service: `/api/customers/{id}`
- Write a Pact test as the consumer
- Generate the pact file
- Verify producer using Spring Cloud Contract or Pact CLI

---

## Summary

You now understand:
- What Contract Testing is
- How to use Spring Cloud Contract and Pact
- How to verify service compatibility across teams
- How to automate contracts in your CI/CD pipeline

Session 9 will focus on testing Kafka message flows and reactive pipelines.
