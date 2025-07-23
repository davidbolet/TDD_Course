
# frauddetectionservice

This microservice is part of the **Banking System TDD Workshop**. Students will develop and test functionality using **Test-Driven Development (TDD)** practices.

---

## ✅ Objective

Develop and test the main business logic for the **Fraud Detection Service** microservice using TDD.

---

## 🧪 Tasks

1. **Write tests first** using JUnit 5 and Mockito.
2. Develop the necessary classes to pass the tests.
3. Apply validations and proper domain rules.
4. Refactor and improve code based on feedback from tests.
5. Ensure 90%+ test coverage.

---

## 💡 Suggested Features to Develop

- Simulate simple fraud detection rule: flag transfers > 5000
- Expose `/fraud/check` API to evaluate transaction
- Add logging and decision explanation

---

## 🛠 Tech Stack

- Java 17
- Spring Boot 3.x
- Maven
- JUnit 5 + Mockito
- JaCoCo for code coverage

---

## 🧵 TDD Development Flow

1. Write a failing test
2. Write minimal code to pass it
3. Refactor
4. Repeat

---

## 📂 Folder Structure

- `src/main/java` — business logic
- `src/test/java` — unit tests
- `README.md` — this file

---

## 📌 Notes

- Use `@Mock` and `@InjectMocks` for service layer testing
- Follow SOLID principles and Clean Code
- Use `@WebMvcTest` for controller tests

Happy testing!
