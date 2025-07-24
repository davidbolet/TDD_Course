# Session 4: Mocking with Mockito

## Objectives

- Understand mocking and why it's important in unit testing.
- Use Mockito to isolate dependencies.
- Learn how to use annotations: `@Mock`, `@InjectMocks`.
- Use `when`, `verify`, and argument matchers.
- Practice with service tests using mocked repositories.

---

## 1. Why Mock?

Mocks are used to isolate the unit under test from its external dependencies, such as:

- Databases
- Web services
- Message brokers

Mocks let us:
- Avoid side effects
- Control behavior
- Verify interactions

---

## 2. Basic Mockito Setup

```java
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository repository;

    @InjectMocks
    CustomerService service;

    @Test
    void shouldReturnCustomerName() {
        when(repository.findById(1L)).thenReturn(Optional.of(new Customer(1L, "Alice")));

        String name = service.getCustomerName(1L);

        assertEquals("Alice", name);
    }
}
```

---

## 3. Stubbing Behavior

```java
when(repository.findByEmail("bob@example.com"))
    .thenReturn(Optional.of(new Customer(2L, "Bob")));
```

Throwing an exception:

```java
when(service.process(null))
    .thenThrow(new IllegalArgumentException("input cannot be null"));
```

---

## 4. Argument Matchers

```java
when(repository.findById(anyLong())).thenReturn(Optional.of(new Customer(1L, "Generic")));

verify(repository).findById(eq(1L));
```

Useful matchers:
- `any(Class.class)`
- `anyInt()`, `anyString()`, `anyLong()`
- `eq(value)`

---

## 5. Verifying Interactions

Verify how mocks were used:

```java
verify(repository).save(any());
verify(repository, times(1)).findById(1L);
verify(repository, never()).deleteById(any());
```

---

## 6. Exercise: Service Tests with Mockito

### Scenario

You have a `UserService` class with the following methods:

```java
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public boolean isActiveUser(Long id) {
        return repository.findById(id)
                .map(User::isActive)
                .orElse(false);
    }
}
```

### Task

Write tests using Mockito:
- Mock `UserRepository`
- Stub `findById` to return active and inactive users
- Verify interactions

```java
@Test
void shouldReturnTrueForActiveUser() {
    when(repository.findById(1L)).thenReturn(Optional.of(new User(1L, "John", true)));

    boolean result = service.isActiveUser(1L);

    assertTrue(result);
    verify(repository).findById(1L);
}
```

Challenge: Write another test where the user is not found.

---

## Summary

You now know how to:
- Use Mockito to mock dependencies
- Control return values and throw exceptions
- Use matchers and verify interactions
- Write unit tests that are isolated and focused

In Session 5, we’ll explore integration testing with real databases.
