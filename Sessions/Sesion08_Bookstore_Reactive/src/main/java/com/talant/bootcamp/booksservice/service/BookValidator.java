package com.talant.bootcamp.booksservice.service;

import com.talant.bootcamp.booksservice.dto.BookRequest;
import com.talant.bootcamp.booksservice.exception.DuplicateIsbnException;
import com.talant.bootcamp.booksservice.repository.BookRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Validator for Book operations
 */
@Component
public class BookValidator {
    
    private final BookRepository bookRepository;
    
    public BookValidator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    /**
     * Validate book for creation
     */
    public void validateForCreation(BookRequest bookRequest) {
        validateBasicFields(bookRequest);
        
        // Check if a book with the same ISBN already exists
        if (bookRepository.existsByIsbn(bookRequest.getIsbn())) {
            throw new DuplicateIsbnException(bookRequest.getIsbn());
        }
    }
    
    /**
     * Validate book for update
     */
    public void validateForUpdate(Long id, BookRequest bookRequest) {
        validateBasicFields(bookRequest);
        
        // Check if the new ISBN already exists in another book
        Optional<com.talant.bootcamp.booksservice.model.Book> bookWithSameIsbn = 
            bookRepository.findByIsbn(bookRequest.getIsbn());
        if (bookWithSameIsbn.isPresent() && !bookWithSameIsbn.get().getId().equals(id)) {
            throw new DuplicateIsbnException(bookRequest.getIsbn());
        }
    }
    
    /**
     * Validate basic book fields
     */
    private void validateBasicFields(BookRequest bookRequest) {
        if (bookRequest.getTitle() == null || bookRequest.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Title must not be empty");
        }
        if (bookRequest.getAuthor() == null || bookRequest.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Author must not be empty");
        }
        if (bookRequest.getIsbn() == null || bookRequest.getIsbn().trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN must not be empty");
        }
        if (bookRequest.getPrice() == null || bookRequest.getPrice().compareTo(java.math.BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price must be non-negative");
        }
        if (bookRequest.getStock() == null || bookRequest.getStock() < 0) {
            throw new IllegalArgumentException("Stock must be non-negative");
        }
        if (bookRequest.getCategory() == null) {
            throw new IllegalArgumentException("Category must not be null");
        }
    }
} 