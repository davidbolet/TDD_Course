package com.talant.bootcamp.booksservice.controller;

import com.talant.bootcamp.booksservice.dto.BookRequest;
import com.talant.bootcamp.booksservice.dto.BookResponse;
import com.talant.bootcamp.booksservice.model.BookCategory;
import com.talant.bootcamp.booksservice.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {
    
    private final BookService bookService;
    
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    
    /**
     * Create a new book
     */
    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest bookRequest) {
        BookResponse createdBook = bookService.createBook(bookRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }
    
    /**
     * Get all books
     */
    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        List<BookResponse> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }
    
    /**
     * Get book by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        BookResponse book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }
    
    /**
     * Get book by ISBN
     */
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookResponse> getBookByIsbn(@PathVariable String isbn) {
        BookResponse book = bookService.getBookByIsbn(isbn);
        return ResponseEntity.ok(book);
    }
    
    /**
     * Update book
     */
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id, 
                                                  @Valid @RequestBody BookRequest bookRequest) {
        BookResponse updatedBook = bookService.updateBook(id, bookRequest);
        return ResponseEntity.ok(updatedBook);
    }
    
    /**
     * Delete book
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Search books by author
     */
    @GetMapping("/author/{author}")
    public ResponseEntity<List<BookResponse>> getBooksByAuthor(@PathVariable String author) {
        List<BookResponse> books = bookService.getBooksByAuthor(author);
        return ResponseEntity.ok(books);
    }
    
    /**
     * Search books by title
     */
    @GetMapping("/title/{title}")
    public ResponseEntity<List<BookResponse>> getBooksByTitle(@PathVariable String title) {
        List<BookResponse> books = bookService.getBooksByTitle(title);
        return ResponseEntity.ok(books);
    }
    
    /**
     * Search books by category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<BookResponse>> getBooksByCategory(@PathVariable BookCategory category) {
        List<BookResponse> books = bookService.getBooksByCategory(category);
        return ResponseEntity.ok(books);
    }
    
    /**
     * Search books with stock available
     */
    @GetMapping("/in-stock")
    public ResponseEntity<List<BookResponse>> getBooksWithStock() {
        List<BookResponse> books = bookService.getBooksWithStock();
        return ResponseEntity.ok(books);
    }
    
    /**
     * Search books without stock
     */
    @GetMapping("/out-of-stock")
    public ResponseEntity<List<BookResponse>> getBooksOutOfStock() {
        List<BookResponse> books = bookService.getBooksOutOfStock();
        return ResponseEntity.ok(books);
    }
    
    /**
     * Search books by price range
     */
    @GetMapping("/price-range")
    public ResponseEntity<List<BookResponse>> getBooksByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        List<BookResponse> books = bookService.getBooksByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(books);
    }
    
    /**
     * Search books by maximum price
     */
    @GetMapping("/max-price/{maxPrice}")
    public ResponseEntity<List<BookResponse>> getBooksByMaxPrice(@PathVariable BigDecimal maxPrice) {
        List<BookResponse> books = bookService.getBooksByMaxPrice(maxPrice);
        return ResponseEntity.ok(books);
    }
    
    /**
     * Search books by minimum price
     */
    @GetMapping("/min-price/{minPrice}")
    public ResponseEntity<List<BookResponse>> getBooksByMinPrice(@PathVariable BigDecimal minPrice) {
        List<BookResponse> books = bookService.getBooksByMinPrice(minPrice);
        return ResponseEntity.ok(books);
    }
    
    /**
     * Search books with low stock
     */
    @GetMapping("/low-stock")
    public ResponseEntity<List<BookResponse>> getBooksWithLowStock() {
        List<BookResponse> books = bookService.getBooksWithLowStock();
        return ResponseEntity.ok(books);
    }
    
    /**
     * Search books by text in title or author
     */
    @GetMapping("/search")
    public ResponseEntity<List<BookResponse>> searchBooks(@RequestParam String q) {
        List<BookResponse> books = bookService.searchBooks(q);
        return ResponseEntity.ok(books);
    }
    
    /**
     * Get books ordered by price ascending
     */
    @GetMapping("/sorted/price-asc")
    public ResponseEntity<List<BookResponse>> getBooksOrderedByPriceAsc() {
        List<BookResponse> books = bookService.getBooksOrderedByPriceAsc();
        return ResponseEntity.ok(books);
    }
    
    /**
     * Get books ordered by price descending
     */
    @GetMapping("/sorted/price-desc")
    public ResponseEntity<List<BookResponse>> getBooksOrderedByPriceDesc() {
        List<BookResponse> books = bookService.getBooksOrderedByPriceDesc();
        return ResponseEntity.ok(books);
    }
    
    /**
     * Get books ordered by title
     */
    @GetMapping("/sorted/title")
    public ResponseEntity<List<BookResponse>> getBooksOrderedByTitle() {
        List<BookResponse> books = bookService.getBooksOrderedByTitle();
        return ResponseEntity.ok(books);
    }
    
    /**
     * Get books ordered by author
     */
    @GetMapping("/sorted/author")
    public ResponseEntity<List<BookResponse>> getBooksOrderedByAuthor() {
        List<BookResponse> books = bookService.getBooksOrderedByAuthor();
        return ResponseEntity.ok(books);
    }
    
    /**
     * Update stock of a book
     */
    @PatchMapping("/{id}/stock")
    public ResponseEntity<BookResponse> updateStock(@PathVariable Long id, 
                                                   @RequestParam Integer stock) {
        BookResponse updatedBook = bookService.updateStock(id, stock);
        return ResponseEntity.ok(updatedBook);
    }
    
    /**
     * Check if a book exists with the given ISBN
     */
    @GetMapping("/exists/{isbn}")
    public ResponseEntity<Boolean> existsByIsbn(@PathVariable String isbn) {
        boolean exists = bookService.existsByIsbn(isbn);
        return ResponseEntity.ok(exists);
    }
    
    /**
     * Get book statistics by category
     */
    @GetMapping("/statistics/category")
    public ResponseEntity<List<Object[]>> getBookStatisticsByCategory() {
        List<Object[]> statistics = bookService.getBookStatisticsByCategory();
        return ResponseEntity.ok(statistics);
    }
    
    /**
     * Get average price by category
     */
    @GetMapping("/statistics/average-price")
    public ResponseEntity<List<Object[]>> getAveragePriceByCategory() {
        List<Object[]> averagePrices = bookService.getAveragePriceByCategory();
        return ResponseEntity.ok(averagePrices);
    }
    
    /**
     * Get all available categories
     */
    @GetMapping("/categories")
    public ResponseEntity<BookCategory[]> getCategories() {
        BookCategory[] categories = BookCategory.values();
        return ResponseEntity.ok(categories);
    }
} 