package com.talant.bootcamp.booksservice.repository;

import com.talant.bootcamp.booksservice.model.Book;
import com.talant.bootcamp.booksservice.model.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for the Book model
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    /**
     * Find a book by ISBN
     */
    Optional<Book> findByIsbn(String isbn);
    
    /**
     * Find books by author
     */
    List<Book> findByAuthorContainingIgnoreCase(String author);
    
    /**
     * Find books by title
     */
    List<Book> findByTitleContainingIgnoreCase(String title);
    
    /**
     * Find books by category
     */
    List<Book> findByCategory(BookCategory category);
    
    /**
     * Find books with stock available
     */
    List<Book> findByStockGreaterThan(Integer stock);
    
    /**
     * Find books without stock
     */
    List<Book> findByStockEquals(Integer stock);
    
    /**
     * Find books by price range
     */
    List<Book> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    /**
     * Find books by maximum price
     */
    List<Book> findByPriceLessThanEqual(BigDecimal maxPrice);
    
    /**
     * Find books by minimum price
     */
    List<Book> findByPriceGreaterThanEqual(BigDecimal minPrice);
    
    /**
    * Find books by author and category
     */
    List<Book> findByAuthorContainingIgnoreCaseAndCategory(String author, BookCategory category);
    
    /**
     * Find books by title and author
     */
    List<Book> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(String title, String author);
    
    /**
     * Find books with low stock (less than 10 units)
     */
    @Query("SELECT b FROM Book b WHERE b.stock < 10")
    List<Book> findBooksWithLowStock();
    
    /**
     * Find books by text in title or author
     */
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(b.author) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Book> searchByTitleOrAuthor(@Param("searchTerm") String searchTerm);
    
    /**
     * Count books by category
     */
    @Query("SELECT b.category, COUNT(b) FROM Book b GROUP BY b.category")
    List<Object[]> countBooksByCategory();
    
    /**
     * Get average price by category
     */
    @Query("SELECT b.category, AVG(b.price) FROM Book b GROUP BY b.category")
    List<Object[]> getAveragePriceByCategory();
    
    /**
     * Check if a book exists with the given ISBN
     */
    boolean existsByIsbn(String isbn);
    
    /**
     * Find books ordered by price ascending
     */
    List<Book> findAllByOrderByPriceAsc();
    
    /**
     * Find books ordered by price descending
     */
    List<Book> findAllByOrderByPriceDesc();
    
    /**
     * Find books ordered by title
     */
    List<Book> findAllByOrderByTitleAsc();
    
    /**
     * Find books ordered by author
     */
    List<Book> findAllByOrderByAuthorAsc();
} 