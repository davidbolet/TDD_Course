package com.talant.bootcamp.booksservice.service;

import com.talant.bootcamp.booksservice.dto.BookRequest;
import com.talant.bootcamp.booksservice.exception.DuplicateIsbnException;
import com.talant.bootcamp.booksservice.model.Book;
import com.talant.bootcamp.booksservice.model.BookCategory;
import com.talant.bootcamp.booksservice.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Book Validator Tests")
class BookValidatorTest {
    
    @Mock
    private BookRepository bookRepository;
    
    @InjectMocks
    private BookValidator bookValidator;
    
    private BookRequest validBookRequest;
    private Book existingBook;
    
    @BeforeEach
    void setUp() {
        validBookRequest = new BookRequest(
            "Test Book",
            "Test Author",
            "1234567890",
            "Test Description",
            new BigDecimal("29.99"),
            10,
            BookCategory.FICTION
        );
        
        existingBook = new Book(
            "Existing Book",
            "Existing Author",
            "0987654321",
            "Existing Description",
            new BigDecimal("19.99"),
            5,
            BookCategory.TECHNOLOGY
        );
        existingBook.setId(1L);
    }
    
    @Test
    @DisplayName("Should validate book for creation successfully")
    void shouldValidateBookForCreationSuccessfully() {
        // Given
        when(bookRepository.existsByIsbn(validBookRequest.getIsbn())).thenReturn(false);
        
        // When & Then
        assertDoesNotThrow(() -> bookValidator.validateForCreation(validBookRequest));
        verify(bookRepository).existsByIsbn(validBookRequest.getIsbn());
    }
    
    @Test
    @DisplayName("Should throw DuplicateIsbnException when creating book with existing ISBN")
    void shouldThrowDuplicateIsbnExceptionWhenCreatingBookWithExistingIsbn() {
        // Given
        when(bookRepository.existsByIsbn(validBookRequest.getIsbn())).thenReturn(true);
        
        // When & Then
        assertThrows(DuplicateIsbnException.class, () -> {
            bookValidator.validateForCreation(validBookRequest);
        });
        verify(bookRepository).existsByIsbn(validBookRequest.getIsbn());
    }
    
    @Test
    @DisplayName("Should validate book for update successfully")
    void shouldValidateBookForUpdateSuccessfully() {
        // Given
        when(bookRepository.findByIsbn(validBookRequest.getIsbn())).thenReturn(Optional.empty());
        
        // When & Then
        assertDoesNotThrow(() -> bookValidator.validateForUpdate(1L, validBookRequest));
        verify(bookRepository).findByIsbn(validBookRequest.getIsbn());
    }
    
    @Test
    @DisplayName("Should validate book for update when ISBN belongs to same book")
    void shouldValidateBookForUpdateWhenIsbnBelongsToSameBook() {
        // Given
        when(bookRepository.findByIsbn(validBookRequest.getIsbn())).thenReturn(Optional.of(existingBook));
        
        // When & Then
        assertDoesNotThrow(() -> bookValidator.validateForUpdate(existingBook.getId(), validBookRequest));
        verify(bookRepository).findByIsbn(validBookRequest.getIsbn());
    }
    
    @Test
    @DisplayName("Should throw DuplicateIsbnException when updating book with existing ISBN from different book")
    void shouldThrowDuplicateIsbnExceptionWhenUpdatingBookWithExistingIsbnFromDifferentBook() {
        // Given
        when(bookRepository.findByIsbn(validBookRequest.getIsbn())).thenReturn(Optional.of(existingBook));
        
        // When & Then
        assertThrows(DuplicateIsbnException.class, () -> {
            bookValidator.validateForUpdate(2L, validBookRequest);
        });
        verify(bookRepository).findByIsbn(validBookRequest.getIsbn());
    }
    
