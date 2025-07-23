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
@DisplayName("Book Search Service Tests")
class BookSearchServiceTest {
    
    @Mock
    private BookRepository bookRepository;
    
    @InjectMocks
    private BookSearchService bookSearchService;
    
    private Book book1;
    private Book book2;
    private List<Book> books;
    
    @BeforeEach
    void setUp() {
        book1 = new Book(
            "Test Book 1",
            "Test Author 1",
            "1234567890",
            "Test Description 1",
            new BigDecimal("29.99"),
            10,
            BookCategory.FICTION
        );
        book1.setId(1L);
        
        book2 = new Book(
            "Test Book 2",
            "Test Author 2",
            "0987654321",
            "Test Description 2",
            new BigDecimal("39.99"),
            5,
            BookCategory.TECHNOLOGY
        );
        book2.setId(2L);
        
        books = Arrays.asList(book1, book2);
    }
    
    @Test
    @DisplayName("Should get books by author")
    void shouldGetBooksByAuthor() {
        // Given
        String author = "Test Author";
        when(bookRepository.findByAuthorContainingIgnoreCase(author)).thenReturn(books);
        
        // When
        List<BookResponse> result = bookSearchService.getBooksByAuthor(author);
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(book1.getAuthor(), result.get(0).getAuthor());
        assertEquals(book2.getAuthor(), result.get(1).getAuthor());
        verify(bookRepository).findByAuthorContainingIgnoreCase(author);
    }
    
    @Test
    @DisplayName("Should get books by title")
    void shouldGetBooksByTitle() {
        // Given
        String title = "Test Book";
        when(bookRepository.findByTitleContainingIgnoreCase(title)).thenReturn(books);
        
        // When
        List<BookResponse> result = bookSearchService.getBooksByTitle(title);
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(book1.getTitle(), result.get(0).getTitle());
        assertEquals(book2.getTitle(), result.get(1).getTitle());
        verify(bookRepository).findByTitleContainingIgnoreCase(title);
    }
    
    @Test
    @DisplayName("Should get books by category")
    void shouldGetBooksByCategory() {
        // Given
        BookCategory category = BookCategory.FICTION;
        when(bookRepository.findByCategory(category)).thenReturn(Arrays.asList(book1));
        
        // When
        List<BookResponse> result = bookSearchService.getBooksByCategory(category);
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(book1.getCategory(), result.get(0).getCategory());
        verify(bookRepository).findByCategory(category);
    }
    
