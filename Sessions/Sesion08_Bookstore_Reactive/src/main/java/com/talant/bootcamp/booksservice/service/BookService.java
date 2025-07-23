package com.talant.bootcamp.booksservice.service;

import com.talant.bootcamp.booksservice.dto.BookRequest;
import com.talant.bootcamp.booksservice.dto.BookResponse;
import com.talant.bootcamp.booksservice.exception.BookNotFoundException;
import com.talant.bootcamp.booksservice.model.Book;
import com.talant.bootcamp.booksservice.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for the Book model - Core CRUD operations
 */
@Service
@Transactional
public class BookService {
    
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    
    private final BookRepository bookRepository;
    private final BookValidator bookValidator;
    private final BookKafkaProducerService kafkaProducerService;
    
    @Autowired
    public BookService(BookRepository bookRepository, BookValidator bookValidator, BookKafkaProducerService kafkaProducerService) {
        this.bookRepository = bookRepository;
        this.bookValidator = bookValidator;
        this.kafkaProducerService = kafkaProducerService;
    }
    
    /**
     * Create a new book
     */
    public BookResponse createBook(BookRequest bookRequest) {
        bookValidator.validateForCreation(bookRequest);
        
        Book book = new Book(
            bookRequest.getTitle(),
            bookRequest.getAuthor(),
            bookRequest.getIsbn(),
            bookRequest.getDescription(),
            bookRequest.getPrice(),
            bookRequest.getStock(),
            bookRequest.getCategory()
        );
        
        Book savedBook = bookRepository.save(book);
        BookResponse bookResponse = new BookResponse(savedBook);
        
        // Send Kafka notification asynchronously
        try {
            kafkaProducerService.sendNewBookNotification(bookResponse)
                .whenComplete((result, throwable) -> {
                    if (throwable != null) {
                        logger.error("Failed to send new book notification for ISBN: {}", bookRequest.getIsbn(), throwable);
                    } else {
                        logger.info("Successfully sent new book notification for ISBN: {}", bookRequest.getIsbn());
                    }
                });
        } catch (Exception e) {
            logger.error("Error sending new book notification for ISBN: {}", bookRequest.getIsbn(), e);
        }
        
        return bookResponse;
    }
    
    /**
     * Get all books
     */
    @Transactional(readOnly = true)
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Get a book by ID
     */
    @Transactional(readOnly = true)
    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return new BookResponse(book);
    }
    
    /**
     * Get a book by ISBN
     */
    @Transactional(readOnly = true)
    public BookResponse getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("ISBN", isbn));
        return new BookResponse(book);
    }
    
    /**
     * Update a book
     */
    public BookResponse updateBook(Long id, BookRequest bookRequest) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        
        bookValidator.validateForUpdate(id, bookRequest);
        
        // Update fields
        existingBook.setTitle(bookRequest.getTitle());
        existingBook.setAuthor(bookRequest.getAuthor());
        existingBook.setIsbn(bookRequest.getIsbn());
        existingBook.setDescription(bookRequest.getDescription());
        existingBook.setPrice(bookRequest.getPrice());
        existingBook.setStock(bookRequest.getStock());
        existingBook.setCategory(bookRequest.getCategory());
        
        Book updatedBook = bookRepository.save(existingBook);
        BookResponse bookResponse = new BookResponse(updatedBook);
        
        // Send Kafka notification asynchronously
        try {
            kafkaProducerService.sendBookUpdateNotification(bookResponse)
                .whenComplete((result, throwable) -> {
                    if (throwable != null) {
                        logger.error("Failed to send book update notification for ID: {}", id, throwable);
                    } else {
                        logger.info("Successfully sent book update notification for ID: {}", id);
                    }
                });
        } catch (Exception e) {
            logger.error("Error sending book update notification for ID: {}", id, e);
        }
        
        return bookResponse;
    }
    
    /**
     * Delete a book
     */
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
    }
    
    /**
     * Update stock of a book
     */
    public BookResponse updateStock(Long id, Integer newStock) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        
        book.setStock(newStock);
        Book updatedBook = bookRepository.save(book);
        BookResponse bookResponse = new BookResponse(updatedBook);
        
        // Send Kafka notification asynchronously
        try {
            kafkaProducerService.sendStockUpdateNotification(bookResponse)
                .whenComplete((result, throwable) -> {
                    if (throwable != null) {
                        logger.error("Failed to send stock update notification for ID: {}", id, throwable);
                    } else {
                        logger.info("Successfully sent stock update notification for ID: {}", id);
                    }
                });
        } catch (Exception e) {
            logger.error("Error sending stock update notification for ID: {}", id, e);
        }
        
        return bookResponse;
    }
    
    /**
     * Check if a book exists by ISBN
     */
    @Transactional(readOnly = true)
    public boolean existsByIsbn(String isbn) {
        return bookRepository.existsByIsbn(isbn);
    }
} 