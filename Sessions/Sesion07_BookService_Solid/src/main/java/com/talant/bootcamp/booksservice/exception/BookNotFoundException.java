package com.talant.bootcamp.booksservice.exception;

/**
 * Exception thrown when a book is not found
 */
public class BookNotFoundException extends RuntimeException {
    
    public BookNotFoundException(String message) {
        super(message);
    }
    
    public BookNotFoundException(Long id) {
        super("Book not found with ID: " + id);
    }
    
    public BookNotFoundException(String field, String value) {
        super("Book not found with " + field + ": " + value);
    }
} 