    @Test
    @DisplayName("Should get books with stock")
    void shouldGetBooksWithStock() {
        // Given
        when(bookRepository.findByStockGreaterThan(0)).thenReturn(books);
        
        // When
        List<BookResponse> result = bookSearchService.getBooksWithStock();
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bookRepository).findByStockGreaterThan(0);
    }
    
    @Test
    @DisplayName("Should get books out of stock")
    void shouldGetBooksOutOfStock() {
        // Given
        Book outOfStockBook = new Book(
            "Out of Stock Book",
            "Author",
            "1111111111",
            "Description",
            new BigDecimal("19.99"),
            0,
            BookCategory.FICTION
        );
        when(bookRepository.findByStockEquals(0)).thenReturn(Arrays.asList(outOfStockBook));
        
        // When
        List<BookResponse> result = bookSearchService.getBooksOutOfStock();
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(0, result.get(0).getStock());
        verify(bookRepository).findByStockEquals(0);
    }
    
    @Test
    @DisplayName("Should get books by price range")
    void shouldGetBooksByPriceRange() {
        // Given
        BigDecimal minPrice = new BigDecimal("20.00");
        BigDecimal maxPrice = new BigDecimal("40.00");
        when(bookRepository.findByPriceBetween(minPrice, maxPrice)).thenReturn(books);
        
        // When
        List<BookResponse> result = bookSearchService.getBooksByPriceRange(minPrice, maxPrice);
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bookRepository).findByPriceBetween(minPrice, maxPrice);
    }
    
    @Test
    @DisplayName("Should get books by maximum price")
    void shouldGetBooksByMaxPrice() {
        // Given
        BigDecimal maxPrice = new BigDecimal("35.00");
        when(bookRepository.findByPriceLessThanEqual(maxPrice)).thenReturn(Arrays.asList(book1));
        
        // When
        List<BookResponse> result = bookSearchService.getBooksByMaxPrice(maxPrice);
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getPrice().compareTo(maxPrice) <= 0);
        verify(bookRepository).findByPriceLessThanEqual(maxPrice);
    }
    
    @Test
    @DisplayName("Should get books by minimum price")
    void shouldGetBooksByMinPrice() {
        // Given
        BigDecimal minPrice = new BigDecimal("30.00");
        when(bookRepository.findByPriceGreaterThanEqual(minPrice)).thenReturn(Arrays.asList(book2));
        
        // When
        List<BookResponse> result = bookSearchService.getBooksByMinPrice(minPrice);
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getPrice().compareTo(minPrice) >= 0);
        verify(bookRepository).findByPriceGreaterThanEqual(minPrice);
    }
    
    @Test
    @DisplayName("Should get books with low stock")
    void shouldGetBooksWithLowStock() {
        // Given
        when(bookRepository.findBooksWithLowStock()).thenReturn(Arrays.asList(book2));
        
        // When
        List<BookResponse> result = bookSearchService.getBooksWithLowStock();
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(book2.getId(), result.get(0).getId());
        verify(bookRepository).findBooksWithLowStock();
    }
    
    @Test
    @DisplayName("Should search books by text")
    void shouldSearchBooksByText() {
        // Given
        String searchTerm = "test";
        when(bookRepository.searchByTitleOrAuthor(searchTerm)).thenReturn(books);
        
        // When
        List<BookResponse> result = bookSearchService.searchBooks(searchTerm);
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bookRepository).searchByTitleOrAuthor(searchTerm);
    }
    
    @Test
    @DisplayName("Should get books ordered by price ascending")
    void shouldGetBooksOrderedByPriceAsc() {
        // Given
        when(bookRepository.findAllByOrderByPriceAsc()).thenReturn(books);
        
        // When
        List<BookResponse> result = bookSearchService.getBooksOrderedByPriceAsc();
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bookRepository).findAllByOrderByPriceAsc();
    }
    
    @Test
    @DisplayName("Should get books ordered by price descending")
    void shouldGetBooksOrderedByPriceDesc() {
        // Given
        when(bookRepository.findAllByOrderByPriceDesc()).thenReturn(books);
        
        // When
        List<BookResponse> result = bookSearchService.getBooksOrderedByPriceDesc();
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bookRepository).findAllByOrderByPriceDesc();
    }
    
    @Test
    @DisplayName("Should get books ordered by title")
    void shouldGetBooksOrderedByTitle() {
        // Given
        when(bookRepository.findAllByOrderByTitleAsc()).thenReturn(books);
        
        // When
        List<BookResponse> result = bookSearchService.getBooksOrderedByTitle();
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bookRepository).findAllByOrderByTitleAsc();
    }
    
    @Test
    @DisplayName("Should get books ordered by author")
    void shouldGetBooksOrderedByAuthor() {
        // Given
        when(bookRepository.findAllByOrderByAuthorAsc()).thenReturn(books);
        
        // When
        List<BookResponse> result = bookSearchService.getBooksOrderedByAuthor();
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bookRepository).findAllByOrderByAuthorAsc();
    }
    
    @Test
    @DisplayName("Should return empty list when no books found")
    void shouldReturnEmptyListWhenNoBooksFound() {
        // Given
        when(bookRepository.findByAuthorContainingIgnoreCase("NonExistent")).thenReturn(Arrays.asList());
        
        // When
        List<BookResponse> result = bookSearchService.getBooksByAuthor("NonExistent");
        
        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bookRepository).findByAuthorContainingIgnoreCase("NonExistent");
    }
} 