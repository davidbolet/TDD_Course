package com.talant.bootcamp.booksservice.service;

import com.talant.bootcamp.booksservice.dto.BookResponse;
import com.talant.bootcamp.booksservice.model.Book;
import com.talant.bootcamp.booksservice.model.BookCategory;
import com.talant.bootcamp.booksservice.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for book search operations
 */
@Service
@Transactional(readOnly = true)
public class BookSearchService {
    
    private final BookRepository bookRepository;
    
    public BookSearchService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    /**
     * Find books by author
     */
    public List<BookResponse> getBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author)
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Find books by title
     */
    public List<BookResponse> getBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Find books by category
     */
    public List<BookResponse> getBooksByCategory(BookCategory category) {
        return bookRepository.findByCategory(category)
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Find books with stock available
     */
    public List<BookResponse> getBooksWithStock() {
        return bookRepository.findByStockGreaterThan(0)
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Find books without stock
     */
    public List<BookResponse> getBooksOutOfStock() {
        return bookRepository.findByStockEquals(0)
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Find books by price range
     */
    public List<BookResponse> getBooksByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return bookRepository.findByPriceBetween(minPrice, maxPrice)
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Find books by maximum price
     */
    public List<BookResponse> getBooksByMaxPrice(BigDecimal maxPrice) {
        return bookRepository.findByPriceLessThanEqual(maxPrice)
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Find books by minimum price
     */
    public List<BookResponse> getBooksByMinPrice(BigDecimal minPrice) {
        return bookRepository.findByPriceGreaterThanEqual(minPrice)
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Find books with low stock
     */
    public List<BookResponse> getBooksWithLowStock() {
        return bookRepository.findBooksWithLowStock()
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Find books by text in title or author
     */
    public List<BookResponse> searchBooks(String searchTerm) {
        return bookRepository.searchByTitleOrAuthor(searchTerm)
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Find books ordered by price ascending
     */
    public List<BookResponse> getBooksOrderedByPriceAsc() {
        return bookRepository.findAllByOrderByPriceAsc()
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Find books ordered by price descending
     */
    public List<BookResponse> getBooksOrderedByPriceDesc() {
        return bookRepository.findAllByOrderByPriceDesc()
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Find books ordered by title
     */
    public List<BookResponse> getBooksOrderedByTitle() {
        return bookRepository.findAllByOrderByTitleAsc()
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Find books ordered by author
     */
    public List<BookResponse> getBooksOrderedByAuthor() {
        return bookRepository.findAllByOrderByAuthorAsc()
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
} 