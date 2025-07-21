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

The following classes contain design issues:

### 1. `ReviewService.java`
- Violates **DIP**: Instantiates a concrete repository internally
- Violates **SRP**: Contains validation and business logic mixed together

### 2. `ReviewController.java`
- Violates **DIP**: Instantiates service directly
- Mixes concerns by managing business logic and HTTP logic

### 3. `InventoryAlertService.java`
- Violates **OCP**: Hardcoded stock threshold (cannot extend alert logic without modifying the class)

### 4. `BookOrder.java`
- Violates **SRP**: Contains business logic (`calculateTotal`) inside the model

---

## 🛠️ Your Tasks

### 🧼 Refactor Tasks
- Extract interfaces where missing
- Use constructor injection for dependencies
- Split validation or logic into their own classes
- Parameterize configurable values (like stock thresholds)

### 🧪 Testing Tasks
- Write unit tests for:
  - `ReviewService`
  - `InventoryAlertService`
  - Any refactored logic
- Apply TDD: Write tests **before** each refactor
- Mock dependencies where applicable

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
