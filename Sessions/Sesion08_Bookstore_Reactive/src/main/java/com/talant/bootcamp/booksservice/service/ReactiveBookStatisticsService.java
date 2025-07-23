package com.talant.bootcamp.booksservice.service;

import com.talant.bootcamp.booksservice.model.Review;
import com.talant.bootcamp.booksservice.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ReactiveBookStatisticsService {

    private final ReviewRepository reviewRepository;

    public ReactiveBookStatisticsService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Mono<Double> getAverageRating(Long bookId) {
        if (bookId == null) {
            return Mono.error(new IllegalArgumentException("Book ID cannot be null"));
        }
        List<Review> reviews = reviewRepository.findByBookId(bookId);
        return Flux.fromIterable(reviews)
                   .map(Review::getRating)
                   .collectList()
                   .map(list -> list.stream().mapToInt(Integer::intValue).average().orElse(0));
    }
}
