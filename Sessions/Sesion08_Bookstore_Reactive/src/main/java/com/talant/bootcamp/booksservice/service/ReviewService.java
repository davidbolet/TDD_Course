package com.talant.bootcamp.booksservice.service;

import com.talant.bootcamp.booksservice.model.Review;
import com.talant.bootcamp.booksservice.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewValidator reviewValidator;

    public ReviewService(ReviewRepository reviewRepository, ReviewValidator reviewValidator) {
        this.reviewRepository = reviewRepository;
        this.reviewValidator = reviewValidator;
    }

    public void addReview(Review review) {
        reviewValidator.validate(review);
        reviewRepository.save(review);
    }

    public List<Review> getReviewsForBook(Long bookId) {
        return reviewRepository.findByBookId(bookId);
    }
}
