package com.talant.bootcamp.booksservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookId;

    @Column(name = "username")
    @NotBlank
    private String username;

    @Min(1)
    @Max(5)
    private int rating;

    @Size(max = 255)
    private String comment;

    public Review() {
        // Default constructor required by JPA
    }

    public Review(Long id, Long bookId, String username, int rating, String comment) {
        this.id = id;
        this.bookId = bookId;
        this.username = username;
        this.rating = rating;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public Long getBookId() {
        return bookId;
    }

    public String getUsername() {
        return username;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isValid() {
        return rating >= 1 && rating <= 5 && comment != null && !comment.isEmpty();
    }

    @Override
    public String toString() {
        return "Review{" +
               "id=" + id +
               ", bookId=" + bookId +
               ", username='" + username + "\'" +
               ", rating=" + rating +
               ", comment='" + comment + "\'" +
               '}';
    }
}
