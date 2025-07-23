package com.talant.bootcamp.booksservice.service;

import com.talant.bootcamp.booksservice.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for book statistics operations
 */
@Service
@Transactional(readOnly = true)
public class BookStatisticsService {
    
    private final BookRepository bookRepository;
    
    public BookStatisticsService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    /**
     * Get book statistics by category
     */
    public List<Object[]> getBookStatisticsByCategory() {
        return bookRepository.countBooksByCategory();
    }
    
    /**
     * Get average price by category
     */
    public List<Object[]> getAveragePriceByCategory() {
        return bookRepository.getAveragePriceByCategory();
    }
} 