# JMeter Test Plan for Book Service API

This JMeter Test Plan is designed to test a Spring Boot-based REST API for a book management microservice.

## ✅ Purpose
This plan simulates HTTP requests to the following endpoints:

- `GET /api/books` - Retrieve all books
- `POST /api/books` - Create a new book
- `DELETE /api/books/{id}` - Delete a book by ID

It validates:
- API responsiveness
- Concurrent access behavior
- HTTP response codes and payloads

---

## ⚙️ Prerequisites

- Java 17+
- Apache Maven
- Apache JMeter (https://jmeter.apache.org/)
- The Spring Boot app running locally:
  ```bash
  mvn spring-boot:run
  ```

---

## 🚀 How to Use the Test Plan

1. Launch JMeter GUI:
   - Windows: `bin/jmeter.bat`
   - Mac/Linux: `bin/jmeter.sh`

2. Open the provided test plan file:
   ```
   File > Open > bookservice_testplan.jmx
   ```

3. Make sure the Spring Boot app is running at `http://localhost:8080`.

4. Review and customize Thread Group settings if needed:
   - Number of Threads (Users): 10
   - Ramp-Up Period: 5 seconds
   - Loop Count: 2

5. Run the test by clicking the green ▶️ start button.

6. Observe results using:
   - **View Results Tree**
   - **Summary Report**
   - **Aggregate Graph**

---

## 📦 Included HTTP Requests

### 1. GET /api/books
- Checks availability of the books endpoint.

### 2. POST /api/books
- Sends a sample book in JSON format.
- Header: `Content-Type: application/json`

### 3. DELETE /api/books/1
- Deletes a book with ID `1`. You may adjust this ID depending on your dataset.

---

## 📈 Optional Enhancements

- Use JSON Extractors to validate response content.
- Add Assertions to verify status codes and fields.
- Use CSV DataSet Config for testing with multiple payloads.
