# 📚 Bookstore Microservice

A complete microservice for managing books, built with Spring Boot, H2 Database, and JPA.

## ✅ Features

- In-memory H2 database
- Full REST API with validation
- Layered architecture (Controller, Service, Repository)
- Global exception handling
- Unit tests with JUnit 5 and Mockito
- Integration tests using AssertJ
- Code coverage with JaCoCo
- Auto-loaded sample data
- Complete documentation

## 🔧 Technologies Used

- Spring Boot 3.5.3
- Spring Data JPA
- H2 Database
- JUnit 5
- Mockito
- AssertJ
- JaCoCo
- Maven

## 📁 Project Structure

```
src/
├── main/java/com/talant/bootcamp/demoservice/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── model/
│   ├── dto/
│   ├── exception/
│   └── config/
└── test/java/com/talant/bootcamp/demoservice/
    ├── service/
    ├── controller/
    └── integration/
```

## 🚀 How to Run

### Prerequisites

- Java 17+
- Maven 3.6+

### Steps

1. Clone the repo:
   ```bash
   git clone <repository-url>
   cd demoservice
   ```

2. Compile:
   ```bash
   mvn clean compile
   ```

3. Run tests:
   ```bash
   mvn test
   ```

4. Run the app:
   ```bash
   mvn spring-boot:run
   ```

5. Generate code coverage:
   ```bash
   mvn jacoco:report
   ```

## 🔍 API Highlights

- `GET /api/books` — List all books
- `POST /api/books` — Add a new book
- `GET /api/books/{id}` — Get book by ID
- `GET /api/books/isbn/{isbn}` — Get book by ISBN
- `PUT /api/books/{id}` — Update book
- `DELETE /api/books/{id}` — Delete book
- `GET /api/books/search?q=term` — Search by title/author
- `GET /api/books/category/{category}` — Filter by category
- `GET /api/books/in-stock` — Books in stock
- `PATCH /api/books/{id}/stock?stock=30` — Update stock

## 🧪 Testing

```bash
mvn test                    # All tests
mvn test -Dtest=*Test       # Unit tests
mvn test -Dtest=*IntegrationTest # Integration tests
```

## 💻 H2 Console

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:bookstoredb`
- Username: `sa`
- Password: `password`

## 📚 Sample Books

15 predefined books are loaded on startup including:
- The Lord of the Rings
- 1984
- Clean Code
- Design Patterns
- The Little Prince
...

## 🤝 Contributing

1. Fork this repo
2. Create a branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add feature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License.
