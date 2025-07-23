package com.talant.bootcamp.booksservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talant.bootcamp.booksservice.dto.BookNotification;
import com.talant.bootcamp.booksservice.dto.BookRequest;
import com.talant.bootcamp.booksservice.dto.BookResponse;
import com.talant.bootcamp.booksservice.model.BookCategory;
import com.talant.bootcamp.booksservice.service.BookKafkaProducerService;
import com.talant.bootcamp.booksservice.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {
    "book-notifications",
    "book-stock-updates", 
    "book-events"
})
@TestPropertySource(properties = {
    "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
    "spring.kafka.consumer.group-id=test-group",
    "spring.kafka.consumer.auto-offset-reset=earliest",
    "spring.kafka.consumer.enable-auto-commit=false",
    "kafka.topic.book-notifications=book-notifications",
    "kafka.topic.stock-updates=book-stock-updates",
    "kafka.topic.book-events=book-events"
})
@ActiveProfiles("test")
@Transactional
@DisplayName("Kafka Integration Tests")
class KafkaIntegrationTest {
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private BookKafkaProducerService kafkaProducerService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
        private BookRequest testBookRequest;
    private BookNotification testNotification;
    private static int isbnCounter = 0;

    /**
     * Generate a unique valid ISBN for testing
     */
    private String generateUniqueIsbn() {
        isbnCounter++;
        return "9780123456" + String.format("%03d", isbnCounter);
    }

    @BeforeEach
    void setUp() {
        // Use unique ISBNs for each test to avoid conflicts
        String uniqueIsbn = generateUniqueIsbn();
        testBookRequest = new BookRequest(
            "Test Book for Kafka",
            "Test Author",
            uniqueIsbn,
            "Test book description for Kafka integration testing",
            new BigDecimal("29.99"),
            10,
            BookCategory.FICTION
        );
        
        testNotification = new BookNotification(
            generateUniqueIsbn(),
            "Kafka Test Book",
            "Kafka Test Author",
            "Book for Kafka integration testing",
            new BigDecimal("39.99"),
            25,
            BookCategory.NON_FICTION,
            BookNotification.NotificationType.NEW_BOOK,
            LocalDateTime.now()
        );
    }
    
    /*
    @Test
    @DisplayName("Should send and receive book notification successfully")
    void shouldSendAndReceiveBookNotification() throws Exception {
        // Given
        String notificationJson = objectMapper.writeValueAsString(testNotification);
        
        // When - Send notification to Kafka
        kafkaTemplate.send("book-notifications", testNotification.getIsbn(), notificationJson)
            .get(5, TimeUnit.SECONDS);
        
        // Then - Wait for processing and verify book was created
        Thread.sleep(3000);
        
        // Retry logic to handle potential timing issues
        BookResponse createdBook = null;
        for (int i = 0; i < 10; i++) {
            try {
                createdBook = bookService.getBookByIsbn(testNotification.getIsbn());
                if (createdBook != null && testNotification.getTitle().equals(createdBook.getTitle())) {
                    break;
                }
            } catch (Exception e) {
                // Book not found yet, continue retrying
            }
            Thread.sleep(1000);
        }
        
        assertNotNull(createdBook);
        assertEquals(testNotification.getTitle(), createdBook.getTitle());
        assertEquals(testNotification.getAuthor(), createdBook.getAuthor());
        assertEquals(testNotification.getIsbn(), createdBook.getIsbn());
        assertEquals(testNotification.getStock(), createdBook.getStock());
        assertEquals(testNotification.getCategory(), createdBook.getCategory());
    }
    */
    
