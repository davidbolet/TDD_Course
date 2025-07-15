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