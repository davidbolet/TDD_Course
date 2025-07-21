# Book Service – SOLID Violations Version

This version of the Book Service project intentionally contains **violations of SOLID principles**. It's designed for educational purposes so that students can practice identifying and refactoring bad design while applying TDD practices.

---

## 🎯 Objective

You will:
- Identify violations of SOLID principles (SRP, DIP, OCP)
- Refactor the affected classes to apply best practices
- Write unit tests before and after refactoring (TDD)

---

## 🔎 What to Look At

- Think about which SOLID principles are violated here and refactor accordingly
- Add corrsponding Unit tests
The following classes should be reviewed:

### 1. `ReviewService`

### 2. `ReviewController`

### 3. `InventoryAlertService.java`

### 4. `BookOrder.java`

---

## 🛠️ Your Tasks

### 🧼 Refactor Tasks
- Extract interfaces where missing
- Use constructor injection for dependencies
- Split validation or logic where they should be
- Make it configurable
- (Optional) Create a OrderService to order books
=======

### 🧪 Testing Tasks
- Write unit tests for new classes and refactored logic
- Apply TDD: Try to write tests **before** each refactor
- Mock dependencies where applicable
- Finally, add integration test

---

## 💡 Tips

- Use `@Mock` and `@InjectMocks` from Mockito to isolate units
- Apply the **Arrange-Act-Assert (AAA)** pattern in tests
- Ensure all refactored classes are tested independently
- Keep existing behavior unchanged (green tests after refactor)

---

## ✅ Outcome

By the end of this activity, you should:
- Have a clean, testable, and SOLID-compliant design
- Understand the impact of clean architecture on maintainability
- Be confident writing and refactoring tests

---

This project is part of the **TDD and Clean Code Workshop**. Happy refactoring!