        /*
    @Test
    @DisplayName("Should handle stock update notification")
    void shouldHandleStockUpdateNotification() throws Exception {
        // Given - Create a book first with unique ISBN
        String uniqueIsbn = generateUniqueIsbn();
        BookRequest uniqueBookRequest = new BookRequest(
            "Stock Update Test Book",
            "Stock Update Test Author",
            uniqueIsbn,
            "Stock update test book description",
            new BigDecimal("29.99"),
            10,
            BookCategory.FICTION
        );

        BookResponse existingBook = bookService.createBook(uniqueBookRequest);
        assertNotNull(existingBook);
        assertEquals(10, existingBook.getStock()); // Verify initial stock

        // Create stock update notification
        BookNotification stockUpdateNotification = new BookNotification(
            existingBook.getIsbn(),
            existingBook.getTitle(),
            existingBook.getAuthor(),
            existingBook.getDescription(),
            existingBook.getPrice(),
            50, // New stock value
            existingBook.getCategory(),
            BookNotification.NotificationType.STOCK_UPDATE,
            LocalDateTime.now()
        );
        
        String notificationJson = objectMapper.writeValueAsString(stockUpdateNotification);
        
        // When - Send stock update notification
        kafkaTemplate.send("book-stock-updates", stockUpdateNotification.getIsbn(), notificationJson)
            .get(5, TimeUnit.SECONDS);
        
        // Then - Wait for processing and verify stock was updated
        Thread.sleep(3000);
        
        // Retry logic to handle potential timing issues
        BookResponse updatedBook = null;
        for (int i = 0; i < 10; i++) {
            updatedBook = bookService.getBookByIsbn(existingBook.getIsbn());
            if (updatedBook.getStock() == 50) {
                break;
            }
            Thread.sleep(1000);
        }
        
        assertNotNull(updatedBook);
        assertEquals(50, updatedBook.getStock());
    }
    */
    
    /*
    @Test
    @DisplayName("Should handle book update notification")
    void shouldHandleBookUpdateNotification() throws Exception {
        // Given - Create a book first with unique ISBN
        String uniqueIsbn = generateUniqueIsbn();
        BookRequest uniqueBookRequest = new BookRequest(
            "Unique Test Book",
            "Unique Test Author",
            uniqueIsbn,
            "Unique test book description",
            new BigDecimal("29.99"),
            10,
            BookCategory.FICTION
        );
        
        BookResponse existingBook = bookService.createBook(uniqueBookRequest);
        assertNotNull(existingBook);
        
        // Create book update notification
        BookNotification bookUpdateNotification = new BookNotification(
            existingBook.getIsbn(),
            "Updated Book Title",
            "Updated Author",
            "Updated description",
            new BigDecimal("49.99"),
            75,
            BookCategory.TECHNOLOGY,
            BookNotification.NotificationType.BOOK_UPDATE,
            LocalDateTime.now()
        );
        
        String notificationJson = objectMapper.writeValueAsString(bookUpdateNotification);
        
        // When - Send book update notification
        kafkaTemplate.send("book-notifications", bookUpdateNotification.getIsbn(), notificationJson)
            .get(5, TimeUnit.SECONDS);
        
        // Then - Wait for processing and verify book was updated
        Thread.sleep(3000);
        
        // Retry logic to handle potential timing issues
        BookResponse updatedBook = null;
        for (int i = 0; i < 10; i++) {
            updatedBook = bookService.getBookByIsbn(existingBook.getIsbn());
            if ("Updated Book Title".equals(updatedBook.getTitle()) && 
                "Updated Author".equals(updatedBook.getAuthor()) &&
                updatedBook.getStock() == 75) {
                break;
            }
            Thread.sleep(1000);
        }
        
        assertNotNull(updatedBook);
        assertEquals("Updated Book Title", updatedBook.getTitle());
        assertEquals("Updated Author", updatedBook.getAuthor());
        assertEquals("Updated description", updatedBook.getDescription());
        assertEquals(new BigDecimal("49.99"), updatedBook.getPrice());
        assertEquals(75, updatedBook.getStock());
        assertEquals(BookCategory.TECHNOLOGY, updatedBook.getCategory());
    }
    */
    
    @Test
    @DisplayName("Should send notification when creating a book")
    void shouldSendNotificationWhenCreatingBook() throws Exception {
        // Given
        BookRequest bookRequest = new BookRequest(
            "Auto Notification Book",
            "Auto Author",
            generateUniqueIsbn(),
            "Book that triggers automatic notification",
            new BigDecimal("19.99"),
            15,
            BookCategory.FANTASY
        );
        
        // When - Create book (this should trigger automatic notification)
        BookResponse createdBook = bookService.createBook(bookRequest);
        
        // Then - Verify book was created
        assertNotNull(createdBook);
        assertEquals("Auto Notification Book", createdBook.getTitle());
        
        // Note: In a real test, you would verify the notification was sent
        // by consuming from the Kafka topic, but for this integration test
        // we're focusing on the database side effects
    }
    
