# Session 5: Integration and Data Tests

## Objectives

- Understand how to write integration tests in Spring Boot.
- Use `@SpringBootTest` and `@DataJpaTest`.
- Run tests with H2 or PostgreSQL.
- Validate transactional behavior and error handling.
- Practice writing tests for edge cases and validation logic.

---

## 1. Spring Boot Integration Testing

### `@SpringBootTest`

- Loads the full application context.
- Useful for service, controller, and integration tests.

```java
@SpringBootTest
class OrderServiceIntegrationTest {
    @Autowired
    OrderService orderService;

    @Test
    void shouldCreateOrder() {
        Order order = new Order("item", 3);
        Order saved = orderService.create(order);
        assertNotNull(saved.getId());
    }
}
```

---

### `@DataJpaTest`

- Loads only JPA components (Repositories).
- Uses in-memory DB (H2) by default.
- Faster than full context.

```java
@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository repository;

    @Test
    void shouldSaveCustomer() {
        Customer c = new Customer(null, "Alice");
        Customer saved = repository.save(c);
        assertNotNull(saved.getId());
    }
}
```

---

## 2. Configuring H2 for Testing

In `application-test.yml`:

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password:
  h2:
    console:
      enabled: true
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

Use profile in test class:

```java
@ActiveProfiles("test")
```

---

## 3. Testing Validations and Edge Cases

Example entity with validation:

```java
@Entity
public class Product {
    @Id @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    @Min(1)
    private int quantity;
}
```

Test invalid input:

```java
@Test
void shouldRejectNegativeQuantity() {
    Product p = new Product(null, "Pen", -5);

    assertThrows(ConstraintViolationException.class, () -> repository.save(p));
}
```

---

## 4. Transactional Tests

By default, tests roll back transactions:

```java
@SpringBootTest
@Transactional
class PaymentTest {
    // Data created in test is not persisted permanently
}
```

---

## 5. Exercise: Full Integration Test

### Scenario

You have a `CustomerService` using a real `CustomerRepository`.

- Set up `@SpringBootTest`
- Configure test DB
- Write tests for:
  - Successful save
  - Save with invalid input
  - Fetch by ID

Example:

```java
@Test
void shouldSaveValidCustomer() {
    Customer customer = new Customer(null, "Valid Name");
    Customer saved = customerService.save(customer);
    assertNotNull(saved.getId());
}
```

---

## Summary

You’ve learned to:
- Use `@SpringBootTest` and `@DataJpaTest` for integration tests
- Work with in-memory DBs like H2
- Write transactional and validation-focused tests
- Handle edge cases in real Spring Boot apps

In Session 6, we’ll automate test runs with CI/CD pipelines and integrate static analysis tools like SonarQube.
