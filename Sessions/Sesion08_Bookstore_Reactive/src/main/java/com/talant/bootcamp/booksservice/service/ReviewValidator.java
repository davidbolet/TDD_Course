package com.talant.bootcamp.booksservice.service;

import com.talant.bootcamp.booksservice.model.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewValidator {

    public void validate(Review review) {
        if (review.getRating() < 1 || review.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        if (review.getComment() == null || review.getComment().trim().isEmpty()) {
            throw new IllegalArgumentException("Comment must not be empty");
        }
    }
}
