package com.talant.bootcamp.booksservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talant.bootcamp.booksservice.dto.BookRequest;
import com.talant.bootcamp.booksservice.dto.BookResponse;
import com.talant.bootcamp.booksservice.exception.BookNotFoundException;
import com.talant.bootcamp.booksservice.exception.DuplicateIsbnException;
import com.talant.bootcamp.booksservice.exception.GlobalExceptionHandler;
import com.talant.bootcamp.booksservice.model.Book;
import com.talant.bootcamp.booksservice.model.BookCategory;
import com.talant.bootcamp.booksservice.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@DisplayName("Book Controller Tests with WebMvcTest")
class BookControllerTest {
    
    @MockitoBean
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
    private BookRequest bookRequest;
    private BookResponse bookResponse;
    private Book book;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

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
    @DisplayName("Should create book successfully")
    void shouldCreateBook() throws Exception {
		// Given
		when(bookService.createBook(any(BookRequest.class))).thenReturn(bookResponse);

		// When & Then
		mockMvc.perform(post("/api/books")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(bookRequest)))
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.title").value(book.getTitle()))
			.andExpect(jsonPath("$.author").value(book.getAuthor()))
			.andExpect(jsonPath("$.isbn").value(book.getIsbn()))
			.andExpect(jsonPath("$.description").value(book.getDescription()))
			.andExpect(jsonPath("$.price").value(book.getPrice().doubleValue()))
			.andExpect(jsonPath("$.stock").value(book.getStock()))
			.andExpect(jsonPath("$.category").value(book.getCategory().name()));

    }

	    @Test
    @DisplayName("Should get all books")
    void shouldGetAllBooks() throws Exception {

    }

    @Test
    @DisplayName("Should get book by ID")
    void shouldGetBookById() throws Exception {

    }

    @Test
    @DisplayName("Should get book by ISBN")
    void shouldGetBookByIsbn() throws Exception {
	}

    @Test
    @DisplayName("Should update book successfully")
    void shouldUpdateBook() throws Exception {
 
    }

    @Test
    @DisplayName("Should delete book successfully")
    void shouldDeleteBook() throws Exception {

    }

    @Test
    @DisplayName("Should get books by author")
    void shouldGetBooksByAuthor() throws Exception {

    }

    @Test
    @DisplayName("Should get books by title")
    void shouldGetBooksByTitle() throws Exception {

    }

    @Test
    @DisplayName("Should get books by category")
    void shouldGetBooksByCategory() throws Exception {

    }

    @Test
    @DisplayName("Should get books with stock")
    void shouldGetBooksWithStock() throws Exception {

    }

    @Test
    @DisplayName("Should get books out of stock")
    void shouldGetBooksOutOfStock() throws Exception {

    }

    @Test
    @DisplayName("Should search books by text")
    void shouldSearchBooksByText() throws Exception {

    }

    @Test
    @DisplayName("Should update stock")
    void shouldUpdateStock() throws Exception {

    }

    @Test
    @DisplayName("Should check if book exists by ISBN")
    void shouldCheckIfBookExistsByIsbn() throws Exception {

    }

    @Test
    @DisplayName("Should get categories")
    void shouldGetCategories() throws Exception {

    }

    @Test
    @DisplayName("Should handle book not found exception")
    void shouldHandleBookNotFoundException() throws Exception {

    }

    @Test
    @DisplayName("Should handle duplicate ISBN exception")
    void shouldHandleDuplicateIsbnException() throws Exception {

    }

    @Test
    @DisplayName("Should validate ISBN format")
    void shouldValidateIsbnFormat() throws Exception {
        // Given - Invalid ISBN format
        BookRequest invalidRequest = new BookRequest(
            "Test Book",
            "Test Author",
            "invalid-isbn",
            "Test Description",
            new BigDecimal("29.99"),
            10,
            BookCategory.FICTION
        );

    }

    @Test
    @DisplayName("Should validate positive price")
    void shouldValidatePositivePrice() throws Exception {
        // Given - Negative price
        BookRequest invalidRequest = new BookRequest(
            "Test Book",
            "Test Author",
            "1234567890",
            "Test Description",
            new BigDecimal("-10.00"),
            10,
            BookCategory.FICTION
        );

    }

    @Test
    @DisplayName("Should validate non-negative stock")
    void shouldValidateNonNegativeStock() throws Exception {
        // Given - Negative stock
        BookRequest invalidRequest = new BookRequest(
            "Test Book",
            "Test Author",
            "1234567890",
            "Test Description",
            new BigDecimal("29.99"),
            -5,
            BookCategory.FICTION
        );

    }

    @Test
    @DisplayName("Should validate required fields")
    void shouldValidateRequiredFields() throws Exception {

    }
    
} 