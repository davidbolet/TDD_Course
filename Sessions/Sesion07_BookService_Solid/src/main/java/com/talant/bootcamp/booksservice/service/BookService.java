package com.talant.bootcamp.booksservice.service;

import com.talant.bootcamp.booksservice.dto.BookRequest;
import com.talant.bootcamp.booksservice.dto.BookResponse;
import com.talant.bootcamp.booksservice.exception.BookNotFoundException;
import com.talant.bootcamp.booksservice.exception.DuplicateIsbnException;
import com.talant.bootcamp.booksservice.model.Book;
import com.talant.bootcamp.booksservice.model.BookCategory;
import com.talant.bootcamp.booksservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for the Book model
 */
@Service
@Transactional
public class BookService {
    
    private final BookRepository bookRepository;
    
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    /**
     * Create a new book
     */
    public BookResponse createBook(BookRequest bookRequest) {
        // Check if a book with the same ISBN already exists
        if (bookRepository.existsByIsbn(bookRequest.getIsbn())) {
            throw new DuplicateIsbnException(bookRequest.getIsbn());
        }
        
        Book book = new Book(
            bookRequest.getTitle(),
            bookRequest.getAuthor(),
            bookRequest.getIsbn(),
            bookRequest.getDescription(),
            bookRequest.getPrice(),
            bookRequest.getStock(),
            bookRequest.getCategory()
        );
        
        Book savedBook = bookRepository.save(book);
        return new BookResponse(savedBook);
    }
    
    /**
     * Get all books
     */
    @Transactional(readOnly = true)
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Get a book by ID
     */
    @Transactional(readOnly = true)
    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return new BookResponse(book);
    }
    
    /**
     * Get a book by ISBN
     */
    @Transactional(readOnly = true)
    public BookResponse getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("ISBN", isbn));
        return new BookResponse(book);
    }
    
    /**
     * Update a book
     */
    public BookResponse updateBook(Long id, BookRequest bookRequest) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        
        // Check if the new ISBN already exists in another book
        Optional<Book> bookWithSameIsbn = bookRepository.findByIsbn(bookRequest.getIsbn());
        if (bookWithSameIsbn.isPresent() && !bookWithSameIsbn.get().getId().equals(id)) {
            throw new DuplicateIsbnException(bookRequest.getIsbn());
        }
        
        // Update fields
        existingBook.setTitle(bookRequest.getTitle());
        existingBook.setAuthor(bookRequest.getAuthor());
        existingBook.setIsbn(bookRequest.getIsbn());
        existingBook.setDescription(bookRequest.getDescription());
        existingBook.setPrice(bookRequest.getPrice());
        existingBook.setStock(bookRequest.getStock());
        existingBook.setCategory(bookRequest.getCategory());
        
        Book updatedBook = bookRepository.save(existingBook);
        return new BookResponse(updatedBook);
    }
    
    /**
     * Delete a book
     */
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
    }
    
    /**
     * Find books by author
     */
    @Transactional(readOnly = true)
    public List<BookResponse> getBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author)
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
    * Find books by title
     */
    @Transactional(readOnly = true)
    public List<BookResponse> getBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Find books by category
     */
    @Transactional(readOnly = true)
    public List<BookResponse> getBooksByCategory(BookCategory category) {
        return bookRepository.findByCategory(category)
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Find books with stock available
     */
    @Transactional(readOnly = true)
    public List<BookResponse> getBooksWithStock() {
        return bookRepository.findByStockGreaterThan(0)
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Find books without stock
     */
    @Transactional(readOnly = true)
    public List<BookResponse> getBooksOutOfStock() {
        return bookRepository.findByStockEquals(0)
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Find books by price range
     */
    @Transactional(readOnly = true)
    public List<BookResponse> getBooksByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return bookRepository.findByPriceBetween(minPrice, maxPrice)
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Find books by maximum price
     */
    @Transactional(readOnly = true)
    public List<BookResponse> getBooksByMaxPrice(BigDecimal maxPrice) {
        return bookRepository.findByPriceLessThanEqual(maxPrice)
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Find books by minimum price
     */
    @Transactional(readOnly = true)
    public List<BookResponse> getBooksByMinPrice(BigDecimal minPrice) {
        return bookRepository.findByPriceGreaterThanEqual(minPrice)
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Find books with low stock
     */
    @Transactional(readOnly = true)
    public List<BookResponse> getBooksWithLowStock() {
        return bookRepository.findBooksWithLowStock()
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Find books by text in title or author
     */
    @Transactional(readOnly = true)
    public List<BookResponse> searchBooks(String searchTerm) {
        return bookRepository.searchByTitleOrAuthor(searchTerm)
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Find books ordered by price ascending
     */
    @Transactional(readOnly = true)
    public List<BookResponse> getBooksOrderedByPriceAsc() {
        return bookRepository.findAllByOrderByPriceAsc()
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Find books ordered by price descending
     */
    @Transactional(readOnly = true)
    public List<BookResponse> getBooksOrderedByPriceDesc() {
        return bookRepository.findAllByOrderByPriceDesc()
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Find books ordered by title
     */
    @Transactional(readOnly = true)
    public List<BookResponse> getBooksOrderedByTitle() {
        return bookRepository.findAllByOrderByTitleAsc()
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Find books ordered by author
     */
    @Transactional(readOnly = true)
    public List<BookResponse> getBooksOrderedByAuthor() {
        return bookRepository.findAllByOrderByAuthorAsc()
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Update stock of a book
     */
    public BookResponse updateStock(Long id, Integer newStock) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        
        book.setStock(newStock);
        Book updatedBook = bookRepository.save(book);
        return new BookResponse(updatedBook);
    }
    
    /**
     * Check if a book exists by ISBN
     */
    @Transactional(readOnly = true)
    public boolean existsByIsbn(String isbn) {
        return bookRepository.existsByIsbn(isbn);
    }
    
    /**
     * Get book statistics by category
     */
    @Transactional(readOnly = true)
    public List<Object[]> getBookStatisticsByCategory() {
        return bookRepository.countBooksByCategory();
    }
    
    /**
     * Get average price by category
     */
    @Transactional(readOnly = true)
    public List<Object[]> getAveragePriceByCategory() {
        return bookRepository.getAveragePriceByCategory();
    }
} 