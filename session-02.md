# Session 2: Test Architecture in Spring Boot

## Objectives

- Understand how to structure unit and integration tests in Spring Boot.
- Apply dependency injection for better testability.
- Learn the Arrange-Act-Assert (AAA) pattern.
- Write testable business logic.
- Practice with service-level tests using mocked repositories.

---

## 1. Testing in Spring Boot Applications

Spring Boot supports layered testing:
- **Unit tests** for services and components.
- **Integration tests** for full application context.

Spring Boot uses JUnit 5 and provides test annotations like:
```java
@SpringBootTest
@DataJpaTest
@WebMvcTest
```

For unit testing, avoid loading full context:
- Use plain classes or `@ExtendWith(MockitoExtension.class)` when using Mockito.

---

## 2. Dependency Injection and Testability

Well-designed applications use **constructor-based injection**, making it easier to test.

Example:
```java
// Service with constructor injection
@Service
public class GreetingService {
    private final TimeProvider timeProvider;

    public GreetingService(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    public String greet() {
        return "Hello, time is " + timeProvider.now();
    }
}
```

Test:
```java
@Test
void shouldGreetWithTime() {
    TimeProvider fakeTime = () -> "12:00";
    GreetingService service = new GreetingService(fakeTime);

    String result = service.greet();
    assertEquals("Hello, time is 12:00", result);
}
```

---

## 3. Arrange-Act-Assert Pattern

AAA pattern improves test readability:
1. **Arrange**: setup your objects
2. **Act**: invoke the method to test
3. **Assert**: check the result

Example:
```java
@Test
void shouldReturnCorrectTotal() {
    // Arrange
    Order order = new Order(2, 10); // qty, price
    OrderService service = new OrderService();

    // Act
    int total = service.calculateTotal(order);

    // Assert
    assertEquals(20, total);
}
```

---

## 4. Exercise: Test a Service with a Fake Repository

**Scenario**: You have a `CustomerService` that depends on a `CustomerRepository`.

### Step 1: Interface

```java
public interface CustomerRepository {
    Optional<Customer> findById(Long id);
}
```

### Step 2: Service

```java
public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public String getCustomerName(Long id) {
        return repository.findById(id)
                .map(Customer::getName)
                .orElse("Unknown");
    }
}
```

### Step 3: Test with Fake Repository

```java
@Test
void returnsCustomerNameIfFound() {
    CustomerRepository fakeRepo = id -> Optional.of(new Customer(id, "Alice"));
    CustomerService service = new CustomerService(fakeRepo);

    String name = service.getCustomerName(1L);
    assertEquals("Alice", name);
}
```

### Challenge

Write another test where the customer is not found and the fallback value is returned.

---

## Summary

In this session, you:
- Built unit tests for Spring services
- Used constructor injection to isolate dependencies
- Applied the AAA pattern for clear test structure
- Practiced testable architecture with fake repositories

Continue to Session 3 to learn how to test REST APIs with Spring's MockMvc.
