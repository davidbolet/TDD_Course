package com.talant.bootcamp.booksservice.repository;

import com.talant.bootcamp.booksservice.model.Review;
import java.util.List;
import java.util.ArrayList;

public class ReviewRepository {
    private List<Review> reviews = new ArrayList<>();

    public void save(Review review) {
        reviews.add(review);
    }

    public List<Review> findByBookId(Long bookId) {
        return reviews.stream().filter(r -> r.getBookId().equals(bookId)).toList();
    }
}
