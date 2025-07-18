package com.talant.bootcamp.booksservice.exception;

/**
 * Exception thrown when a book with the same ISBN already exists
 */
public class DuplicateIsbnException extends RuntimeException {
    
    public DuplicateIsbnException(String isbn) {
        super("A book with ISBN " + isbn + " already exists");
    }
} 