package com.talant.bootcamp.booksservice.service;

import com.talant.bootcamp.booksservice.dto.BookNotification;
import com.talant.bootcamp.booksservice.dto.BookRequest;
import com.talant.bootcamp.booksservice.dto.BookResponse;
import com.talant.bootcamp.booksservice.exception.BookNotFoundException;
import com.talant.bootcamp.booksservice.model.Book;
import com.talant.bootcamp.booksservice.model.BookCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Book Notification Service Tests")
class BookNotificationServiceTest {
    
    @Mock
    private BookService bookService;
    
    @InjectMocks
    private BookNotificationService bookNotificationService;
    
    private BookNotification newBookNotification;
    private BookNotification stockUpdateNotification;
    private BookNotification bookUpdateNotification;
    private BookResponse existingBookResponse;
    private BookRequest bookRequest;
    
    @BeforeEach
    void setUp() {
        newBookNotification = new BookNotification(
            "9780123456789",
            "Test Book",
            "Test Author",
            "Test Description",
            new BigDecimal("29.99"),
            10,
            BookCategory.FICTION,
            BookNotification.NotificationType.NEW_BOOK,
            LocalDateTime.now()
        );
        
        stockUpdateNotification = new BookNotification(
            "9780123456789",
            "Test Book",
            "Test Author",
            "Test Description",
            new BigDecimal("29.99"),
            15,
            BookCategory.FICTION,
            BookNotification.NotificationType.STOCK_UPDATE,
            LocalDateTime.now()
        );
        
        bookUpdateNotification = new BookNotification(
            "9780123456789",
            "Updated Test Book",
            "Updated Test Author",
            "Updated Test Description",
            new BigDecimal("39.99"),
            20,
            BookCategory.NON_FICTION,
            BookNotification.NotificationType.BOOK_UPDATE,
            LocalDateTime.now()
        );
        
        Book book = new Book(
            "Test Book",
            "Test Author",
            "9780123456789",
            "Test Description",
            new BigDecimal("29.99"),
            10,
            BookCategory.FICTION
        );
        book.setId(1L);
        
        existingBookResponse = new BookResponse(book);
        
        bookRequest = new BookRequest(
            "Test Book",
            "Test Author",
            "9780123456789",
            "Test Description",
            new BigDecimal("29.99"),
            10,
            BookCategory.FICTION
        );
    }
    
    @Test
    @DisplayName("Should process new book notification successfully")
    void processBookNotification_NewBook_Success() {
        // Given
        when(bookService.existsByIsbn("9780123456789")).thenReturn(false);
        when(bookService.createBook(any(BookRequest.class))).thenReturn(existingBookResponse);
        
        // When
        bookNotificationService.processBookNotification(newBookNotification);
        
        // Then
        verify(bookService).existsByIsbn("9780123456789");
        verify(bookService).createBook(any(BookRequest.class));
        verify(bookService, never()).getBookByIsbn(anyString());
        verify(bookService, never()).updateStock(anyLong(), anyInt());
        verify(bookService, never()).updateBook(anyLong(), any(BookRequest.class));
    }
    
    @Test
    @DisplayName("Should handle new book notification when book already exists")
    void processBookNotification_NewBook_BookAlreadyExists() {
        // Given
        when(bookService.existsByIsbn("9780123456789")).thenReturn(true);
        when(bookService.getBookByIsbn("9780123456789")).thenReturn(existingBookResponse);
        when(bookService.updateStock(1L, 10)).thenReturn(existingBookResponse);
        
        // When
        bookNotificationService.processBookNotification(newBookNotification);
        
        // Then
        verify(bookService).existsByIsbn("9780123456789");
        verify(bookService).getBookByIsbn("9780123456789");
        verify(bookService).updateStock(1L, 10);
        verify(bookService, never()).createBook(any(BookRequest.class));
    }
    
    @Test
    @DisplayName("Should process stock update notification successfully")
    void processBookNotification_StockUpdate_Success() {
        // Given
        when(bookService.getBookByIsbn("9780123456789")).thenReturn(existingBookResponse);
        when(bookService.updateStock(1L, 15)).thenReturn(existingBookResponse);
        
        // When
        bookNotificationService.processBookNotification(stockUpdateNotification);
        
        // Then
        verify(bookService).getBookByIsbn("9780123456789");
        verify(bookService).updateStock(1L, 15);
        verify(bookService, never()).createBook(any(BookRequest.class));
    }
    
    @Test
    @DisplayName("Should handle stock update notification when book not found")
    void processBookNotification_StockUpdate_BookNotFound() {
        // Given
        when(bookService.getBookByIsbn("9780123456789")).thenThrow(new BookNotFoundException("ISBN", "9780123456789"));
        when(bookService.existsByIsbn("9780123456789")).thenReturn(false);
        when(bookService.createBook(any(BookRequest.class))).thenReturn(existingBookResponse);
        
        // When
        bookNotificationService.processBookNotification(stockUpdateNotification);
        
        // Then
        verify(bookService).getBookByIsbn("9780123456789");
        verify(bookService).existsByIsbn("9780123456789");
        verify(bookService).createBook(any(BookRequest.class));
        verify(bookService, never()).updateStock(anyLong(), anyInt());
    }
    
