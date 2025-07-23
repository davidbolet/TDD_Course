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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReactiveBookOrderServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private ReactiveBookOrderService reactiveBookOrderService;

    private Book bookWithStock;
    private Book bookWithoutStock;

    @BeforeEach
    void setUp() {
        bookWithStock = new Book(
            "Test Book",
            "Test Author",
            "1234567890",
            "Test Description",
            new BigDecimal("29.99"),
            5,
            BookCategory.FICTION
        );
        bookWithStock.setId(1L);

        bookWithoutStock = new Book(
            "Out of Stock Book",
            "Test Author",
            "0987654321",
            "Test Description",
            new BigDecimal("19.99"),
            0,
            BookCategory.NON_FICTION
        );
        bookWithoutStock.setId(2L);
    }

    @Test
    void checkStock_WhenBookExistsWithStock_ShouldReturnTrue() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(bookWithStock));

        // When
        Mono<Boolean> result = reactiveBookOrderService.checkStock(1L);

        // Then
        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void checkStock_WhenBookExistsWithoutStock_ShouldReturnFalse() {
        // Given
        when(bookRepository.findById(2L)).thenReturn(Optional.of(bookWithoutStock));

        // When
        Mono<Boolean> result = reactiveBookOrderService.checkStock(2L);

        // Then
        StepVerifier.create(result)
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    void checkStock_WhenBookDoesNotExist_ShouldReturnFalse() {
        // Given
        when(bookRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Mono<Boolean> result = reactiveBookOrderService.checkStock(999L);

        // Then
        StepVerifier.create(result)
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    void checkStock_WhenBookHasNullStock_ShouldReturnFalse() {
        // Given
        Book bookWithNullStock = new Book(
            "Null Stock Book",
            "Test Author",
            "1111111111",
            "Test Description",
            new BigDecimal("15.99"),
            null,
            BookCategory.FICTION
        );
        bookWithNullStock.setId(3L);
        when(bookRepository.findById(3L)).thenReturn(Optional.of(bookWithNullStock));

        // When
        Mono<Boolean> result = reactiveBookOrderService.checkStock(3L);

        // Then
        StepVerifier.create(result)
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    void checkStock_WhenBookHasNegativeStock_ShouldReturnFalse() {
        // Given
        Book bookWithNegativeStock = new Book(
            "Negative Stock Book",
            "Test Author",
            "2222222222",
            "Test Description",
            new BigDecimal("25.99"),
            -5,
            BookCategory.FICTION
        );
        bookWithNegativeStock.setId(4L);
        when(bookRepository.findById(4L)).thenReturn(Optional.of(bookWithNegativeStock));

        // When
        Mono<Boolean> result = reactiveBookOrderService.checkStock(4L);

        // Then
        StepVerifier.create(result)
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    void checkStock_WhenBookHasExactlyOneStock_ShouldReturnTrue() {
        // Given
        Book bookWithOneStock = new Book(
            "One Stock Book",
            "Test Author",
            "3333333333",
            "Test Description",
            new BigDecimal("35.99"),
            1,
            BookCategory.FICTION
        );
        bookWithOneStock.setId(5L);
        when(bookRepository.findById(5L)).thenReturn(Optional.of(bookWithOneStock));

        // When
        Mono<Boolean> result = reactiveBookOrderService.checkStock(5L);

        // Then
        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void checkStock_WhenBookHasHighStock_ShouldReturnTrue() {
        // Given
        Book bookWithHighStock = new Book(
            "High Stock Book",
            "Test Author",
            "4444444444",
            "Test Description",
            new BigDecimal("45.99"),
            1000,
            BookCategory.FICTION
        );
        bookWithHighStock.setId(6L);
        when(bookRepository.findById(6L)).thenReturn(Optional.of(bookWithHighStock));

        // When
        Mono<Boolean> result = reactiveBookOrderService.checkStock(6L);

        // Then
        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void checkStock_WhenBookIdIsNull_ShouldThrowException() {
        // Given
        Long bookId = null;

        // When & Then
        StepVerifier.create(reactiveBookOrderService.checkStock(bookId))
                .expectError(IllegalArgumentException.class)
                .verify();
    }

    @Test
    void checkStock_WhenBookIdIsNegative_ShouldReturnFalse() {
        // Given
        Long bookId = -1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // When
        Mono<Boolean> result = reactiveBookOrderService.checkStock(bookId);

        // Then
        StepVerifier.create(result)
                .expectNext(false)
                .verifyComplete();
    }
} 