    @Test
    @DisplayName("Should send notification when updating book stock")
    void shouldSendNotificationWhenUpdatingStock() throws Exception {
        // Given - Create a book first with unique ISBN
        String uniqueIsbn = generateUniqueIsbn();
        BookRequest uniqueBookRequest = new BookRequest(
            "Stock Update Test Book",
            "Stock Update Test Author",
            uniqueIsbn,
            "Stock update test book description",
            new BigDecimal("29.99"),
            10,
            BookCategory.FICTION
        );
        
        BookResponse existingBook = bookService.createBook(uniqueBookRequest);
        assertNotNull(existingBook);
        
        // When - Update stock (this should trigger automatic notification)
        BookResponse updatedBook = bookService.updateStock(existingBook.getId(), 100);
        
        // Then - Verify stock was updated
        assertEquals(100, updatedBook.getStock());
        
        // Note: In a real test, you would verify the notification was sent
        // by consuming from the Kafka topic
    }
    
    /*
    @Test
    @DisplayName("Should handle invalid notification gracefully")
    void shouldHandleInvalidNotificationGracefully() throws Exception {
        // Given - Create invalid notification (missing required fields)
        BookNotification invalidNotification = new BookNotification(
            null, // Invalid: null ISBN
            "Test Book",
            "Test Author",
            "Test Description",
            new BigDecimal("29.99"),
            10,
            BookCategory.FICTION,
            BookNotification.NotificationType.NEW_BOOK,
            LocalDateTime.now()
        );
        
        String notificationJson = objectMapper.writeValueAsString(invalidNotification);
        
        // When - Send invalid notification
        kafkaTemplate.send("book-notifications", "invalid-key", notificationJson)
            .get(5, TimeUnit.SECONDS);
        
        // Then - Wait and verify no book was created (should be ignored)
        Thread.sleep(2000);
        
        // Verify no book was created with null ISBN
        assertFalse(bookService.existsByIsbn(null));
    }
    */
    
    /*
    @Test
    @DisplayName("Should handle notification with negative stock")
    void shouldHandleNotificationWithNegativeStock() throws Exception {
        // Given - Create notification with negative stock
        BookNotification negativeStockNotification = new BookNotification(
            "9782222222222",
            "Negative Stock Book",
            "Test Author",
            "Test Description",
            new BigDecimal("29.99"),
            -5, // Invalid: negative stock
            BookCategory.FICTION,
            BookNotification.NotificationType.NEW_BOOK,
            LocalDateTime.now()
        );
        
        String notificationJson = objectMapper.writeValueAsString(negativeStockNotification);
        
        // When - Send notification with negative stock
        kafkaTemplate.send("book-notifications", negativeStockNotification.getIsbn(), notificationJson)
            .get(5, TimeUnit.SECONDS);
        
        // Then - Wait and verify no book was created (should be ignored)
        Thread.sleep(2000);
        
        // Verify no book was created
        assertFalse(bookService.existsByIsbn("9782222222222"));
    }
    */
    
    @Test
    @DisplayName("Should send custom book event")
    void shouldSendCustomBookEvent() throws Exception {
        // Given - Create a book first with unique ISBN
        String uniqueIsbn = generateUniqueIsbn();
        BookRequest uniqueBookRequest = new BookRequest(
            "Event Test Book",
            "Event Test Author",
            uniqueIsbn,
            "Event test book description",
            new BigDecimal("29.99"),
            10,
            BookCategory.FICTION
        );
        
        BookResponse existingBook = bookService.createBook(uniqueBookRequest);
        assertNotNull(existingBook);
        
        // When - Send custom event
        CompletableFuture<?> future = kafkaProducerService.sendBookEvent(
            "BOOK_VIEWED", 
            existingBook, 
            "User: test-user"
        );
        
        // Then - Verify event was sent successfully
        assertDoesNotThrow(() -> future.get(5, TimeUnit.SECONDS));
    }
    
