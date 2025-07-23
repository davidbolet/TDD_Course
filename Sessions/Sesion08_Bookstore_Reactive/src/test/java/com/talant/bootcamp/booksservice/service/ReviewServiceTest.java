package com.talant.bootcamp.booksservice.service;

import com.talant.bootcamp.booksservice.model.Review;
import com.talant.bootcamp.booksservice.repository.ReviewRepository;
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
@DisplayName("Review Service Tests")
class ReviewServiceTest {
    
    @Mock
    private ReviewRepository reviewRepository;
    
    @Mock
    private ReviewValidator reviewValidator;
    
    @InjectMocks
    private ReviewService reviewService;
    
    private Review validReview;
    private Review anotherReview;
    
    @BeforeEach
    void setUp() {
        validReview = new Review(1L, 1L, "user1", 5, "Great book!");
        anotherReview = new Review(2L, 1L, "user2", 4, "Good book, but could be better.");
    }
    

    
    @Test
    @DisplayName("Should get reviews for book")
    void shouldGetReviewsForBook() {
        // Given
        Long bookId = 1L;
        List<Review> expectedReviews = Arrays.asList(validReview, anotherReview);
        when(reviewRepository.findByBookId(bookId)).thenReturn(expectedReviews);
        
        // When
        List<Review> result = reviewService.getReviewsForBook(bookId);
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(validReview, result.get(0));
        assertEquals(anotherReview, result.get(1));
        verify(reviewRepository).findByBookId(bookId);
    }
    
    @Test
    @DisplayName("Should return empty list when no reviews exist for book")
    void shouldReturnEmptyListWhenNoReviewsExistForBook() {
        // Given
        Long bookId = 999L;
        when(reviewRepository.findByBookId(bookId)).thenReturn(Arrays.asList());
        
        // When
        List<Review> result = reviewService.getReviewsForBook(bookId);
        
        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(reviewRepository).findByBookId(bookId);
    }
    
    @Test
    @DisplayName("Should handle multiple reviews for same book")
    void shouldHandleMultipleReviewsForSameBook() {
        // Given
        Long bookId = 1L;
        Review review3 = new Review(3L, bookId, "user3", 3, "Average book");
        List<Review> multipleReviews = Arrays.asList(validReview, anotherReview, review3);
        when(reviewRepository.findByBookId(bookId)).thenReturn(multipleReviews);
        
        // When
        List<Review> result = reviewService.getReviewsForBook(bookId);
        
        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains(validReview));
        assertTrue(result.contains(anotherReview));
        assertTrue(result.contains(review3));
        verify(reviewRepository).findByBookId(bookId);
    }
    
    @Test
    @DisplayName("Should handle review with invalid values")
    void shouldHandleReviewWithInvalidValues() {
        // Given
        Review invalidReview = new Review(1L, 1L, "user1", 6, ""); // Invalid rating and empty comment
        doThrow(new IllegalArgumentException("Invalid review")).when(reviewValidator).validate(invalidReview);
        
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            reviewService.addReview(invalidReview);
        });
        
        verify(reviewValidator).validate(invalidReview);
        verify(reviewRepository, never()).save(any(Review.class));
    }
    

}
