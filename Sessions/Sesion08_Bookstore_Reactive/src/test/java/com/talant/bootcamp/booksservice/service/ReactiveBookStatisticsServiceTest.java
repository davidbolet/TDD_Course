package com.talant.bootcamp.booksservice.service;

import com.talant.bootcamp.booksservice.model.Review;
import com.talant.bootcamp.booksservice.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReactiveBookStatisticsServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReactiveBookStatisticsService reactiveBookStatisticsService;

    private Review review1;
    private Review review2;
    private Review review3;
    private Review review4;
    private Review review5;

    @BeforeEach
    void setUp() {
        review1 = new Review(1L, 1L, "user1", 5, "Excellent book!");
        review2 = new Review(2L, 1L, "user2", 4, "Very good book");
        review3 = new Review(3L, 1L, "user3", 3, "Good book");
        review4 = new Review(4L, 1L, "user4", 2, "Average book");
        review5 = new Review(5L, 1L, "user5", 1, "Poor book");
    }

    @Test
    void getAverageRating_WhenBookHasMultipleReviews_ShouldReturnCorrectAverage() {
        // Given
        Long bookId = 1L;
        List<Review> reviews = Arrays.asList(review1, review2, review3, review4, review5);
        when(reviewRepository.findByBookId(bookId)).thenReturn(reviews);

        // Expected average: (5+4+3+2+1)/5 = 3.0
        double expectedAverage = 3.0;

        // When
        Mono<Double> result = reactiveBookStatisticsService.getAverageRating(bookId);

        // Then
        StepVerifier.create(result)
                .expectNext(expectedAverage)
                .verifyComplete();
    }

    @Test
    void getAverageRating_WhenBookHasOneReview_ShouldReturnThatRating() {
        // Given
        Long bookId = 1L;
        List<Review> reviews = Collections.singletonList(review1);
        when(reviewRepository.findByBookId(bookId)).thenReturn(reviews);

        // Expected average: 5.0
        double expectedAverage = 5.0;

        // When
        Mono<Double> result = reactiveBookStatisticsService.getAverageRating(bookId);

        // Then
        StepVerifier.create(result)
                .expectNext(expectedAverage)
                .verifyComplete();
    }

    @Test
    void getAverageRating_WhenBookHasNoReviews_ShouldReturnZero() {
        // Given
        Long bookId = 1L;
        List<Review> reviews = Collections.emptyList();
        when(reviewRepository.findByBookId(bookId)).thenReturn(reviews);

        // Expected average: 0.0 (default when no reviews)
        double expectedAverage = 0.0;

        // When
        Mono<Double> result = reactiveBookStatisticsService.getAverageRating(bookId);

        // Then
        StepVerifier.create(result)
                .expectNext(expectedAverage)
                .verifyComplete();
    }

    @Test
    void getAverageRating_WhenBookHasAllFiveStarReviews_ShouldReturnFive() {
        // Given
        Long bookId = 1L;
        Review fiveStarReview1 = new Review(1L, 1L, "user1", 5, "Perfect!");
        Review fiveStarReview2 = new Review(2L, 1L, "user2", 5, "Amazing!");
        Review fiveStarReview3 = new Review(3L, 1L, "user3", 5, "Outstanding!");
        
        List<Review> reviews = Arrays.asList(fiveStarReview1, fiveStarReview2, fiveStarReview3);
        when(reviewRepository.findByBookId(bookId)).thenReturn(reviews);

        // Expected average: (5+5+5)/3 = 5.0
        double expectedAverage = 5.0;

        // When
        Mono<Double> result = reactiveBookStatisticsService.getAverageRating(bookId);

        // Then
        StepVerifier.create(result)
                .expectNext(expectedAverage)
                .verifyComplete();
    }

    @Test
    void getAverageRating_WhenBookHasAllOneStarReviews_ShouldReturnOne() {
        // Given
        Long bookId = 1L;
        Review oneStarReview1 = new Review(1L, 1L, "user1", 1, "Terrible!");
        Review oneStarReview2 = new Review(2L, 1L, "user2", 1, "Awful!");
        Review oneStarReview3 = new Review(3L, 1L, "user3", 1, "Disappointing!");
        
        List<Review> reviews = Arrays.asList(oneStarReview1, oneStarReview2, oneStarReview3);
        when(reviewRepository.findByBookId(bookId)).thenReturn(reviews);

        // Expected average: (1+1+1)/3 = 1.0
        double expectedAverage = 1.0;

        // When
        Mono<Double> result = reactiveBookStatisticsService.getAverageRating(bookId);

        // Then
        StepVerifier.create(result)
                .expectNext(expectedAverage)
                .verifyComplete();
    }

    @Test
    void getAverageRating_WhenBookHasMixedRatings_ShouldReturnCorrectAverage() {
        // Given
        Long bookId = 1L;
        Review review1 = new Review(1L, 1L, "user1", 5, "Excellent!");
        Review review2 = new Review(2L, 1L, "user2", 3, "Good");
        Review review3 = new Review(3L, 1L, "user3", 4, "Very good");
        Review review4 = new Review(4L, 1L, "user4", 2, "Average");
        Review review5 = new Review(5L, 1L, "user5", 5, "Perfect!");
        
        List<Review> reviews = Arrays.asList(review1, review2, review3, review4, review5);
        when(reviewRepository.findByBookId(bookId)).thenReturn(reviews);

        // Expected average: (5+3+4+2+5)/5 = 3.8
        double expectedAverage = 3.8;

        // When
        Mono<Double> result = reactiveBookStatisticsService.getAverageRating(bookId);

        // Then
        StepVerifier.create(result)
                .expectNext(expectedAverage)
                .verifyComplete();
    }

    @Test
    void getAverageRating_WhenBookHasDecimalAverage_ShouldReturnCorrectDecimal() {
        // Given
        Long bookId = 1L;
        Review review1 = new Review(1L, 1L, "user1", 4, "Good");
        Review review2 = new Review(2L, 1L, "user2", 3, "Average");
        Review review3 = new Review(3L, 1L, "user3", 5, "Excellent");
        
        List<Review> reviews = Arrays.asList(review1, review2, review3);
        when(reviewRepository.findByBookId(bookId)).thenReturn(reviews);

        // Expected average: (4+3+5)/3 = 4.0
        double expectedAverage = 4.0;

        // When
        Mono<Double> result = reactiveBookStatisticsService.getAverageRating(bookId);

        // Then
        StepVerifier.create(result)
                .expectNext(expectedAverage)
                .verifyComplete();
    }

    @Test
    void getAverageRating_WhenBookHasManyReviews_ShouldReturnCorrectAverage() {
        // Given
        Long bookId = 1L;
        List<Review> reviews = Arrays.asList(
            new Review(1L, 1L, "user1", 5, "Great!"),
            new Review(2L, 1L, "user2", 4, "Good"),
            new Review(3L, 1L, "user3", 5, "Excellent"),
            new Review(4L, 1L, "user4", 3, "Average"),
            new Review(5L, 1L, "user5", 4, "Very good"),
            new Review(6L, 1L, "user6", 5, "Perfect"),
            new Review(7L, 1L, "user7", 2, "Poor"),
            new Review(8L, 1L, "user8", 4, "Good"),
            new Review(9L, 1L, "user9", 5, "Amazing"),
            new Review(10L, 1L, "user10", 3, "Okay")
        );
        when(reviewRepository.findByBookId(bookId)).thenReturn(reviews);

        // Expected average: (5+4+5+3+4+5+2+4+5+3)/10 = 4.0
        double expectedAverage = 4.0;

        // When
        Mono<Double> result = reactiveBookStatisticsService.getAverageRating(bookId);

        // Then
        StepVerifier.create(result)
                .expectNext(expectedAverage)
                .verifyComplete();
    }



    @Test
    void getAverageRating_WhenBookIdIsNegative_ShouldReturnZero() {
        // Given
        Long bookId = -1L;
        when(reviewRepository.findByBookId(bookId)).thenReturn(Collections.emptyList());

        // Expected average: 0.0
        double expectedAverage = 0.0;

        // When
        Mono<Double> result = reactiveBookStatisticsService.getAverageRating(bookId);

        // Then
        StepVerifier.create(result)
                .expectNext(expectedAverage)
                .verifyComplete();
    }

    @Test
    void getAverageRating_WhenBookIdIsZero_ShouldReturnZero() {
        // Given
        Long bookId = 0L;
        when(reviewRepository.findByBookId(bookId)).thenReturn(Collections.emptyList());

        // Expected average: 0.0
        double expectedAverage = 0.0;

        // When
        Mono<Double> result = reactiveBookStatisticsService.getAverageRating(bookId);

        // Then
        StepVerifier.create(result)
                .expectNext(expectedAverage)
                .verifyComplete();
    }

    @Test
    void getAverageRating_WhenReviewsHaveDifferentBookIds_ShouldOnlyConsiderMatchingBookId() {
        // Given
        Long bookId = 1L;
        Review reviewForBook1 = new Review(1L, 1L, "user1", 5, "Great book 1!");
        Review reviewForBook2 = new Review(2L, 2L, "user2", 3, "Average book 2");
        Review reviewForBook1Again = new Review(3L, 1L, "user3", 4, "Good book 1");
        
        List<Review> reviews = Arrays.asList(reviewForBook1, reviewForBook2, reviewForBook1Again);
        when(reviewRepository.findByBookId(bookId)).thenReturn(Arrays.asList(reviewForBook1, reviewForBook1Again));

        // Expected average: (5+4)/2 = 4.5
        double expectedAverage = 4.5;

        // When
        Mono<Double> result = reactiveBookStatisticsService.getAverageRating(bookId);

        // Then
        StepVerifier.create(result)
                .expectNext(expectedAverage)
                .verifyComplete();
    }

    @Test
    void getAverageRating_WhenBookIdIsNull_ShouldThrowException() {
        // Given
        Long bookId = null;

        // When & Then
        StepVerifier.create(reactiveBookStatisticsService.getAverageRating(bookId))
                .expectError(IllegalArgumentException.class)
                .verify();
    }
} 