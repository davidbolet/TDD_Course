package com.talant.bootcamp.booksservice.repository;

import com.talant.bootcamp.booksservice.model.Book;
import com.talant.bootcamp.booksservice.model.BookCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Book Repository Tests")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private Book fictionBook;
    private Book fantasyBook;
    private Book technologyBook;
    private Book mysteryBook;
    private Book outOfStockBook;
    private Book lowStockBook;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        
        // Create test books
        fictionBook = new Book(
            "1984", "George Orwell", "1234567890",
            "Dystopian novel", new BigDecimal("19.99"), 50, BookCategory.FICTION
        );
        
        fantasyBook = new Book(
            "The Lord of the Rings", "J.R.R. Tolkien", "2345678901",
            "Epic fantasy", new BigDecimal("29.99"), 30, BookCategory.FANTASY
        );
        
        technologyBook = new Book(
            "Clean Code", "Robert C. Martin", "3456789012",
            "Programming best practices", new BigDecimal("45.99"), 15, BookCategory.TECHNOLOGY
        );
        
        mysteryBook = new Book(
            "The Hound of the Baskervilles", "Arthur Conan Doyle", "4567890123",
            "Sherlock Holmes mystery", new BigDecimal("15.99"), 25, BookCategory.MYSTERY
        );
        
        outOfStockBook = new Book(
            "Out of Stock Book", "Unknown Author", "5678901234",
            "No stock available", new BigDecimal("10.00"), 0, BookCategory.FICTION
        );
        
        lowStockBook = new Book(
            "Low Stock Book", "Another Author", "6789012345",
            "Only few copies left", new BigDecimal("25.00"), 5, BookCategory.SCIENCE_FICTION
        );
        
        // Save all books
        bookRepository.saveAll(List.of(
            fictionBook, fantasyBook, technologyBook, 
            mysteryBook, outOfStockBook, lowStockBook
        ));
    }

    @Test
    @DisplayName("Should save and find book by ID")
    void shouldSaveAndFindBookById() {
        // Given
        Book newBook = new Book(
            "Test Book", "Test Author", "9999999999",
            "Test Description", new BigDecimal("20.00"), 10, BookCategory.FICTION
        );
        
        // When
        Book savedBook = bookRepository.save(newBook);
        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());
        
        // Then
        assertThat(foundBook).isPresent(); // So we avoid NullPointerException
		assertThat(foundBook.get().getId()).isEqualTo(savedBook.getId()); // Same ID
        assertThat(foundBook.get().getTitle()).isEqualTo("Test Book");
        assertThat(foundBook.get().getAuthor()).isEqualTo("Test Author");
        assertThat(foundBook.get().getIsbn()).isEqualTo("9999999999");
    }

    @Test
    @DisplayName("Should find book by ISBN")
    void shouldFindBookByIsbn() {
       
    }

    @Test
    @DisplayName("Should return empty when ISBN not found")
    void shouldReturnEmptyWhenIsbnNotFound() {

    }

    @Test
    @DisplayName("Should find books by author (case insensitive)")
    void shouldFindBooksByAuthor() {

    }

    @Test
    @DisplayName("Should find multiple books by partial author name")
    void shouldFindMultipleBooksByPartialAuthor() {
		// Testing AssertJ enhaced assertions for multiple books by author
        // Given - Add another book with similar author name. 
        Book anotherBook = new Book(
            "Another Book", "George R.R. Martin", "7890123456",
            "Another description", new BigDecimal("30.00"), 20, BookCategory.FANTASY
        );
        bookRepository.save(anotherBook);
        
        // When
        List<Book> books = bookRepository.findByAuthorContainingIgnoreCase("George");
        
        // Then
        assertThat(books).hasSize(2);
        assertThat(books).extracting("author")
            .contains("George Orwell", "George R.R. Martin");
    }

    @Test
    @DisplayName("Should find books by title (case insensitive)")
    void shouldFindBooksByTitle() {
       
    }

    @Test
    @DisplayName("Should find books by category")
    void shouldFindBooksByCategory() {
        // When
        List<Book> books = bookRepository.findByCategory(BookCategory.FICTION);
        
        // Then
        assertThat(books).hasSize(2); // fictionBook and outOfStockBook
        assertThat(books).extracting("category")
            .allMatch(category -> category == BookCategory.FICTION);
    }

    @Test
    @DisplayName("Should find books with stock greater than specified amount")
    void shouldFindBooksWithStockGreaterThan() {
        // When
        List<Book> books = bookRepository.findByStockGreaterThan(20);
        
        // Then
        assertThat(books).hasSize(3); // fictionBook(50), fantasyBook(30), mysteryBook(25)
        assertThat(books).extracting("stock")
            .allMatch(stock -> (Integer) stock > 20);
    }

    @Test
    @DisplayName("Should find books with specific stock amount")
    void shouldFindBooksWithSpecificStock() {
      
    }

    @Test
    @DisplayName("Should find books by price range")
    void shouldFindBooksByPriceRange() {
		// This is a quite complicated test - I leave it as example
        // When
        List<Book> books = bookRepository.findByPriceBetween(
            new BigDecimal("15.00"), new BigDecimal("30.00")
        );
        
        // Then
        assertThat(books).hasSize(4); // fictionBook(19.99), fantasyBook(29.99), mysteryBook(15.99), lowStockBook(25.00)
        assertThat(books).extracting("price")
            .allMatch(price -> {
                BigDecimal p = (BigDecimal) price;
                return p.compareTo(new BigDecimal("15.00")) >= 0 && 
                       p.compareTo(new BigDecimal("30.00")) <= 0;
            });
    }

    @Test
    @DisplayName("Should find books with price less than or equal to max price")
    void shouldFindBooksWithPriceLessThanOrEqualTo() {

    }

    @Test
    @DisplayName("Should find books with price greater than or equal to min price")
    void shouldFindBooksWithPriceGreaterThanOrEqualTo() {
       
    }

    @Test
    @DisplayName("Should find books by author and category")
    void shouldFindBooksByAuthorAndCategory() {
       
    }

    @Test
    @DisplayName("Should find books by title and author")
    void shouldFindBooksByTitleAndAuthor() {
       
    }

    @Test
    @DisplayName("Should find books with low stock (less than 10)")
    void shouldFindBooksWithLowStock() {
       
    }

    @Test
    @DisplayName("Should search books by text in title or author")
    void shouldSearchBooksByTitleOrAuthor() {
       
    }

    @Test
    @DisplayName("Should search books by text in title")
    void shouldSearchBooksByTextInTitle() {
       
    }

    @Test
    @DisplayName("Should count books by category")
    void shouldCountBooksByCategory() {
        // When
        List<Object[]> results = bookRepository.countBooksByCategory();
        
        // Then
        assertThat(results).isNotEmpty();
        
        // Verify specific categories
        boolean fictionFound = false;
        boolean fantasyFound = false;
        
        for (Object[] result : results) {
            BookCategory category = (BookCategory) result[0];
            Long count = (Long) result[1];
            
            if (category == BookCategory.FICTION) {
                assertThat(count).isEqualTo(2L); // fictionBook and outOfStockBook
                fictionFound = true;
            } else if (category == BookCategory.FANTASY) {
                assertThat(count).isEqualTo(1L); // fantasyBook
                fantasyFound = true;
            }
        }
        
        assertThat(fictionFound).isTrue();
        assertThat(fantasyFound).isTrue();
    }

    @Test
    @DisplayName("Should get average price by category")
    void shouldGetAveragePriceByCategory() {
        // When
        List<Object[]> results = bookRepository.getAveragePriceByCategory();
        
        // Then
        assertThat(results).isNotEmpty();
        
        // Verify FICTION category average (19.99 + 10.00) / 2 = 14.995
        for (Object[] result : results) {
            BookCategory category = (BookCategory) result[0];
            Double avgPrice = (Double) result[1];
            
            if (category == BookCategory.FICTION) {
                assertThat(avgPrice).isCloseTo(14.995, within(0.01));
                break;
            }
        }
    }

    @Test
    @DisplayName("Should check if book exists by ISBN")
    void shouldCheckIfBookExistsByIsbn() {
        
    }

    @Test
    @DisplayName("Should find all books ordered by price ascending")
    void shouldFindAllBooksOrderedByPriceAsc() {
        // When
        List<Book> books = bookRepository.findAllByOrderByPriceAsc();
        
        // Then
        assertThat(books).hasSize(6);
        assertThat(books).extracting("price")
            .isSortedAccordingTo((p1, p2) -> ((BigDecimal) p1).compareTo((BigDecimal) p2));
    }

    @Test
    @DisplayName("Should find all books ordered by price descending")
    void shouldFindAllBooksOrderedByPriceDesc() {
       
    }

    @Test
    @DisplayName("Should find all books ordered by title ascending")
    void shouldFindAllBooksOrderedByTitleAsc() {
        // When
        List<Book> books = bookRepository.findAllByOrderByTitleAsc();
        
        // Then
        assertThat(books).hasSize(6);
        assertThat(books).extracting("title")
            .isSorted();
    }

    @Test
    @DisplayName("Should find all books ordered by author ascending")
    void shouldFindAllBooksOrderedByAuthorAsc() {
      
    }

    @Test
    @DisplayName("Should update book")
    void shouldUpdateBook() {
        // Update Some fields of an existing book
    }

    @Test
    @DisplayName("Should delete book")
    void shouldDeleteBook() {
       // Delete a book, try to retrieve it and check it is not found

    }

    @Test
    @DisplayName("Should count total books")
    void shouldCountTotalBooks() {
        // When
        long totalBooks = bookRepository.count();
        
        // Then
        assertThat(totalBooks).isEqualTo(6L);
    }

    @Test
    @DisplayName("Should check if book exists by ID")
    void shouldCheckIfBookExistsById() {
       
    }

    @Test
    @DisplayName("Should find all books")
    void shouldFindAllBooks() {
		// Example to check more exotic assertions
        // When
        List<Book> allBooks = bookRepository.findAll();
        
        // Then
        assertThat(allBooks).hasSize(6);
        assertThat(allBooks).extracting("isbn")
            .containsExactlyInAnyOrder(
                "1234567890", "2345678901", "3456789012",
                "4567890123", "5678901234", "6789012345"
            );
    }

    @Test
    @DisplayName("Should save multiple books")
    void shouldSaveMultipleBooks() {
        // Given
        List<Book> newBooks = List.of(
            new Book("Book 1", "Author 1", "1111111111", "Desc 1", 
                    new BigDecimal("10.00"), 5, BookCategory.FICTION),
            new Book("Book 2", "Author 2", "2222222222", "Desc 2", 
                    new BigDecimal("20.00"), 10, BookCategory.FANTASY)
        );
        
        // When
        List<Book> savedBooks = bookRepository.saveAll(newBooks);
        
        // Then
        assertThat(savedBooks).hasSize(2);
        assertThat(bookRepository.count()).isEqualTo(8L);
    }

    @Test
    @DisplayName("Should delete book by ID")
    void shouldDeleteBookById() {
	}

    @Test
    @DisplayName("Should delete all books")
    void shouldDeleteAllBooks() {
        // When
        bookRepository.deleteAll();
        
        // Then
        assertThat(bookRepository.count()).isEqualTo(0L);
    }

    @Test
    @DisplayName("Should delete all books in batch")
    void shouldDeleteAllBooksInBatch() {
        
    }

    @Test
    @DisplayName("Should flush changes to database")
    void shouldFlushChangesToDatabase() {
        // Given
        Book newBook = new Book("Flush Test", "Test Author", "9999999999",
                               "Test Description", new BigDecimal("15.00"), 5, BookCategory.FICTION);
        
        // When
        Book savedBook = bookRepository.save(newBook);
        bookRepository.flush();
        
        // Then
        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());
        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getTitle()).isEqualTo("Flush Test");
    }

    @Test
    @DisplayName("Should handle empty search results")
    void shouldHandleEmptySearchResults() {
        // When
        List<Book> books = bookRepository.searchByTitleOrAuthor("NonExistentText");
        
        // Then
        assertThat(books).isEmpty();
    }

    @Test
    @DisplayName("Should handle case insensitive search with special characters")
    void shouldHandleCaseInsensitiveSearchWithSpecialCharacters() {

    }

    @Test
    @DisplayName("Should handle price boundary conditions")
    void shouldHandlePriceBoundaryConditions() {
        // When
        List<Book> books = bookRepository.findByPriceBetween(
            new BigDecimal("19.99"), new BigDecimal("19.99")
        );
        
        // Then
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getTitle()).isEqualTo("1984");
    }

    @Test
    @DisplayName("Should handle stock boundary conditions")
    void shouldHandleStockBoundaryConditions() {
        // When
        List<Book> books = bookRepository.findByStockGreaterThan(0);
        
        // Then
        assertThat(books).hasSize(5); // All books except outOfStockBook
        assertThat(books).extracting("stock")
            .allMatch(stock -> (Integer) stock > 0);
    }
}
