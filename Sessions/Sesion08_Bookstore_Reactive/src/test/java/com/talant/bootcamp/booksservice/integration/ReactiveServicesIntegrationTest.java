package com.talant.bootcamp.booksservice.integration;

import com.talant.bootcamp.booksservice.model.Book;
import com.talant.bootcamp.booksservice.model.BookCategory;
import com.talant.bootcamp.booksservice.model.Review;
import com.talant.bootcamp.booksservice.repository.BookRepository;
import com.talant.bootcamp.booksservice.repository.ReviewRepository;
import com.talant.bootcamp.booksservice.service.ReactiveBookOrderService;
import com.talant.bootcamp.booksservice.service.ReactiveBookSearchService;
import com.talant.bootcamp.booksservice.service.ReactiveBookStatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ReactiveServicesIntegrationTest {

    @Autowired
    private ReactiveBookOrderService reactiveBookOrderService;

    @Autowired
    private ReactiveBookSearchService reactiveBookSearchService;

    @Autowired
    private ReactiveBookStatisticsService reactiveBookStatisticsService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    private Book book1;
    private Book book2;
    private Book book3;

    @BeforeEach
    void setUp() {
        // Limpiar datos existentes
        reviewRepository.deleteAll();
        bookRepository.deleteAll();

        // Crear libros de prueba
        book1 = new Book(
            "Java Programming Guide",
            "John Doe",
            "1234567890",
            "Complete guide to Java programming",
            new BigDecimal("29.99"),
            10,
            BookCategory.TECHNOLOGY
        );

        book2 = new Book(
            "Advanced Java Concepts",
            "Jane Smith",
            "0987654321",
            "Advanced Java programming concepts",
            new BigDecimal("39.99"),
            0,
            BookCategory.TECHNOLOGY
        );

        book3 = new Book(
            "Python Basics",
            "Bob Johnson",
            "1122334455",
            "Python programming basics",
            new BigDecimal("24.99"),
            5,
            BookCategory.TECHNOLOGY
        );

        // Guardar libros
        book1 = bookRepository.save(book1);
        book2 = bookRepository.save(book2);
        book3 = bookRepository.save(book3);

        // Crear reviews para book1
        Review review1 = new Review(null, book1.getId(), "user1", 5, "Excellent book!");
        Review review2 = new Review(null, book1.getId(), "user2", 4, "Very good book");
        Review review3 = new Review(null, book1.getId(), "user3", 3, "Good book");

        // Crear reviews para book2
        Review review4 = new Review(null, book2.getId(), "user4", 2, "Average book");
        Review review5 = new Review(null, book2.getId(), "user5", 1, "Poor book");

        // Guardar reviews
        reviewRepository.saveAll(List.of(review1, review2, review3, review4, review5));
    }

    @Test
    void testReactiveBookOrderService_CheckStock() {
        // Test book with stock
        StepVerifier.create(reactiveBookOrderService.checkStock(book1.getId()))
                .expectNext(true)
                .verifyComplete();

        // Test book without stock
        StepVerifier.create(reactiveBookOrderService.checkStock(book2.getId()))
                .expectNext(false)
                .verifyComplete();

        // Test book with some stock
        StepVerifier.create(reactiveBookOrderService.checkStock(book3.getId()))
                .expectNext(true)
                .verifyComplete();

        // Test non-existent book
        StepVerifier.create(reactiveBookOrderService.checkStock(999L))
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    void testReactiveBookSearchService_FindByTitleContains() {
        // Test search for "Java"
        StepVerifier.create(reactiveBookSearchService.findByTitleContains("Java"))
                .expectNextCount(2) // book1 and book2
                .verifyComplete();

        // Test search for "Python"
        StepVerifier.create(reactiveBookSearchService.findByTitleContains("Python"))
                .expectNextCount(1) // book3
                .verifyComplete();

        // Test search for "Programming"
        StepVerifier.create(reactiveBookSearchService.findByTitleContains("Programming"))
                .expectNextCount(1) // book1
                .verifyComplete();

        // Test search for non-existent keyword
        StepVerifier.create(reactiveBookSearchService.findByTitleContains("NonExistent"))
                .verifyComplete();

        // Test case insensitive search
        StepVerifier.create(reactiveBookSearchService.findByTitleContains("java"))
                .expectNextCount(2) // book1 and book2
                .verifyComplete();
    }

    @Test
    void testReactiveBookStatisticsService_GetAverageRating() {
        // Test average rating for book1 (5+4+3)/3 = 4.0
        StepVerifier.create(reactiveBookStatisticsService.getAverageRating(book1.getId()))
                .expectNext(4.0)
                .verifyComplete();

        // Test average rating for book2 (2+1)/2 = 1.5
        StepVerifier.create(reactiveBookStatisticsService.getAverageRating(book2.getId()))
                .expectNext(1.5)
                .verifyComplete();

        // Test average rating for book3 (no reviews) = 0.0
        StepVerifier.create(reactiveBookStatisticsService.getAverageRating(book3.getId()))
                .expectNext(0.0)
                .verifyComplete();

        // Test non-existent book
        StepVerifier.create(reactiveBookStatisticsService.getAverageRating(999L))
                .expectNext(0.0)
                .verifyComplete();
    }

    @Test
    void testReactiveServicesWorkflow_CompleteScenario() {
        // 1. Buscar libros con "Java"
        Flux<Book> javaBooks = reactiveBookSearchService.findByTitleContains("Java");
        
        // 2. Para cada libro encontrado, verificar stock y obtener estadísticas
        javaBooks.flatMap(book -> 
            Mono.zip(
                reactiveBookOrderService.checkStock(book.getId()),
                reactiveBookStatisticsService.getAverageRating(book.getId())
            ).map(tuple -> {
                boolean hasStock = tuple.getT1();
                double avgRating = tuple.getT2();
                
                // Verificar que los resultados son consistentes
                if (book.getId().equals(book1.getId())) {
                    assertTrue(hasStock, "Book1 should have stock");
                    assertEquals(4.0, avgRating, 0.01, "Book1 should have average rating of 4.0");
                } else if (book.getId().equals(book2.getId())) {
                    assertFalse(hasStock, "Book2 should not have stock");
                    assertEquals(1.5, avgRating, 0.01, "Book2 should have average rating of 1.5");
                }
                
                return book;
            })
        ).collectList()
        .as(StepVerifier::create)
        .expectNextMatches(books -> {
            assertEquals(2, books.size(), "Should find 2 Java books");
            return true;
        })
        .verifyComplete();
    }

    @Test
    void testReactiveServices_ConcurrentOperations() {
        // Ejecutar múltiples operaciones reactivas de forma concurrente
        Mono<Boolean> stockCheck1 = reactiveBookOrderService.checkStock(book1.getId());
        Mono<Boolean> stockCheck2 = reactiveBookOrderService.checkStock(book2.getId());
        Mono<Boolean> stockCheck3 = reactiveBookOrderService.checkStock(book3.getId());
        
        Mono<Double> avgRating1 = reactiveBookStatisticsService.getAverageRating(book1.getId());
        Mono<Double> avgRating2 = reactiveBookStatisticsService.getAverageRating(book2.getId());
        Mono<Double> avgRating3 = reactiveBookStatisticsService.getAverageRating(book3.getId());
        
        Flux<Book> searchResults = reactiveBookSearchService.findByTitleContains("Java");

        // Combinar todas las operaciones
        Mono.zip(stockCheck1, stockCheck2, stockCheck3, avgRating1, avgRating2, avgRating3)
            .flatMapMany(tuple -> {
                boolean stock1 = tuple.getT1();
                boolean stock2 = tuple.getT2();
                boolean stock3 = tuple.getT3();
                double rating1 = tuple.getT4();
                double rating2 = tuple.getT5();
                double rating3 = tuple.getT6();

                // Verificar resultados
                assertTrue(stock1, "Book1 should have stock");
                assertFalse(stock2, "Book2 should not have stock");
                assertTrue(stock3, "Book3 should have stock");
                
                assertEquals(4.0, rating1, 0.01, "Book1 should have average rating of 4.0");
                assertEquals(1.5, rating2, 0.01, "Book2 should have average rating of 1.5");
                assertEquals(0.0, rating3, 0.01, "Book3 should have average rating of 0.0");

                return searchResults;
            })
            .collectList()
            .as(StepVerifier::create)
            .expectNextMatches(books -> {
                assertEquals(2, books.size(), "Should find 2 Java books");
                return true;
            })
            .verifyComplete();
    }

    @Test
    void testReactiveServices_ErrorHandling() {
        // Test con bookId null - debería lanzar excepción
        StepVerifier.create(reactiveBookOrderService.checkStock(null))
                .expectError(IllegalArgumentException.class)
                .verify();

        StepVerifier.create(reactiveBookStatisticsService.getAverageRating(null))
                .expectError(IllegalArgumentException.class)
                .verify();

        // Test con bookId negativo - debería funcionar normalmente
        StepVerifier.create(reactiveBookOrderService.checkStock(-1L))
                .expectNext(false)
                .verifyComplete();

        StepVerifier.create(reactiveBookStatisticsService.getAverageRating(-1L))
                .expectNext(0.0)
                .verifyComplete();
    }
} 