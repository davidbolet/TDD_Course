# Session 7: Refactoring and Advanced Clean Code

## Objectives

- Apply SOLID principles to improve code design and testability.
- Refactor code while keeping tests green.
- Identify code smells and apply structured refactorings.
- Practice improving architecture through clean code techniques.

---

## 1. What is Refactoring?

**Refactoring** is the process of improving the internal structure of code without changing its external behavior.

Guidelines:
- Always keep your tests green (passing).
- Use small, safe refactoring steps.
- Refactor after writing a failing test (TDD cycle).

---

## 2. SOLID Principles (Recap)

- **S** – Single Responsibility Principle  
- **O** – Open/Closed Principle  
- **L** – Liskov Substitution Principle  
- **I** – Interface Segregation Principle  
- **D** – Dependency Inversion Principle

These principles promote modular, extensible, and testable code.

---

## 3. Common Code Smells

- Long functions
- Duplicated code
- Large classes
- Primitive obsession
- Feature envy (one class heavily depends on another)

---

## 4. Refactoring Example: Extract Method

**Before**:

```java
public String formatUser(User user) {
    return "Name: " + user.getName() + ", Email: " + user.getEmail();
}
```

**After**:

```java
public String formatUser(User user) {
    return formatName(user) + ", " + formatEmail(user);
}

private String formatName(User user) {
    return "Name: " + user.getName();
}

private String formatEmail(User user) {
    return "Email: " + user.getEmail();
}
```

---

## 5. Test-Safe Refactoring

Refactor code with full confidence using tests as a safety net:

- Run your test suite after each change.
- Use IDE refactor tools (rename, extract method/class).
- Avoid logic changes while cleaning structure.

---

## 6. Exercise: Refactor While Keeping Tests Green

### Scenario

You have the following legacy class:

```java
public class ReportBuilder {
    public String build(User user) {
        String name = "Name: " + user.getName();
        String email = "Email: " + user.getEmail();
        return name + ", " + email;
    }
}
```

### Task

1. Write tests for `build(User)` method.
2. Refactor the class:
   - Extract methods for formatting
   - Improve naming
   - Apply SRP

### Example Test

```java
@Test
void shouldBuildUserReport() {
    User user = new User("Alice", "alice@example.com");
    ReportBuilder builder = new ReportBuilder();

    String report = builder.build(user);

    assertEquals("Name: Alice, Email: alice@example.com", report);
}
```

---

## 7. Architecture Refactoring

Break apart tightly coupled modules:

**Before**:
```java
public class UserService {
    public void register(User user) {
        saveToDb(user);
        sendWelcomeEmail(user);
    }
}
```

**After**:
```java
public class UserService {
    private final UserRepository repository;
    private final EmailService emailService;

    public void register(User user) {
        repository.save(user);
        emailService.sendWelcome(user);
    }
}
```

---

## Summary

In this session you:
- Practiced safe, incremental refactoring
- Applied SOLID principles to improve design
- Wrote and used tests to guide architectural improvements

Session 8 will cover Contract Testing using Spring Cloud Contract and Pact.
