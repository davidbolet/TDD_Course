package com.talant.bootcamp.booksservice.service;

import com.talant.bootcamp.booksservice.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Book Statistics Service Tests")
class BookStatisticsServiceTest {
    
    @Mock
    private BookRepository bookRepository;
    
    @InjectMocks
    private BookStatisticsService bookStatisticsService;
    
    private List<Object[]> categoryStatistics;
    private List<Object[]> averagePriceStatistics;
    
    @BeforeEach
    void setUp() {
        // Mock data for category statistics
        Object[] fictionStats = {"FICTION", 5L};
        Object[] technologyStats = {"TECHNOLOGY", 3L};
        categoryStatistics = Arrays.asList(fictionStats, technologyStats);
        
        // Mock data for average price statistics
        Object[] fictionAvgPrice = {"FICTION", 25.50};
        Object[] technologyAvgPrice = {"TECHNOLOGY", 45.75};
        averagePriceStatistics = Arrays.asList(fictionAvgPrice, technologyAvgPrice);
    }
    
    @Test
    @DisplayName("Should get book statistics by category")
    void shouldGetBookStatisticsByCategory() {
        // Given
        when(bookRepository.countBooksByCategory()).thenReturn(categoryStatistics);
        
        // When
        List<Object[]> result = bookStatisticsService.getBookStatisticsByCategory();
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("FICTION", result.get(0)[0]);
        assertEquals(5L, result.get(0)[1]);
        assertEquals("TECHNOLOGY", result.get(1)[0]);
        assertEquals(3L, result.get(1)[1]);
        verify(bookRepository).countBooksByCategory();
    }
    
    @Test
    @DisplayName("Should get average price by category")
    void shouldGetAveragePriceByCategory() {
        // Given
        when(bookRepository.getAveragePriceByCategory()).thenReturn(averagePriceStatistics);
        
        // When
        List<Object[]> result = bookStatisticsService.getAveragePriceByCategory();
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("FICTION", result.get(0)[0]);
        assertEquals(25.50, result.get(0)[1]);
        assertEquals("TECHNOLOGY", result.get(1)[0]);
        assertEquals(45.75, result.get(1)[1]);
        verify(bookRepository).getAveragePriceByCategory();
    }
    
    @Test
    @DisplayName("Should return empty list when no statistics available")
    void shouldReturnEmptyListWhenNoStatisticsAvailable() {
        // Given
        when(bookRepository.countBooksByCategory()).thenReturn(Arrays.asList());
        
        // When
        List<Object[]> result = bookStatisticsService.getBookStatisticsByCategory();
        
        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bookRepository).countBooksByCategory();
    }
    
    @Test
    @DisplayName("Should return empty list when no average price statistics available")
    void shouldReturnEmptyListWhenNoAveragePriceStatisticsAvailable() {
        // Given
        when(bookRepository.getAveragePriceByCategory()).thenReturn(Arrays.asList());
        
        // When
        List<Object[]> result = bookStatisticsService.getAveragePriceByCategory();
        
        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bookRepository).getAveragePriceByCategory();
    }
    

} 