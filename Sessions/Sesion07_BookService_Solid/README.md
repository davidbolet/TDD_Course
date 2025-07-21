# Book Service – SOLID Refactored Version

A Spring Boot microservice for managing books, reviews, orders, and stock alerts. This version has been refactored to follow SOLID principles and improve maintainability, testability, and architecture clarity.

---

## ✅ Features

- Manage books with CRUD operations and filtering
- Submit and view book reviews (`ReviewService`)
- Detect low-stock alerts (`InventoryAlertService`)
- Place book orders (`BookOrder` model only)
- In-memory storage for simplicity (stub-style repositories)

---

## 🧠 SOLID Principles Applied

- **SRP (Single Responsibility):**
  - Separated validation logic from service operations
  - `ReviewService` handles only review logic
  - `InventoryAlertService` handles only stock checking

- **DIP (Dependency Inversion):**
  - All services depend on interfaces, not concrete implementations
  - Review repository injected via `ReviewService` constructor

- **OCP (Open/Closed):**
  - `InventoryAlertService` accepts a threshold parameter for alerts

- **ISP / LSP:** Not directly addressed in this context but classes are extensible and interface-based.

---

## 📁 Project Structure

```
src/
└── main/
    └── java/com/talant/bootcamp/booksservice/
        ├── controller/
        │   └── ReviewController.java
        ├── service/
        │   ├── ReviewService.java
        │   └── InventoryAlertService.java
        ├── model/
        │   ├── BookOrder.java
        │   └── Review.java
        └── repository/
            ├── ReviewRepository.java (interface)
            └── InMemoryReviewRepository.java (implementation)
```

---

## 🚀 How to Run

### Prerequisites

- Java 17+
- Maven 3.8+

### Run the App

```bash
mvn clean install
mvn spring-boot:run
```

---

## 📚 API Endpoints

### Reviews

- `POST /api/reviews` — Add a new review
- `GET /api/reviews/{bookId}` — Get reviews for a book

*(Book, Order, and Alert endpoints to be added or integrated with existing project structure)*

---

## 🔬 Notes

- This project is designed for educational use in TDD and Clean Code workshops.
- All services and logic are simplified and test-friendly.
- Data is stored in memory (non-persistent).
