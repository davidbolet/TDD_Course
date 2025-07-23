package com.talant.bootcamp.booksservice.service;

import com.talant.bootcamp.booksservice.dto.BookRequest;
import com.talant.bootcamp.booksservice.dto.BookResponse;
import com.talant.bootcamp.booksservice.exception.BookNotFoundException;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Book Service Tests")
class BookServiceTest {
    
    @Mock
    private BookRepository bookRepository;
    
    @Mock
    private BookValidator bookValidator;
    
    @InjectMocks
    private BookService bookService;
    
    private BookRequest bookRequest;
    private Book book;
    private BookResponse bookResponse;
    
    @BeforeEach
    void setUp() {
        bookRequest = new BookRequest(
            "Test Book",
            "Test Author",
            "1234567890",
            "Test Description",
            new BigDecimal("29.99"),
            10,
            BookCategory.FICTION
        );
        
        book = new Book(
            "Test Book",
            "Test Author",
            "1234567890",
            "Test Description",
            new BigDecimal("29.99"),
            10,
            BookCategory.FICTION
        );
        book.setId(1L);
        
        bookResponse = new BookResponse(book);
    }
    
    @Test
    @DisplayName("Should create a book successfully")
    void shouldCreateBook() {
        // Given
        doNothing().when(bookValidator).validateForCreation(bookRequest);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        
        // When
        BookResponse result = bookService.createBook(bookRequest);
        
        // Then
        assertNotNull(result);
        assertEquals(bookRequest.getTitle(), result.getTitle());
        assertEquals(bookRequest.getAuthor(), result.getAuthor());
        assertEquals(bookRequest.getIsbn(), result.getIsbn());
        verify(bookValidator).validateForCreation(bookRequest);
        verify(bookRepository).save(any(Book.class));
    }
    
    @Test
    @DisplayName("Should throw exception when validation fails during creation")
    void shouldThrowExceptionWhenValidationFailsDuringCreation() {
        // Given
        doThrow(new DuplicateIsbnException(bookRequest.getIsbn())).when(bookValidator).validateForCreation(bookRequest);
        
        // When & Then
        assertThrows(DuplicateIsbnException.class, () -> {
            bookService.createBook(bookRequest);
        });
        
        verify(bookValidator).validateForCreation(bookRequest);
        verify(bookRepository, never()).save(any(Book.class));
    }
    
    @Test
    @DisplayName("Should get all books")
    void shouldGetAllBooks() {
        // Given
        List<Book> books = Arrays.asList(book);
        when(bookRepository.findAll()).thenReturn(books);
        
        // When
        List<BookResponse> result = bookService.getAllBooks();
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(book.getTitle(), result.get(0).getTitle());
        verify(bookRepository).findAll();
    }
    
    @Test
    @DisplayName("Should get book by ID")
    void shouldGetBookById() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        
        // When
        BookResponse result = bookService.getBookById(1L);
        
        // Then
        assertNotNull(result);
        assertEquals(book.getTitle(), result.getTitle());
        verify(bookRepository).findById(1L);
    }
    
    @Test
    @DisplayName("Should throw BookNotFoundException when book not found by ID")
    void shouldThrowBookNotFoundExceptionWhenBookNotFoundById() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(BookNotFoundException.class, () -> {
            bookService.getBookById(1L);
        });
        
        verify(bookRepository).findById(1L);
    }
    
    @Test
    @DisplayName("Should get book by ISBN")
    void shouldGetBookByIsbn() {
        // Given
        when(bookRepository.findByIsbn("1234567890")).thenReturn(Optional.of(book));
        
        // When
        BookResponse result = bookService.getBookByIsbn("1234567890");
        
        // Then
        assertNotNull(result);
        assertEquals(book.getIsbn(), result.getIsbn());
        verify(bookRepository).findByIsbn("1234567890");
    }
    
    @Test
    @DisplayName("Should throw BookNotFoundException when book not found by ISBN")
    void shouldThrowBookNotFoundExceptionWhenBookNotFoundByIsbn() {
        // Given
        when(bookRepository.findByIsbn("1234567890")).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(BookNotFoundException.class, () -> {
            bookService.getBookByIsbn("1234567890");
        });
        
        verify(bookRepository).findByIsbn("1234567890");
    }
    
    @Test
    @DisplayName("Should update book successfully")
    void shouldUpdateBook() {
        // Given
        BookRequest updateRequest = new BookRequest(
            "Updated Book",
            "Updated Author",
            "0987654321",
            "Updated Description",
            new BigDecimal("39.99"),
            20,
            BookCategory.TECHNOLOGY
        );
        
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        doNothing().when(bookValidator).validateForUpdate(1L, updateRequest);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        
        // When
        BookResponse result = bookService.updateBook(1L, updateRequest);
        
        // Then
        assertNotNull(result);
        verify(bookRepository).findById(1L);
        verify(bookValidator).validateForUpdate(1L, updateRequest);
        verify(bookRepository).save(any(Book.class));
    }
    
    @Test
    @DisplayName("Should throw BookNotFoundException when updating non-existent book")
    void shouldThrowBookNotFoundExceptionWhenUpdatingNonExistentBook() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(BookNotFoundException.class, () -> {
            bookService.updateBook(1L, bookRequest);
        });
        
        verify(bookRepository).findById(1L);
        verify(bookRepository, never()).save(any(Book.class));
    }
    
    @Test
    @DisplayName("Should delete book successfully")
    void shouldDeleteBook() {
        // Given
        when(bookRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(1L);
        
        // When
        bookService.deleteBook(1L);
        
        // Then
        verify(bookRepository).existsById(1L);
        verify(bookRepository).deleteById(1L);
    }
    
    @Test
    @DisplayName("Should throw BookNotFoundException when deleting non-existent book")
    void shouldThrowBookNotFoundExceptionWhenDeletingNonExistentBook() {
        // Given
        when(bookRepository.existsById(1L)).thenReturn(false);
        
        // When & Then
        assertThrows(BookNotFoundException.class, () -> {
            bookService.deleteBook(1L);
        });
        
        verify(bookRepository).existsById(1L);
        verify(bookRepository, never()).deleteById(any());
    }
    

    
    @Test
    @DisplayName("Should update stock successfully")
    void shouldUpdateStock() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        
        // When
        BookResponse result = bookService.updateStock(1L, 25);
        
        // Then
        assertNotNull(result);
        verify(bookRepository).findById(1L);
        verify(bookRepository).save(any(Book.class));
    }
    
    @Test
    @DisplayName("Should check if book exists by ISBN")
    void shouldCheckIfBookExistsByIsbn() {
        // Given
        when(bookRepository.existsByIsbn("1234567890")).thenReturn(true);
        
        // When
        boolean result = bookService.existsByIsbn("1234567890");
        
        // Then
        assertTrue(result);
        verify(bookRepository).existsByIsbn("1234567890");
    }
} 