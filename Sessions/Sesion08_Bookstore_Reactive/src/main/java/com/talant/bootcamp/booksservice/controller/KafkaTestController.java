package com.talant.bootcamp.booksservice.controller;

import com.talant.bootcamp.booksservice.dto.BookNotification;
import com.talant.bootcamp.booksservice.dto.BookResponse;
import com.talant.bootcamp.booksservice.model.BookCategory;
import com.talant.bootcamp.booksservice.service.BookKafkaProducerService;
import com.talant.bootcamp.booksservice.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

/**
 * Controller for testing Kafka functionality
 */
@RestController
@RequestMapping("/api/kafka")
@CrossOrigin(origins = "*")
public class KafkaTestController {
    
    private static final Logger logger = LoggerFactory.getLogger(KafkaTestController.class);
    
    private final BookKafkaProducerService kafkaProducerService;
    private final BookService bookService;
    
    @Autowired
    public KafkaTestController(BookKafkaProducerService kafkaProducerService, BookService bookService) {
        this.kafkaProducerService = kafkaProducerService;
        this.bookService = bookService;
    }
    
    /**
     * Send a test book notification
     */
    @PostMapping("/send-notification")
    public ResponseEntity<String> sendTestNotification(@RequestBody BookNotification notification) {
        try {
            logger.info("Sending test notification: {}", notification);
            
            CompletableFuture<?> future = kafkaProducerService.sendCustomNotification(notification);
            
            future.whenComplete((result, throwable) -> {
                if (throwable != null) {
                    logger.error("Failed to send notification", throwable);
                } else {
                    logger.info("Successfully sent notification");
                }
            });
            
            return ResponseEntity.ok("Notification sent successfully");
        } catch (Exception e) {
            logger.error("Error sending notification", e);
            return ResponseEntity.internalServerError().body("Error sending notification: " + e.getMessage());
        }
    }
    
    /**
     * Send a test new book notification
     */
    @PostMapping("/send-new-book")
    public ResponseEntity<String> sendNewBookNotification(@RequestParam Long bookId) {
        try {
            logger.info("Sending new book notification for book ID: {}", bookId);
            
            BookResponse book = bookService.getBookById(bookId);
            CompletableFuture<?> future = kafkaProducerService.sendNewBookNotification(book);
            
            future.whenComplete((result, throwable) -> {
                if (throwable != null) {
                    logger.error("Failed to send new book notification", throwable);
                } else {
                    logger.info("Successfully sent new book notification");
                }
            });
            
            return ResponseEntity.ok("New book notification sent successfully");
        } catch (Exception e) {
            logger.error("Error sending new book notification", e);
            return ResponseEntity.internalServerError().body("Error sending new book notification: " + e.getMessage());
        }
    }
    
    /**
     * Send a test stock update notification
     */
    @PostMapping("/send-stock-update")
    public ResponseEntity<String> sendStockUpdateNotification(@RequestParam Long bookId) {
        try {
            logger.info("Sending stock update notification for book ID: {}", bookId);
            
            BookResponse book = bookService.getBookById(bookId);
            CompletableFuture<?> future = kafkaProducerService.sendStockUpdateNotification(book);
            
            future.whenComplete((result, throwable) -> {
                if (throwable != null) {
                    logger.error("Failed to send stock update notification", throwable);
                } else {
                    logger.info("Successfully sent stock update notification");
                }
            });
            
            return ResponseEntity.ok("Stock update notification sent successfully");
        } catch (Exception e) {
            logger.error("Error sending stock update notification", e);
            return ResponseEntity.internalServerError().body("Error sending stock update notification: " + e.getMessage());
        }
    }
    
    /**
     * Send a test book event
     */
    @PostMapping("/send-event")
    public ResponseEntity<String> sendBookEvent(
            @RequestParam Long bookId,
            @RequestParam String eventType,
            @RequestParam(required = false) String additionalData) {
        try {
            logger.info("Sending book event: {} for book ID: {}", eventType, bookId);
            
            BookResponse book = bookService.getBookById(bookId);
            CompletableFuture<?> future = kafkaProducerService.sendBookEvent(eventType, book, additionalData);
            
            future.whenComplete((result, throwable) -> {
                if (throwable != null) {
                    logger.error("Failed to send book event", throwable);
                } else {
                    logger.info("Successfully sent book event");
                }
            });
            
            return ResponseEntity.ok("Book event sent successfully");
        } catch (Exception e) {
            logger.error("Error sending book event", e);
            return ResponseEntity.internalServerError().body("Error sending book event: " + e.getMessage());
        }
    }
    
    /**
     * Create a sample book notification for testing
     */
    @GetMapping("/sample-notification")
    public ResponseEntity<BookNotification> getSampleNotification() {
        BookNotification sampleNotification = new BookNotification(
            "9780123456789",
            "Sample Book Title",
            "Sample Author",
            "This is a sample book description for testing Kafka notifications.",
            new BigDecimal("29.99"),
            10,
            BookCategory.FICTION,
            BookNotification.NotificationType.NEW_BOOK,
            LocalDateTime.now()
        );
        
        return ResponseEntity.ok(sampleNotification);
    }
} 