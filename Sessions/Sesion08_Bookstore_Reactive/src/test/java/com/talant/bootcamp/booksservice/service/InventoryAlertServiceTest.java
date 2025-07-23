package com.talant.bootcamp.booksservice.service;

import com.talant.bootcamp.booksservice.dto.BookResponse;
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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Inventory Alert Service Tests")
class InventoryAlertServiceTest {
    
    @Mock
    private BookRepository bookRepository;
    
    private InventoryAlertService inventoryAlertService;
    
    private Book bookWithLowStock;
    private Book bookWithNormalStock;
    private Book bookWithZeroStock;
    private List<Book> allBooks;
    
    @BeforeEach
    void setUp() {
        inventoryAlertService = new InventoryAlertService(bookRepository, 5);
        
        bookWithLowStock = new Book(
            "Low Stock Book",
            "Author 1",
            "1234567890",
            "Description 1",
            new BigDecimal("29.99"),
            3, // Below threshold of 5
            BookCategory.FICTION
        );
        bookWithLowStock.setId(1L);
        
        bookWithNormalStock = new Book(
            "Normal Stock Book",
            "Author 2",
            "0987654321",
            "Description 2",
            new BigDecimal("39.99"),
            10, // Above threshold
            BookCategory.TECHNOLOGY
        );
        bookWithNormalStock.setId(2L);
        
        bookWithZeroStock = new Book(
            "Zero Stock Book",
            "Author 3",
            "1111111111",
            "Description 3",
            new BigDecimal("19.99"),
            0, // Zero stock
            BookCategory.FICTION
        );
        bookWithZeroStock.setId(3L);
        
        allBooks = Arrays.asList(bookWithLowStock, bookWithNormalStock, bookWithZeroStock);
    }
    
    @Test
    @DisplayName("Should get books with low stock")
    void shouldGetBooksWithLowStock() {
        // Given
        when(bookRepository.findAll()).thenReturn(allBooks);
        
        // When
        List<Book> result = inventoryAlertService.getBooksWithLowStock();
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.size()); // bookWithLowStock (3) and bookWithZeroStock (0)
        assertTrue(result.contains(bookWithLowStock));
        assertTrue(result.contains(bookWithZeroStock));
        assertFalse(result.contains(bookWithNormalStock));
        verify(bookRepository).findAll();
    }
    
    @Test
    @DisplayName("Should get books with low stock as BookResponse")
    void shouldGetBooksWithLowStockAsResponse() {
        // Given
        when(bookRepository.findAll()).thenReturn(allBooks);
        
        // When
        List<BookResponse> result = inventoryAlertService.getBooksWithLowStockAsResponse();
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(response -> response.getId().equals(bookWithLowStock.getId())));
        assertTrue(result.stream().anyMatch(response -> response.getId().equals(bookWithZeroStock.getId())));
        assertFalse(result.stream().anyMatch(response -> response.getId().equals(bookWithNormalStock.getId())));
        verify(bookRepository).findAll();
    }
    
    @Test
    @DisplayName("Should check if book has low stock")
    void shouldCheckIfBookHasLowStock() {
        // Given & When & Then
        assertTrue(inventoryAlertService.hasLowStock(bookWithLowStock)); // stock = 3
        assertTrue(inventoryAlertService.hasLowStock(bookWithZeroStock)); // stock = 0
        assertFalse(inventoryAlertService.hasLowStock(bookWithNormalStock)); // stock = 10
    }
    
    @Test
    @DisplayName("Should get current low stock threshold")
    void shouldGetCurrentLowStockThreshold() {
        // When
        int threshold = inventoryAlertService.getLowStockThreshold();
        
        // Then
        assertEquals(5, threshold); // Default threshold
    }
    
    @Test
    @DisplayName("Should return empty list when no books have low stock")
    void shouldReturnEmptyListWhenNoBooksHaveLowStock() {
        // Given
        Book highStockBook1 = new Book("High Stock 1", "Author", "111", "Desc", new BigDecimal("10"), 10, BookCategory.FICTION);
        Book highStockBook2 = new Book("High Stock 2", "Author", "222", "Desc", new BigDecimal("20"), 15, BookCategory.TECHNOLOGY);
        List<Book> highStockBooks = Arrays.asList(highStockBook1, highStockBook2);
        
        when(bookRepository.findAll()).thenReturn(highStockBooks);
        
        // When
        List<Book> result = inventoryAlertService.getBooksWithLowStock();
        
        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bookRepository).findAll();
    }
    
    @Test
    @DisplayName("Should return empty list when no books exist")
    void shouldReturnEmptyListWhenNoBooksExist() {
        // Given
        when(bookRepository.findAll()).thenReturn(Arrays.asList());
        
        // When
        List<Book> result = inventoryAlertService.getBooksWithLowStock();
        
        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bookRepository).findAll();
    }
    
    @Test
    @DisplayName("Should handle books with stock exactly at threshold")
    void shouldHandleBooksWithStockExactlyAtThreshold() {
        // Given
        Book bookAtThreshold = new Book(
            "At Threshold Book",
            "Author",
            "3333333333",
            "Description",
            new BigDecimal("25.00"),
            5, // Exactly at threshold
            BookCategory.FICTION
        );
        
        when(bookRepository.findAll()).thenReturn(Arrays.asList(bookAtThreshold));
        
        // When
        List<Book> result = inventoryAlertService.getBooksWithLowStock();
        
        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty()); // Should not be considered low stock (stock >= threshold)
        verify(bookRepository).findAll();
    }
} 