# Session 1: TDD Basics and Clean Code

## Objectives

- Understand the Red-Green-Refactor cycle.
- Set up the environment with Spring Boot and JUnit 5.
- Write your first TDD tests using JUnit.
- Learn the fundamentals of Clean Code and its relevance to testing.
- Practice with simple exercises: FizzBuzz and Roman Numerals.

---

## 1. Introduction to Test-Driven Development

**TDD** is a software development approach where tests are written before writing the functional code. The cycle is:

1. **Red**: Write a failing test.
2. **Green**: Write minimal code to make the test pass.
3. **Refactor**: Improve the code while keeping tests green.

```java
// Example: A simple adder using TDD

@Test
void shouldAddTwoNumbers() {
    Calculator calculator = new Calculator();
    int result = calculator.add(2, 3);
    assertEquals(5, result);
}

// Functional code
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
}
```

---

## 2. Environment Setup

You will need:
- **Java 17+**
- **Spring Boot 3+**
- **Maven or Gradle**
- **JUnit 5 (Jupiter API)**

Create a Spring Boot app with the following dependencies:
- `spring-boot-starter-test`

Verify JUnit 5 is active:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

---

## 3. JUnit 5 Basics

JUnit 5 includes:
- **Platform**: Launching framework
- **Jupiter**: New programming model and annotations
- **Vintage**: Support for JUnit 3 & 4

Useful annotations:
```java
@BeforeEach  // runs before each test
@AfterEach   // runs after each test
@Test        // declares a test
@Disabled    // disables a test
```

Assertions:
```java
assertEquals(expected, actual);
assertTrue(condition);
assertThrows(Exception.class, () -> method());
```

---

## 4. Clean Code Philosophy

Based on *Robert C. Martin (Uncle Bob)*'s work:
- Meaningful names
- Small functions
- Clear separation of concerns
- Avoid duplication
- Use expressive test cases

Example:
```java
// Bad
int r = c.p(5, 10);

// Clean
int result = calculator.add(5, 10);
```

---

## 5. Refactor While Keeping Tests Green

Always refactor after writing tests. Clean up:
- Duplicated code
- Magic numbers
- Verbose logic

Use IDE tools and rely on green tests to ensure safety.

---

## 6. Exercises

### Exercise 1: FizzBuzz (TDD Style)

Write a class that returns:
- “Fizz” for multiples of 3
- “Buzz” for multiples of 5
- “FizzBuzz” for both
- The number as a string otherwise

Example test:
```java
@Test
void returnsFizzForMultiplesOf3() {
    assertEquals("Fizz", fizzBuzz.say(3));
}
```

### Exercise 2: Roman Numerals

Convert integers to Roman numerals using TDD.

Test case:
```java
@Test
void converts1ToI() {
    assertEquals("I", romanNumeral.convert(1));
}
```

Challenge yourself by following Red-Green-Refactor strictly.

---

## Summary

You have now:
- Understood the TDD cycle
- Practiced with JUnit 5
- Written your first test-driven Java code
- Applied Clean Code ideas from day one

Move on to Session 2 to structure your tests and projects effectively.
