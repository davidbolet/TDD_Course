# Session 10: Final TDD Workshop

## Objectives

- Apply all TDD practices learned in a complete development cycle.
- Design and implement a full feature using tests first.
- Combine unit, integration, and mock-based tests.
- Review code quality, coverage, and architecture.

---

## 1. Project Overview

You will develop a small CRUD functionality using **Spring Boot**, **TDD**, and **Clean Code** principles.

### Suggested Domain: Task Manager

Entity: `Task`
- `id`: Long
- `title`: String
- `description`: String
- `completed`: boolean

---

## 2. TDD Process Recap

Follow the full TDD cycle:

1. **Red** – Write a failing test.
2. **Green** – Make the test pass.
3. **Refactor** – Improve the code with tests as safety net.

---

## 3. Implementation Guidelines

### Step 1: Write the first unit test for service

```java
@Test
void shouldMarkTaskAsCompleted() {
    Task task = new Task(1L, "Clean code", "Write tests", false);
    when(repository.findById(1L)).thenReturn(Optional.of(task));

    service.completeTask(1L);

    assertTrue(task.isCompleted());
}
```

### Step 2: Implement service logic

```java
public void completeTask(Long id) {
    Task task = repository.findById(id)
        .orElseThrow(() -> new TaskNotFoundException(id));
    task.setCompleted(true);
    repository.save(task);
}
```

### Step 3: Add integration test

```java
@SpringBootTest
@AutoConfigureMockMvc
class TaskIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldCreateTask() throws Exception {
        String json = "{\"title\": \"Refactor\", \"description\": \"Clean the service layer\"}";

        mockMvc.perform(post("/tasks")
                .contentType("application/json")
                .content(json))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value("Refactor"));
    }
}
```

---

## 4. Code Review and Coverage

Use **JaCoCo** and **SonarQube** to analyze:
- Test coverage
- Duplicates
- Code smells
- Cyclomatic complexity

Generate reports:

```bash
mvn verify sonar:sonar
```

Or view HTML report:

```
target/site/jacoco/index.html
```

---

## 5. Exercise: Build Complete Task Feature

### Deliverables:
- CRUD endpoints for Task entity
- Tests using TDD
- Mocks and integration tests
- CI pipeline config (optional)
- Clean code and refactored architecture

---

## Summary

You have now:
- Built a real feature from scratch using **Test-Driven Development**
- Written clean, tested, and maintainable code
- Practiced unit, integration, and reactive testing
- Automated testing with CI/CD and verified code quality

🎉 Congratulations on completing the **TDD and Clean Code** course!