    @Test
    @DisplayName("Should throw IllegalArgumentException when title is null")
    void shouldThrowIllegalArgumentExceptionWhenTitleIsNull() {
        // Given
        BookRequest invalidRequest = new BookRequest(
            null,
            "Test Author",
            "1234567890",
            "Test Description",
            new BigDecimal("29.99"),
            10,
            BookCategory.FICTION
        );
        
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            bookValidator.validateForCreation(invalidRequest);
        });
    }
    
    @Test
    @DisplayName("Should throw IllegalArgumentException when title is empty")
    void shouldThrowIllegalArgumentExceptionWhenTitleIsEmpty() {
        // Given
        BookRequest invalidRequest = new BookRequest(
            "",
            "Test Author",
            "1234567890",
            "Test Description",
            new BigDecimal("29.99"),
            10,
            BookCategory.FICTION
        );
        
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            bookValidator.validateForCreation(invalidRequest);
        });
    }
    
    @Test
    @DisplayName("Should throw IllegalArgumentException when author is null")
    void shouldThrowIllegalArgumentExceptionWhenAuthorIsNull() {
        // Given
        BookRequest invalidRequest = new BookRequest(
            "Test Book",
            null,
            "1234567890",
            "Test Description",
            new BigDecimal("29.99"),
            10,
            BookCategory.FICTION
        );
        
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            bookValidator.validateForCreation(invalidRequest);
        });
    }
    
    @Test
    @DisplayName("Should throw IllegalArgumentException when ISBN is null")
    void shouldThrowIllegalArgumentExceptionWhenIsbnIsNull() {
        // Given
        BookRequest invalidRequest = new BookRequest(
            "Test Book",
            "Test Author",
            null,
            "Test Description",
            new BigDecimal("29.99"),
            10,
            BookCategory.FICTION
        );
        
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            bookValidator.validateForCreation(invalidRequest);
        });
    }
    
    @Test
    @DisplayName("Should throw IllegalArgumentException when price is null")
    void shouldThrowIllegalArgumentExceptionWhenPriceIsNull() {
        // Given
        BookRequest invalidRequest = new BookRequest(
            "Test Book",
            "Test Author",
            "1234567890",
            "Test Description",
            null,
            10,
            BookCategory.FICTION
        );
        
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            bookValidator.validateForCreation(invalidRequest);
        });
    }
    
    @Test
    @DisplayName("Should throw IllegalArgumentException when price is negative")
    void shouldThrowIllegalArgumentExceptionWhenPriceIsNegative() {
        // Given
        BookRequest invalidRequest = new BookRequest(
            "Test Book",
            "Test Author",
            "1234567890",
            "Test Description",
            new BigDecimal("-10.00"),
            10,
            BookCategory.FICTION
        );
        
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            bookValidator.validateForCreation(invalidRequest);
        });
    }
    
    @Test
    @DisplayName("Should throw IllegalArgumentException when stock is null")
    void shouldThrowIllegalArgumentExceptionWhenStockIsNull() {
        // Given
        BookRequest invalidRequest = new BookRequest(
            "Test Book",
            "Test Author",
            "1234567890",
            "Test Description",
            new BigDecimal("29.99"),
            null,
            BookCategory.FICTION
        );
        
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            bookValidator.validateForCreation(invalidRequest);
        });
    }
    
    @Test
    @DisplayName("Should throw IllegalArgumentException when stock is negative")
    void shouldThrowIllegalArgumentExceptionWhenStockIsNegative() {
        // Given
        BookRequest invalidRequest = new BookRequest(
            "Test Book",
            "Test Author",
            "1234567890",
            "Test Description",
            new BigDecimal("29.99"),
            -5,
            BookCategory.FICTION
        );
        
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            bookValidator.validateForCreation(invalidRequest);
        });
    }
    
    @Test
    @DisplayName("Should throw IllegalArgumentException when category is null")
    void shouldThrowIllegalArgumentExceptionWhenCategoryIsNull() {
        // Given
        BookRequest invalidRequest = new BookRequest(
            "Test Book",
            "Test Author",
            "1234567890",
            "Test Description",
            new BigDecimal("29.99"),
            10,
            null
        );
        
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            bookValidator.validateForCreation(invalidRequest);
        });
    }
} 