    @Test
    @DisplayName("Should process book update notification successfully")
    void processBookNotification_BookUpdate_Success() {
        // Given
        when(bookService.getBookByIsbn("9780123456789")).thenReturn(existingBookResponse);
        when(bookService.updateBook(eq(1L), any(BookRequest.class))).thenReturn(existingBookResponse);
        
        // When
        bookNotificationService.processBookNotification(bookUpdateNotification);
        
        // Then
        verify(bookService).getBookByIsbn("9780123456789");
        verify(bookService).updateBook(eq(1L), any(BookRequest.class));
        verify(bookService, never()).createBook(any(BookRequest.class));
    }
    
    @Test
    @DisplayName("Should handle book update notification when book not found")
    void processBookNotification_BookUpdate_BookNotFound() {
        // Given
        when(bookService.getBookByIsbn("9780123456789")).thenThrow(new BookNotFoundException("ISBN", "9780123456789"));
        when(bookService.existsByIsbn("9780123456789")).thenReturn(false);
        when(bookService.createBook(any(BookRequest.class))).thenReturn(existingBookResponse);
        
        // When
        bookNotificationService.processBookNotification(bookUpdateNotification);
        
        // Then
        verify(bookService).getBookByIsbn("9780123456789");
        verify(bookService).existsByIsbn("9780123456789");
        verify(bookService).createBook(any(BookRequest.class));
        verify(bookService, never()).updateBook(anyLong(), any(BookRequest.class));
    }
    
    @Test
    @DisplayName("Should validate notification successfully")
    void isValidNotification_ValidNotification_ReturnsTrue() {
        // When
        boolean isValid = bookNotificationService.isValidNotification(newBookNotification);
        
        // Then
        assertTrue(isValid);
    }
    
    @Test
    @DisplayName("Should reject null notification")
    void isValidNotification_NullNotification_ReturnsFalse() {
        // When
        boolean isValid = bookNotificationService.isValidNotification(null);
        
        // Then
        assertFalse(isValid);
    }
    
    @Test
    @DisplayName("Should reject notification with null ISBN")
    void isValidNotification_NullIsbn_ReturnsFalse() {
        // Given
        newBookNotification.setIsbn(null);
        
        // When
        boolean isValid = bookNotificationService.isValidNotification(newBookNotification);
        
        // Then
        assertFalse(isValid);
    }
    
    @Test
    @DisplayName("Should reject notification with empty ISBN")
    void isValidNotification_EmptyIsbn_ReturnsFalse() {
        // Given
        newBookNotification.setIsbn("");
        
        // When
        boolean isValid = bookNotificationService.isValidNotification(newBookNotification);
        
        // Then
        assertFalse(isValid);
    }
    
    @Test
    @DisplayName("Should reject notification with null title")
    void isValidNotification_NullTitle_ReturnsFalse() {
        // Given
        newBookNotification.setTitle(null);
        
        // When
        boolean isValid = bookNotificationService.isValidNotification(newBookNotification);
        
        // Then
        assertFalse(isValid);
    }
    
    @Test
    @DisplayName("Should reject notification with null author")
    void isValidNotification_NullAuthor_ReturnsFalse() {
        // Given
        newBookNotification.setAuthor(null);
        
        // When
        boolean isValid = bookNotificationService.isValidNotification(newBookNotification);
        
        // Then
        assertFalse(isValid);
    }
    
    @Test
    @DisplayName("Should reject notification with null price")
    void isValidNotification_NullPrice_ReturnsFalse() {
        // Given
        newBookNotification.setPrice(null);
        
        // When
        boolean isValid = bookNotificationService.isValidNotification(newBookNotification);
        
        // Then
        assertFalse(isValid);
    }
    
    @Test
    @DisplayName("Should reject notification with zero price")
    void isValidNotification_ZeroPrice_ReturnsFalse() {
        // Given
        newBookNotification.setPrice(BigDecimal.ZERO);
        
        // When
        boolean isValid = bookNotificationService.isValidNotification(newBookNotification);
        
        // Then
        assertFalse(isValid);
    }
    
    @Test
    @DisplayName("Should reject notification with negative price")
    void isValidNotification_NegativePrice_ReturnsFalse() {
        // Given
        newBookNotification.setPrice(new BigDecimal("-10.00"));
        
        // When
        boolean isValid = bookNotificationService.isValidNotification(newBookNotification);
        
        // Then
        assertFalse(isValid);
    }
    
    @Test
    @DisplayName("Should reject notification with null stock")
    void isValidNotification_NullStock_ReturnsFalse() {
        // Given
        newBookNotification.setStock(null);
        
        // When
        boolean isValid = bookNotificationService.isValidNotification(newBookNotification);
        
        // Then
        assertFalse(isValid);
    }
    
    @Test
    @DisplayName("Should reject notification with negative stock")
    void isValidNotification_NegativeStock_ReturnsFalse() {
        // Given
        newBookNotification.setStock(-5);
        
        // When
        boolean isValid = bookNotificationService.isValidNotification(newBookNotification);
        
        // Then
        assertFalse(isValid);
    }
    
    @Test
    @DisplayName("Should reject notification with null category")
    void isValidNotification_NullCategory_ReturnsFalse() {
        // Given
        newBookNotification.setCategory(null);
        
        // When
        boolean isValid = bookNotificationService.isValidNotification(newBookNotification);
        
        // Then
        assertFalse(isValid);
    }
    
    @Test
    @DisplayName("Should reject notification with null notification type")
    void isValidNotification_NullNotificationType_ReturnsFalse() {
        // Given
        newBookNotification.setNotificationType(null);
        
        // When
        boolean isValid = bookNotificationService.isValidNotification(newBookNotification);
        
        // Then
        assertFalse(isValid);
    }
} 