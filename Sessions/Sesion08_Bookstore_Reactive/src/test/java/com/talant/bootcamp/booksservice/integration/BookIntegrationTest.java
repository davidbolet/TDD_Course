package com.talant.bootcamp.booksservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talant.bootcamp.booksservice.dto.BookRequest;
import com.talant.bootcamp.booksservice.dto.BookResponse;
import com.talant.bootcamp.booksservice.model.BookCategory;
import com.talant.bootcamp.booksservice.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Transactional
@DisplayName("Book Integration Tests")
class BookIntegrationTest {
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private MockMvc mockMvc;
    
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        bookRepository.deleteAll();
    }
    
    @Test
    @DisplayName("Should create and retrieve a book")
    void shouldCreateAndRetrieveBook() throws Exception {
        // Given
        BookRequest bookRequest = new BookRequest(
            "Test Book",
            "Test Author",
            "1234567890",
            "Test Description",
            new BigDecimal("29.99"),
            10,
            BookCategory.FICTION
        );
        
        // When & Then - Create book
        String createResponse = mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author").value("Test Author"))
                .andExpect(jsonPath("$.isbn").value("1234567890"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        BookResponse createdBook = objectMapper.readValue(createResponse, BookResponse.class);
        
        // When & Then - Retrieve book
        mockMvc.perform(get("/api/books/" + createdBook.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdBook.getId()))
                .andExpect(jsonPath("$.title").value("Test Book"));
    }
    
    @Test
    @DisplayName("Should update an existing book")
    void shouldUpdateExistingBook() throws Exception {
     
    }
    
    @Test
    @DisplayName("Should delete a book")
    void shouldDeleteBook() throws Exception {
    
    }
    
    @Test
    @DisplayName("Should search books by author")
    void shouldSearchBooksByAuthor() throws Exception {
        // Given - Create multiple books
        createTestBooks();
        
        // When & Then - Search by author
        mockMvc.perform(get("/api/books/author/Tolkien"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].author").value("J.R.R. Tolkien"));
    }
    
    @Test
    @DisplayName("Should search books by title")
    void shouldSearchBooksByTitle() throws Exception {
       
    }
    
    @Test
    @DisplayName("Should search books by category")
    void shouldSearchBooksByCategory() throws Exception {
      
    }
    
    @Test
    @DisplayName("Should get books with stock available")
    void shouldGetBooksWithStock() throws Exception {
      
    }
    
    @Test
    @DisplayName("Should get books without stock")
    void shouldGetBooksOutOfStock() throws Exception {
      
    }
    
    @Test
    @DisplayName("Should search books by text")
    void shouldSearchBooksByText() throws Exception {
        // Given - Create multiple books
        createTestBooks();
        
        // When & Then - Search by text
        mockMvc.perform(get("/api/books/search")
                .param("q", "Tolkien"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].author").value("J.R.R. Tolkien"));
    }
    
    @Test
    @DisplayName("Should update stock of a book")
    void shouldUpdateBookStock() throws Exception {

    }
    
    @Test
    @DisplayName("Should check if a book exists by ISBN")
    void shouldCheckExistsByIsbn() throws Exception {
     
    }
    
    @Test
    @DisplayName("Should get available categories")
    void shouldGetCategories() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/books/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").value("Fiction"));
    }
    
    @Test
    @DisplayName("Should handle duplicate ISBN")
    void shouldHandleDuplicateIsbn() throws Exception {
        
    }
    
    @Test
    @DisplayName("Should validate required fields")
    void shouldValidateRequiredFields() throws Exception {
        // Given - Request without required fields
       
    }
    
    @Test
    @DisplayName("Should handle book not found")
    void shouldHandleBookNotFound() throws Exception {
      
    }
    
    private void createTestBooks() throws Exception {
        // Create several test books
        BookRequest[] bookRequests = {
            new BookRequest("The Lord of the Rings", "J.R.R. Tolkien", "9788445071405", 
                          "Epic fantasy", new BigDecimal("29.99"), 50, BookCategory.FANTASY),
            new BookRequest("1984", "George Orwell", "9788497594257", 
                          "Dystopia", new BigDecimal("19.99"), 30, BookCategory.FICTION),
            new BookRequest("Clean Code", "Robert C. Martin", "9780132350884", 
                          "Programming", new BigDecimal("45.99"), 15, BookCategory.TECHNOLOGY)
        };
        
        for (BookRequest request : bookRequests) {
            mockMvc.perform(post("/api/books")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated());
        }
    }
} 