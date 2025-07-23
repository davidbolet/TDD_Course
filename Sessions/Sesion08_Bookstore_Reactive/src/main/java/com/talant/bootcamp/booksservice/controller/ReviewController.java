package com.talant.bootcamp.booksservice.controller;

import com.talant.bootcamp.booksservice.model.Review;
import com.talant.bootcamp.booksservice.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public void addReview(@RequestBody Review review) {
        reviewService.addReview(review);
    }

    @GetMapping("/{bookId}")
    public List<Review> getReviews(@PathVariable Long bookId) {
        return reviewService.getReviewsForBook(bookId);
    }
}
