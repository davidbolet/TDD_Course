package com.talant.bootcamp.booksservice.service;

import com.talant.bootcamp.booksservice.model.Book;
import com.talant.bootcamp.booksservice.model.BookCategory;
import com.talant.bootcamp.booksservice.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReactiveBookSearchServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private ReactiveBookSearchService reactiveBookSearchService;

    private Book book1;
    private Book book2;
    private Book book3;

    @BeforeEach
    void setUp() {
        book1 = new Book(
            "Java Programming",
            "John Doe",
            "1234567890",
            "Learn Java programming",
            new BigDecimal("29.99"),
            10,
            BookCategory.TECHNOLOGY
        );
        book1.setId(1L);

        book2 = new Book(
            "Advanced Java",
            "Jane Smith",
            "0987654321",
            "Advanced Java concepts",
            new BigDecimal("39.99"),
            5,
            BookCategory.TECHNOLOGY
        );
        book2.setId(2L);

        book3 = new Book(
            "Python Basics",
            "Bob Johnson",
            "1122334455",
            "Python programming basics",
            new BigDecimal("24.99"),
            15,
            BookCategory.TECHNOLOGY
        );
        book3.setId(3L);
    }

    @Test
    void findByTitleContains_WhenKeywordMatchesOneBook_ShouldReturnOneBook() {
        // Given
        String keyword = "Java";
        List<Book> expectedBooks = Arrays.asList(book1, book2);
        when(bookRepository.findByTitleContainingIgnoreCase(keyword)).thenReturn(expectedBooks);

        // When
        Flux<Book> result = reactiveBookSearchService.findByTitleContains(keyword);

        // Then
        StepVerifier.create(result)
                .expectNext(book1)
                .expectNext(book2)
                .verifyComplete();
    }

    @Test
    void findByTitleContains_WhenKeywordMatchesMultipleBooks_ShouldReturnAllMatchingBooks() {
        // Given
        String keyword = "Programming";
        List<Book> expectedBooks = Arrays.asList(book1, book3);
        when(bookRepository.findByTitleContainingIgnoreCase(keyword)).thenReturn(expectedBooks);

        // When
        Flux<Book> result = reactiveBookSearchService.findByTitleContains(keyword);

        // Then
        StepVerifier.create(result)
                .expectNext(book1)
                .expectNext(book3)
                .verifyComplete();
    }

    @Test
    void findByTitleContains_WhenKeywordDoesNotMatchAnyBook_ShouldReturnEmptyFlux() {
        // Given
        String keyword = "NonExistent";
        when(bookRepository.findByTitleContainingIgnoreCase(keyword)).thenReturn(Collections.emptyList());

        // When
        Flux<Book> result = reactiveBookSearchService.findByTitleContains(keyword);

        // Then
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void findByTitleContains_WhenKeywordIsEmpty_ShouldReturnAllBooks() {
        // Given
        String keyword = "";
        List<Book> expectedBooks = Arrays.asList(book1, book2, book3);
        when(bookRepository.findByTitleContainingIgnoreCase(keyword)).thenReturn(expectedBooks);

        // When
        Flux<Book> result = reactiveBookSearchService.findByTitleContains(keyword);

        // Then
        StepVerifier.create(result)
                .expectNext(book1)
                .expectNext(book2)
                .expectNext(book3)
                .verifyComplete();
    }

    @Test
    void findByTitleContains_WhenKeywordIsNull_ShouldReturnAllBooks() {
        // Given
        String keyword = null;
        List<Book> expectedBooks = Arrays.asList(book1, book2, book3);
        when(bookRepository.findByTitleContainingIgnoreCase(keyword)).thenReturn(expectedBooks);

        // When
        Flux<Book> result = reactiveBookSearchService.findByTitleContains(keyword);

        // Then
        StepVerifier.create(result)
                .expectNext(book1)
                .expectNext(book2)
                .expectNext(book3)
                .verifyComplete();
    }

    @Test
    void findByTitleContains_WhenKeywordIsCaseInsensitive_ShouldReturnMatchingBooks() {
        // Given
        String keyword = "java";
        List<Book> expectedBooks = Arrays.asList(book1, book2);
        when(bookRepository.findByTitleContainingIgnoreCase(keyword)).thenReturn(expectedBooks);

        // When
        Flux<Book> result = reactiveBookSearchService.findByTitleContains(keyword);

        // Then
        StepVerifier.create(result)
                .expectNext(book1)
                .expectNext(book2)
                .verifyComplete();
    }

    @Test
    void findByTitleContains_WhenKeywordIsUpperCase_ShouldReturnMatchingBooks() {
        // Given
        String keyword = "JAVA";
        List<Book> expectedBooks = Arrays.asList(book1, book2);
        when(bookRepository.findByTitleContainingIgnoreCase(keyword)).thenReturn(expectedBooks);

        // When
        Flux<Book> result = reactiveBookSearchService.findByTitleContains(keyword);

        // Then
        StepVerifier.create(result)
                .expectNext(book1)
                .expectNext(book2)
                .verifyComplete();
    }

    @Test
    void findByTitleContains_WhenKeywordIsPartialWord_ShouldReturnMatchingBooks() {
        // Given
        String keyword = "Prog";
        List<Book> expectedBooks = Arrays.asList(book1, book3);
        when(bookRepository.findByTitleContainingIgnoreCase(keyword)).thenReturn(expectedBooks);

        // When
        Flux<Book> result = reactiveBookSearchService.findByTitleContains(keyword);

        // Then
        StepVerifier.create(result)
                .expectNext(book1)
                .expectNext(book3)
                .verifyComplete();
    }

    @Test
    void findByTitleContains_WhenKeywordIsSingleCharacter_ShouldReturnMatchingBooks() {
        // Given
        String keyword = "J";
        List<Book> expectedBooks = Arrays.asList(book1, book2);
        when(bookRepository.findByTitleContainingIgnoreCase(keyword)).thenReturn(expectedBooks);

        // When
        Flux<Book> result = reactiveBookSearchService.findByTitleContains(keyword);

        // Then
        StepVerifier.create(result)
                .expectNext(book1)
                .expectNext(book2)
                .verifyComplete();
    }

    @Test
    void findByTitleContains_WhenKeywordHasSpecialCharacters_ShouldReturnMatchingBooks() {
        // Given
        String keyword = "Java-Programming";
        List<Book> expectedBooks = Collections.singletonList(book1);
        when(bookRepository.findByTitleContainingIgnoreCase(keyword)).thenReturn(expectedBooks);

        // When
        Flux<Book> result = reactiveBookSearchService.findByTitleContains(keyword);

        // Then
        StepVerifier.create(result)
                .expectNext(book1)
                .verifyComplete();
    }

    @Test
    void findByTitleContains_WhenKeywordHasNumbers_ShouldReturnMatchingBooks() {
        // Given
        String keyword = "Java123";
        List<Book> expectedBooks = Collections.singletonList(book1);
        when(bookRepository.findByTitleContainingIgnoreCase(keyword)).thenReturn(expectedBooks);

        // When
        Flux<Book> result = reactiveBookSearchService.findByTitleContains(keyword);

        // Then
        StepVerifier.create(result)
                .expectNext(book1)
                .verifyComplete();
    }

    @Test
    void findByTitleContains_WhenKeywordIsVeryLong_ShouldReturnMatchingBooks() {
        // Given
        String keyword = "This is a very long keyword that might not match anything";
        when(bookRepository.findByTitleContainingIgnoreCase(keyword)).thenReturn(Collections.emptyList());

        // When
        Flux<Book> result = reactiveBookSearchService.findByTitleContains(keyword);

        // Then
        StepVerifier.create(result)
                .verifyComplete();
    }
} 