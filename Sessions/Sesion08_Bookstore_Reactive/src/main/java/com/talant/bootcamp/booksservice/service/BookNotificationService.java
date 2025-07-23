package com.talant.bootcamp.booksservice.service;

import com.talant.bootcamp.booksservice.dto.BookNotification;
import com.talant.bootcamp.booksservice.dto.BookRequest;
import com.talant.bootcamp.booksservice.dto.BookResponse;
import com.talant.bootcamp.booksservice.exception.BookNotFoundException;
import com.talant.bootcamp.booksservice.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Service to handle book notifications from Kafka
 */
@Service
public class BookNotificationService {
    
    private static final Logger logger = LoggerFactory.getLogger(BookNotificationService.class);
    
    private final BookService bookService;
    
    @Autowired
    public BookNotificationService(BookService bookService) {
        this.bookService = bookService;
    }
    
    /**
     * Process a book notification from Kafka
     * Creates a new book if it doesn't exist, or updates stock if it does
     */
    @Transactional
    public void processBookNotification(BookNotification notification) {
        logger.info("Processing book notification: {}", notification);
        
        try {
            switch (notification.getNotificationType()) {
                case NEW_BOOK:
                    handleNewBook(notification);
                    break;
                case STOCK_UPDATE:
                    handleStockUpdate(notification);
                    break;
                case BOOK_UPDATE:
                    handleBookUpdate(notification);
                    break;
                default:
                    logger.warn("Unknown notification type: {}", notification.getNotificationType());
            }
        } catch (Exception e) {
            logger.error("Error processing book notification: {}", notification, e);
            throw e;
        }
    }
    
    /**
     * Handle new book notification
     */
    private void handleNewBook(BookNotification notification) {
        logger.info("Handling new book notification for ISBN: {}", notification.getIsbn());
        
        // Check if book already exists
        if (bookService.existsByIsbn(notification.getIsbn())) {
            logger.info("Book with ISBN {} already exists, updating stock", notification.getIsbn());
            handleStockUpdate(notification);
            return;
        }
        
        // Create new book
        BookRequest bookRequest = createBookRequestFromNotification(notification);
        BookResponse createdBook = bookService.createBook(bookRequest);
        
        logger.info("Successfully created new book: {}", createdBook);
    }
    
    /**
     * Handle stock update notification
     */
    private void handleStockUpdate(BookNotification notification) {
        logger.info("Handling stock update notification for ISBN: {}", notification.getIsbn());
        
        try {
            // Get existing book by ISBN
            BookResponse existingBook = bookService.getBookByIsbn(notification.getIsbn());
            
            // Update stock
            BookResponse updatedBook = bookService.updateStock(existingBook.getId(), notification.getStock());
            
            logger.info("Successfully updated stock for book: {}", updatedBook);
        } catch (BookNotFoundException e) {
            logger.warn("Book with ISBN {} not found, creating new book", notification.getIsbn());
            handleNewBook(notification);
        }
    }
    
    /**
     * Handle book update notification
     */
    private void handleBookUpdate(BookNotification notification) {
        logger.info("Handling book update notification for ISBN: {}", notification.getIsbn());
        
        try {
            // Get existing book by ISBN
            BookResponse existingBook = bookService.getBookByIsbn(notification.getIsbn());
            
            // Create book request for update
            BookRequest bookRequest = createBookRequestFromNotification(notification);
            
            // Update book
            BookResponse updatedBook = bookService.updateBook(existingBook.getId(), bookRequest);
            
            logger.info("Successfully updated book: {}", updatedBook);
        } catch (BookNotFoundException e) {
            logger.warn("Book with ISBN {} not found, creating new book", notification.getIsbn());
            handleNewBook(notification);
        }
    }
    
    /**
     * Create BookRequest from BookNotification
     */
    private BookRequest createBookRequestFromNotification(BookNotification notification) {
        return new BookRequest(
            notification.getTitle(),
            notification.getAuthor(),
            notification.getIsbn(),
            notification.getDescription(),
            notification.getPrice(),
            notification.getStock(),
            notification.getCategory()
        );
    }
    
    /**
     * Validate book notification
     */
    public boolean isValidNotification(BookNotification notification) {
        if (notification == null) {
            logger.warn("Notification is null");
            return false;
        }
        
        if (notification.getIsbn() == null || notification.getIsbn().trim().isEmpty()) {
            logger.warn("Notification has null or empty ISBN");
            return false;
        }
        
        if (notification.getTitle() == null || notification.getTitle().trim().isEmpty()) {
            logger.warn("Notification has null or empty title");
            return false;
        }
        
        if (notification.getAuthor() == null || notification.getAuthor().trim().isEmpty()) {
            logger.warn("Notification has null or empty author");
            return false;
        }
        
        if (notification.getPrice() == null || notification.getPrice().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            logger.warn("Notification has invalid price: {}", notification.getPrice());
            return false;
        }
        
        if (notification.getStock() == null || notification.getStock() < 0) {
            logger.warn("Notification has invalid stock: {}", notification.getStock());
            return false;
        }
        
        if (notification.getCategory() == null) {
            logger.warn("Notification has null category");
            return false;
        }
        
        if (notification.getNotificationType() == null) {
            logger.warn("Notification has null notification type");
            return false;
        }
        
        return true;
    }
} 