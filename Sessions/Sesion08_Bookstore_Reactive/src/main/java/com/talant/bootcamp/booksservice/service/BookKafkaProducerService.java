package com.talant.bootcamp.booksservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talant.bootcamp.booksservice.dto.BookNotification;
import com.talant.bootcamp.booksservice.dto.BookRequest;
import com.talant.bootcamp.booksservice.dto.BookResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

/**
 * Service to send book notifications to Kafka
 */
@Service
public class BookKafkaProducerService {
    
    private static final Logger logger = LoggerFactory.getLogger(BookKafkaProducerService.class);
    
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    
    @Value("${kafka.topic.book-notifications:book-notifications}")
    private String bookNotificationsTopic;
    
    @Value("${kafka.topic.stock-updates:book-stock-updates}")
    private String stockUpdatesTopic;
    
    @Value("${kafka.topic.book-events:book-events}")
    private String bookEventsTopic;
    
    @Autowired
    public BookKafkaProducerService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }
    
    /**
     * Send a new book notification
     */
    public CompletableFuture<SendResult<String, String>> sendNewBookNotification(BookResponse book) {
        BookNotification notification = createNotificationFromBook(book, BookNotification.NotificationType.NEW_BOOK);
        return sendNotification(notification, bookNotificationsTopic, book.getIsbn());
    }
    
    /**
     * Send a stock update notification
     */
    public CompletableFuture<SendResult<String, String>> sendStockUpdateNotification(BookResponse book) {
        BookNotification notification = createNotificationFromBook(book, BookNotification.NotificationType.STOCK_UPDATE);
        return sendNotification(notification, stockUpdatesTopic, book.getIsbn());
    }
    
    /**
     * Send a book update notification
     */
    public CompletableFuture<SendResult<String, String>> sendBookUpdateNotification(BookResponse book) {
        BookNotification notification = createNotificationFromBook(book, BookNotification.NotificationType.BOOK_UPDATE);
        return sendNotification(notification, bookNotificationsTopic, book.getIsbn());
    }
    
    /**
     * Send a custom book notification
     */
    public CompletableFuture<SendResult<String, String>> sendCustomNotification(BookNotification notification) {
        return sendNotification(notification, bookNotificationsTopic, notification.getIsbn());
    }
    
    /**
     * Send a book event (for general events like book created, updated, deleted)
     */
    public CompletableFuture<SendResult<String, String>> sendBookEvent(String eventType, BookResponse book, String additionalData) {
        try {
            String eventMessage = createBookEventMessage(eventType, book, additionalData);
            return kafkaTemplate.send(bookEventsTopic, book.getIsbn(), eventMessage)
                    .whenComplete((result, throwable) -> {
                        if (throwable != null) {
                            logger.error("Failed to send book event: {}", eventMessage, throwable);
                        } else {
                            logger.info("Successfully sent book event: {} for ISBN: {}", eventType, book.getIsbn());
                        }
                    });
        } catch (Exception e) {
            logger.error("Error creating book event message", e);
            CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
            future.completeExceptionally(e);
            return future;
        }
    }
    
    /**
     * Send notification to specified topic
     */
    private CompletableFuture<SendResult<String, String>> sendNotification(BookNotification notification, String topic, String key) {
        try {
            String message = objectMapper.writeValueAsString(notification);
            
            return kafkaTemplate.send(topic, key, message)
                    .whenComplete((result, throwable) -> {
                        if (throwable != null) {
                            logger.error("Failed to send notification to topic {}: {}", topic, message, throwable);
                        } else {
                            logger.info("Successfully sent notification to topic {} for ISBN: {}", topic, notification.getIsbn());
                        }
                    });
        } catch (JsonProcessingException e) {
            logger.error("Error serializing notification: {}", notification, e);
            CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
            future.completeExceptionally(e);
            return future;
        }
    }
    
    /**
     * Create BookNotification from BookResponse
     */
    private BookNotification createNotificationFromBook(BookResponse book, BookNotification.NotificationType type) {
        return new BookNotification(
            book.getIsbn(),
            book.getTitle(),
            book.getAuthor(),
            book.getDescription(),
            book.getPrice(),
            book.getStock(),
            book.getCategory(),
            type,
            LocalDateTime.now()
        );
    }
    
    /**
     * Create book event message
     */
    private String createBookEventMessage(String eventType, BookResponse book, String additionalData) throws JsonProcessingException {
        BookEventMessage eventMessage = new BookEventMessage(
            eventType,
            book.getIsbn(),
            book.getId(),
            LocalDateTime.now(),
            additionalData
        );
        return objectMapper.writeValueAsString(eventMessage);
    }
    
    /**
     * Inner class for book event messages
     */
    private static class BookEventMessage {
        private String eventType;
        private String isbn;
        private Long bookId;
        private LocalDateTime timestamp;
        private String additionalData;
        
        public BookEventMessage(String eventType, String isbn, Long bookId, LocalDateTime timestamp, String additionalData) {
            this.eventType = eventType;
            this.isbn = isbn;
            this.bookId = bookId;
            this.timestamp = timestamp;
            this.additionalData = additionalData;
        }
        
        // Getters and setters
        public String getEventType() { return eventType; }
        public void setEventType(String eventType) { this.eventType = eventType; }
        
        public String getIsbn() { return isbn; }
        public void setIsbn(String isbn) { this.isbn = isbn; }
        
        public Long getBookId() { return bookId; }
        public void setBookId(Long bookId) { this.bookId = bookId; }
        
        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
        
        public String getAdditionalData() { return additionalData; }
        public void setAdditionalData(String additionalData) { this.additionalData = additionalData; }
    }
} 