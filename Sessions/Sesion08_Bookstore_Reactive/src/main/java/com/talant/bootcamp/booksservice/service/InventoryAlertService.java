package com.talant.bootcamp.booksservice.service;

import com.talant.bootcamp.booksservice.model.Book;
import com.talant.bootcamp.booksservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for inventory alert operations
 */
@Service
public class InventoryAlertService {

    public static final int DEFAULT_LOW_STOCK_THRESHOLD = 5;
    
    private final BookRepository bookRepository;
    private final int lowStockThreshold;

    public InventoryAlertService(BookRepository bookRepository, 
                               @Value("${book.inventory.low-stock-threshold:" + DEFAULT_LOW_STOCK_THRESHOLD + "}") int lowStockThreshold) {
        this.bookRepository = bookRepository;
        this.lowStockThreshold = lowStockThreshold;
    }

    /**
     * Get books with low stock
     */
    public List<Book> getBooksWithLowStock() {
        return bookRepository.findAll().stream()
                .filter(book -> book.getStock() < lowStockThreshold)
                .collect(Collectors.toList());
    }
    
    /**
     * Get books with low stock as BookResponse
     */
    public List<com.talant.bootcamp.booksservice.dto.BookResponse> getBooksWithLowStockAsResponse() {
        return getBooksWithLowStock().stream()
                .map(com.talant.bootcamp.booksservice.dto.BookResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Check if a book has low stock
     */
    public boolean hasLowStock(Book book) {
        return book.getStock() < lowStockThreshold;
    }
    
    /**
     * Get the current low stock threshold
     */
    public int getLowStockThreshold() {
        return lowStockThreshold;
    }
}
