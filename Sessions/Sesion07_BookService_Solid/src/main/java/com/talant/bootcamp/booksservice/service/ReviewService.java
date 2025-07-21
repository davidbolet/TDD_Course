package com.talant.bootcamp.booksservice.service;

import com.talant.bootcamp.booksservice.model.Review;
import com.talant.bootcamp.booksservice.repository.ReviewRepository;
import java.util.List;

public class ReviewService {
    private ReviewRepository reviewRepo = new ReviewRepository(); 

    public void addReview(Review review) {
        if (review.getRating() < 1 || review.getRating() > 5) {
            throw new IllegalArgumentException("Invalid rating");
        }
		if (review.isValid() == false) {
			throw new IllegalArgumentException("Invalid review");
		}
		if (review.getComment() == null || review.getComment().isEmpty()) {
			throw new IllegalArgumentException("Invalid comment");
		}
        reviewRepo.save(review);
    }

    public List<Review> getReviewsForBook(Long bookId) {
        return reviewRepo.findByBookId(bookId);
    }
}