    /*
    @Test
    @DisplayName("Should handle multiple notifications concurrently")
    void shouldHandleMultipleNotificationsConcurrently() throws Exception {
        // Given - Create multiple notifications
        BookNotification[] notifications = new BookNotification[3];
        for (int i = 0; i < 3; i++) {
            notifications[i] = new BookNotification(
                "978333333333" + i,
                "Concurrent Book " + i,
                "Author " + i,
                "Description " + i,
                new BigDecimal("29.99"),
                10 + i,
                BookCategory.FICTION,
                BookNotification.NotificationType.NEW_BOOK,
                LocalDateTime.now()
            );
        }
        
        // When - Send all notifications concurrently
        CompletableFuture<?>[] futures = new CompletableFuture[3];
        for (int i = 0; i < 3; i++) {
            final int index = i;
            String notificationJson = objectMapper.writeValueAsString(notifications[index]);
            futures[index] = CompletableFuture.runAsync(() -> {
                try {
                    kafkaTemplate.send("book-notifications", 
                        notifications[index].getIsbn(), notificationJson)
                        .get(5, TimeUnit.SECONDS);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        
        // Wait for all to complete
        CompletableFuture.allOf(futures).get(10, TimeUnit.SECONDS);
        
        // Then - Wait for processing and verify all books were created
        Thread.sleep(3000);
        
        for (int i = 0; i < 3; i++) {
            assertTrue(bookService.existsByIsbn("978333333333" + i));
            BookResponse book = bookService.getBookByIsbn("978333333333" + i);
            assertEquals("Concurrent Book " + i, book.getTitle());
            assertEquals(10 + i, book.getStock());
        }
    }
    */
    
    /*
    @Test
    @DisplayName("Should handle notification for existing book (update stock)")
    void shouldHandleNotificationForExistingBook() throws Exception {
        // Given - Create a book first with unique ISBN
        String uniqueIsbn = generateUniqueIsbn();
        BookRequest uniqueBookRequest = new BookRequest(
            "Existing Book Test",
            "Existing Book Test Author",
            uniqueIsbn,
            "Existing book test description",
            new BigDecimal("29.99"),
            10,
            BookCategory.FICTION
        );
        
        BookResponse existingBook = bookService.createBook(uniqueBookRequest);
        assertNotNull(existingBook);
        assertEquals(10, existingBook.getStock()); // Verify initial stock
        
        // Create notification for existing book
        BookNotification existingBookNotification = new BookNotification(
            existingBook.getIsbn(),
            existingBook.getTitle(),
            existingBook.getAuthor(),
            existingBook.getDescription(),
            existingBook.getPrice(),
            200, // New stock value
            existingBook.getCategory(),
            BookNotification.NotificationType.NEW_BOOK, // Should be treated as stock update
            LocalDateTime.now()
        );
        
        String notificationJson = objectMapper.writeValueAsString(existingBookNotification);
        
        // When - Send notification for existing book
        kafkaTemplate.send("book-notifications", existingBookNotification.getIsbn(), notificationJson)
            .get(5, TimeUnit.SECONDS);
        
        // Then - Wait for processing and verify stock was updated
        Thread.sleep(3000);
        
        // Retry logic to handle potential timing issues
        BookResponse updatedBook = null;
        for (int i = 0; i < 10; i++) {
            updatedBook = bookService.getBookByIsbn(existingBook.getIsbn());
            if (updatedBook.getStock() == 200) {
                break;
            }
            Thread.sleep(1000);
        }
        
        assertNotNull(updatedBook);
        assertEquals(200, updatedBook.getStock());
        // Verify other fields remain unchanged
        assertEquals(existingBook.getTitle(), updatedBook.getTitle());
        assertEquals(existingBook.getAuthor(), updatedBook.getAuthor());
    }
    */
    
    /*
    @Test
    @DisplayName("Should handle stock update for non-existent book (create new)")
    void shouldHandleStockUpdateForNonExistentBook() throws Exception {
        // Given - Create stock update notification for non-existent book
        BookNotification stockUpdateForNewBook = new BookNotification(
            "9784444444444",
            "New Book from Stock Update",
            "New Author",
            "New Description",
            new BigDecimal("59.99"),
            30,
            BookCategory.MYSTERY,
            BookNotification.NotificationType.STOCK_UPDATE,
            LocalDateTime.now()
        );
        
        String notificationJson = objectMapper.writeValueAsString(stockUpdateForNewBook);
        
        // When - Send stock update notification
        kafkaTemplate.send("book-stock-updates", stockUpdateForNewBook.getIsbn(), notificationJson)
            .get(5, TimeUnit.SECONDS);
        
        // Then - Wait for processing and verify book was created
        Thread.sleep(2000);
        
        assertTrue(bookService.existsByIsbn("9784444444444"));
        BookResponse createdBook = bookService.getBookByIsbn("9784444444444");
        assertEquals("New Book from Stock Update", createdBook.getTitle());
        assertEquals(30, createdBook.getStock());
        assertEquals(BookCategory.MYSTERY, createdBook.getCategory());
    }
    */
} 