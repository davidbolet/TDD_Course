package com.talant.bootcamp.booksservice.integration;

import com.talant.bootcamp.booksservice.dto.BookRequest;
import com.talant.bootcamp.booksservice.dto.BookResponse;
import com.talant.bootcamp.booksservice.model.BookCategory;
import com.talant.bootcamp.booksservice.repository.BookRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
@DisplayName("Second Book Integration Tests with RestAssured")
class SecondBookIntegrationTest {
    
    @LocalServerPort
    private int port;
    
    @Autowired
    private BookRepository bookRepository;
    
    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        bookRepository.deleteAll();
    }
    
    @Test
    @DisplayName("Should create and retrieve a book")
    void shouldCreateAndRetrieveBook() {
        // Given
        BookRequest bookRequest = new BookRequest(
            "Test Book",
            "Test Author",
            "1234567890",
            "Test Description",
            new BigDecimal("29.99"),
            10,
            BookCategory.FICTION
        );
        
        // When & Then - Create book
        Response createResponse = given()
            .contentType(ContentType.JSON)
            .body(bookRequest)
            .when()
            .post("/api/books")
            .then()
            .statusCode(201)
            .body("title", equalTo("Test Book"))
            .body("author", equalTo("Test Author"))
            .body("isbn", equalTo("1234567890"))
            .extract()
            .response();
        
        BookResponse createdBook = createResponse.as(BookResponse.class);
        
        // When & Then - Retrieve book
        given()
            .when()
            .get("/api/books/" + createdBook.getId())
            .then()
            .statusCode(200)
            .body("id", equalTo(createdBook.getId().intValue()))
            .body("title", equalTo("Test Book"));
    }
    
    @Test
    @DisplayName("Should update an existing book")
    void shouldUpdateExistingBook() {
        // Given - Create book
        
        
        // Given - Update request
        
        // When & Then - Update book
        
    }
    
    @Test
    @DisplayName("Should delete a book")
    void shouldDeleteBook() {
        // Given - Create book
        // When & Then - Delete book
        
        // When & Then - Verify book is deleted

    }
    
    @Test
    @DisplayName("Should search books by author")
    void shouldSearchBooksByAuthor() {
        // Given - Create multiple books
        createTestBooks();
    
        // When & Then - Search by author
    }
    
    @Test
    @DisplayName("Should search books by title")
    void shouldSearchBooksByTitle() {
        // Given - Create multiple books
        createTestBooks();
        
        // When & Then - Search by title
        given()
            .when()
            .get("/api/books/title/Rings")
            .then()
            .statusCode(200)
            .body("$", hasSize(greaterThan(0)))
            .body("[0].title", equalTo("The Lord of the Rings"));
    }
    
    @Test
    @DisplayName("Should search books by category")
    void shouldSearchBooksByCategory() {
   
    }
    
    @Test
    @DisplayName("Should get books with stock available")
    void shouldGetBooksWithStock() {

    }
    
    @Test
    @DisplayName("Should get books without stock")
    void shouldGetBooksOutOfStock() {
        // Given - Create book without stock
        BookRequest bookRequest = new BookRequest(
            "Out of Stock Book",
            "Author",
            "2222222222",
            "Description",
            new BigDecimal("10.00"),
            0,
            BookCategory.FICTION
        );
        
        given()
            .contentType(ContentType.JSON)
            .body(bookRequest)
            .when()
            .post("/api/books")
            .then()
            .statusCode(201);
        
        // When & Then - Get books without stock
        given()
            .when()
            .get("/api/books/out-of-stock")
            .then()
            .statusCode(200)
            .body("$", hasSize(greaterThan(0)))
            .body("[0].stock", equalTo(0));
    }
    
    @Test
    @DisplayName("Should search books by text")
    void shouldSearchBooksByText() {
      
    }
    
    @Test
    @DisplayName("Should update stock of a book")
    void shouldUpdateBookStock() {
        // Given - Create book
        BookRequest bookRequest = new BookRequest(
            "Stock Test Book",
            "Author",
            "3333333333",
            "Description",
            new BigDecimal("20.00"),
            10,
            BookCategory.FICTION
        );
        
        Response createResponse = given()
            .contentType(ContentType.JSON)
            .body(bookRequest)
            .when()
            .post("/api/books")
            .then()
            .statusCode(201)
            .extract()
            .response();
        
        BookResponse createdBook = createResponse.as(BookResponse.class);
        
        // When & Then - Update stock
        given()
            .param("stock", "25")
            .when()
            .patch("/api/books/" + createdBook.getId() + "/stock")
            .then()
            .statusCode(200)
            .body("stock", equalTo(25));
    }
    
    @Test
    @DisplayName("Should check if a book exists by ISBN")
    void shouldCheckExistsByIsbn() {
        // Given - Create book
        BookRequest bookRequest = new BookRequest(
            "Test Book",
            "Author",
            "4444444444",
            "Description",
            new BigDecimal("15.00"),
            5,
            BookCategory.FICTION
        );
        
        given()
            .contentType(ContentType.JSON)
            .body(bookRequest)
            .when()
            .post("/api/books")
            .then()
            .statusCode(201);
        
        // When & Then - Verify existence
        given()
            .when()
            .get("/api/books/exists/4444444444")
            .then()
            .statusCode(200)
            .body(equalTo("true"));
        
        // When & Then - Verify non-existence
        given()
            .when()
            .get("/api/books/exists/9999999999")
            .then()
            .statusCode(200)
            .body(equalTo("false"));
    }
    
    @Test
    @DisplayName("Should get available categories")
    void shouldGetCategories() {
        // When & Then
        given()
            .when()
            .get("/api/books/categories")
            .then()
            .statusCode(200)
            .body("$", hasSize(greaterThan(0)))
            .body("[0]", equalTo("Fiction"));
    }
    
    @Test
    @DisplayName("Should handle duplicate ISBN")
    void shouldHandleDuplicateIsbn() {
        // Given - Create first book
        BookRequest bookRequest1 = new BookRequest(
            "First Book",
            "Author 1",
            "5555555555",
            "Description 1",
            new BigDecimal("20.00"),
            10,
            BookCategory.FICTION
        );
        
        given()
            .contentType(ContentType.JSON)
            .body(bookRequest1)
            .when()
            .post("/api/books")
            .then()
            .statusCode(201);
        
        // Given - Second book with same ISBN
        BookRequest bookRequest2 = new BookRequest(
            "Second Book",
            "Author 2",
            "5555555555", // Same ISBN
            "Description 2",
            new BigDecimal("25.00"),
            15,
            BookCategory.MYSTERY
        );
        
        // When & Then - Try to create book with duplicate ISBN
        given()
            .contentType(ContentType.JSON)
            .body(bookRequest2)
            .when()
            .post("/api/books")
            .then()
            .statusCode(409)
            .body("error", equalTo("Duplicate ISBN"));
    }
    
    @Test
    @DisplayName("Should validate required fields")
    void shouldValidateRequiredFields() {
        // Given - Request without required fields
        BookRequest invalidRequest = new BookRequest();
        
        // When & Then
        given()
            .contentType(ContentType.JSON)
            .body(invalidRequest)
            .when()
            .post("/api/books")
            .then()
            .statusCode(400)
            .body("error", equalTo("Validation error"));
    }
    
    @Test
    @DisplayName("Should handle book not found")
    void shouldHandleBookNotFound() {
        // When & Then
        given()
            .when()
            .get("/api/books/999")
            .then()
            .statusCode(404)
            .body("error", equalTo("Book not found"));
    }
    
    private void createTestBooks() {
        // Genera ISBNs aleatorios válidos de 10 dígitos
        String isbn1 = String.valueOf(System.currentTimeMillis()).substring(3, 13);
        String isbn2 = String.valueOf(System.nanoTime()).substring(3, 13);
        String isbn3 = String.valueOf((System.currentTimeMillis() + System.nanoTime()) % 10000000000L + 1000000000L);

        BookRequest[] bookRequests = {
            new BookRequest("The Lord of the Rings", "J.R.R. Tolkien", isbn1,
                          "Epic fantasy", new BigDecimal("29.99"), 50, BookCategory.FANTASY),
            new BookRequest("1984", "George Orwell", isbn2,
                          "Dystopia", new BigDecimal("19.99"), 30, BookCategory.FICTION),
            new BookRequest("Clean Code", "Robert C. Martin", isbn3,
                          "Programming", new BigDecimal("45.99"), 15, BookCategory.TECHNOLOGY)
        };

        for (BookRequest request : bookRequests) {
            given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/books")
                .then()
                .statusCode(201);
        }
    }
} 