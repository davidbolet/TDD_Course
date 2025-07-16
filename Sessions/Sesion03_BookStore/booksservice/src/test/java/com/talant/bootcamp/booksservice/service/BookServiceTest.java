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
import org.junit.jupiter.api.Nested;
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
    
    @InjectMocks
    private BookService bookService;
    
    private BookRequest bookRequest;
    private Book book;
    private BookResponse bookResponse;


	@Nested
    class CrudOperations {
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
			//Arrange
			when(bookRepository.existsByIsbn(bookRequest.getIsbn())).thenReturn(false);
			when(bookRepository.save(any(Book.class))).thenReturn(book);
			
			//Act
			BookResponse createdBook = bookService.createBook(bookRequest);

			//Assert
			assertNotNull(createdBook);
			assertEquals(bookRequest.getTitle(), createdBook.getTitle());
			assertEquals(bookRequest.getAuthor(), createdBook.getAuthor());
			assertEquals(bookRequest.getIsbn(), createdBook.getIsbn());
			assertEquals(bookRequest.getDescription(), createdBook.getDescription());
			verify(bookRepository, times(1)).existsByIsbn(bookRequest.getIsbn());
			verify(bookRepository, times(1)).save(any(Book.class));
			
		}
	
	}

    
    
} 