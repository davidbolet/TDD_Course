package com.talant.bootcamp.booksservice.controller;

import com.talant.bootcamp.booksservice.dto.BookRequest;
import com.talant.bootcamp.booksservice.dto.BookResponse;
import com.talant.bootcamp.booksservice.model.BookCategory;
import com.talant.bootcamp.booksservice.service.BookService;
import com.talant.bootcamp.booksservice.service.BookSearchService;
import com.talant.bootcamp.booksservice.service.BookStatisticsService;
import com.talant.bootcamp.booksservice.service.InventoryAlertService;
import com.talant.bootcamp.booksservice.service.ReactiveBookOrderService;
import com.talant.bootcamp.booksservice.service.ReactiveBookSearchService;
import com.talant.bootcamp.booksservice.service.ReactiveBookStatisticsService;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    private final BookSearchService bookSearchService;
    private final BookStatisticsService bookStatisticsService;
    private final InventoryAlertService inventoryAlertService;
    private final ReactiveBookSearchService reactiveBookSearchService;
	private final ReactiveBookOrderService reactiveBookOrderService;
	private final ReactiveBookStatisticsService reactiveBookStatisticsService;
    
    @Autowired
    public BookController(BookService bookService, 
                         BookSearchService bookSearchService,
                         BookStatisticsService bookStatisticsService,
                         InventoryAlertService inventoryAlertService, ReactiveBookSearchService reactiveBookSearchService, 
						 ReactiveBookOrderService reactiveBookOrderService, 
						 ReactiveBookStatisticsService reactiveBookStatisticsService) {
		this.reactiveBookStatisticsService = reactiveBookStatisticsService;
        this.bookService = bookService;
        this.bookSearchService = bookSearchService;
        this.bookStatisticsService = bookStatisticsService;
        this.inventoryAlertService = inventoryAlertService;
		this.reactiveBookSearchService = reactiveBookSearchService;
		this.reactiveBookOrderService = reactiveBookOrderService;
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
        List<BookResponse> books = bookSearchService.getBooksByAuthor(author);
        return ResponseEntity.ok(books);
    }
    
    /**
     * Search books by title
     */
    @GetMapping("/title/{title}")
    public ResponseEntity<List<BookResponse>> getBooksByTitle(@PathVariable String title) {
        List<BookResponse> books = bookSearchService.getBooksByTitle(title);
        return ResponseEntity.ok(books);
    }
    
    /**
     * Search books by category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<BookResponse>> getBooksByCategory(@PathVariable BookCategory category) {
        List<BookResponse> books = bookSearchService.getBooksByCategory(category);
        return ResponseEntity.ok(books);
    }
    
    /**
     * Search books with stock available
     */
    @GetMapping("/in-stock")
    public ResponseEntity<List<BookResponse>> getBooksWithStock() {
        List<BookResponse> books = bookSearchService.getBooksWithStock();
        return ResponseEntity.ok(books);
    }
    
    /**
     * Search books without stock
     */
    @GetMapping("/out-of-stock")
    public ResponseEntity<List<BookResponse>> getBooksOutOfStock() {
        List<BookResponse> books = bookSearchService.getBooksOutOfStock();
        return ResponseEntity.ok(books);
    }
    
    /**
     * Search books by price range
     */
    @GetMapping("/price-range")
    public ResponseEntity<List<BookResponse>> getBooksByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        List<BookResponse> books = bookSearchService.getBooksByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(books);
    }
    
    /**
     * Search books by maximum price
     */
    @GetMapping("/max-price/{maxPrice}")
    public ResponseEntity<List<BookResponse>> getBooksByMaxPrice(@PathVariable BigDecimal maxPrice) {
        List<BookResponse> books = bookSearchService.getBooksByMaxPrice(maxPrice);
        return ResponseEntity.ok(books);
    }
    
    /**
     * Search books by minimum price
     */
    @GetMapping("/min-price/{minPrice}")
    public ResponseEntity<List<BookResponse>> getBooksByMinPrice(@PathVariable BigDecimal minPrice) {
        List<BookResponse> books = bookSearchService.getBooksByMinPrice(minPrice);
        return ResponseEntity.ok(books);
    }
    
    /**
     * Search books with low stock
     */
    @GetMapping("/low-stock")
    public ResponseEntity<List<BookResponse>> getBooksWithLowStock() {
        List<BookResponse> books = inventoryAlertService.getBooksWithLowStockAsResponse();
        return ResponseEntity.ok(books);
    }
    
    /**
     * Search books by text in title or author
     */
    @GetMapping("/search")
    public ResponseEntity<List<BookResponse>> searchBooks(@RequestParam String q) {
        List<BookResponse> books = bookSearchService.searchBooks(q);
        return ResponseEntity.ok(books);
    }
    

	/**
	 * Search books by title using reactive approach
	 * @param keyword
	 * @return
	 */
	@GetMapping("/search/reactive")
    public Flux<BookResponse> searchBooksReactive(@RequestParam String keyword) {
    return reactiveBookSearchService.findByTitleContains(keyword)
        .flatMap(book -> 
            Mono.zip(
                reactiveBookOrderService.checkStock(book.getId()),
                reactiveBookStatisticsService.getAverageRating(book.getId())
            ).map(tuple -> new BookResponse(book, tuple.getT1(), tuple.getT2()))
        );
	}

    /**
     * Get books ordered by price ascending
     */
    @GetMapping("/sorted/price-asc")
    public ResponseEntity<List<BookResponse>> getBooksOrderedByPriceAsc() {
        List<BookResponse> books = bookSearchService.getBooksOrderedByPriceAsc();
        return ResponseEntity.ok(books);
    }
    
    /**
     * Get books ordered by price descending
     */
    @GetMapping("/sorted/price-desc")
    public ResponseEntity<List<BookResponse>> getBooksOrderedByPriceDesc() {
        List<BookResponse> books = bookSearchService.getBooksOrderedByPriceDesc();
        return ResponseEntity.ok(books);
    }
    
    /**
     * Get books ordered by title
     */
    @GetMapping("/sorted/title")
    public ResponseEntity<List<BookResponse>> getBooksOrderedByTitle() {
        List<BookResponse> books = bookSearchService.getBooksOrderedByTitle();
        return ResponseEntity.ok(books);
    }
    
    /**
     * Get books ordered by author
     */
    @GetMapping("/sorted/author")
    public ResponseEntity<List<BookResponse>> getBooksOrderedByAuthor() {
        List<BookResponse> books = bookSearchService.getBooksOrderedByAuthor();
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
        List<Object[]> statistics = bookStatisticsService.getBookStatisticsByCategory();
        return ResponseEntity.ok(statistics);
    }
    
    /**
     * Get average price by category
     */
    @GetMapping("/statistics/average-price")
    public ResponseEntity<List<Object[]>> getAveragePriceByCategory() {
        List<Object[]> averagePrices = bookStatisticsService.getAveragePriceByCategory();
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