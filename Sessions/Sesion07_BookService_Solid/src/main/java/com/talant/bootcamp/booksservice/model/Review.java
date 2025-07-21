package com.talant.bootcamp.booksservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "reviews")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long bookId;
	@Column(name = "username")
    private String user;
    private int rating;
    private String comment;

    public Review(Long id, Long bookId, String user, int rating, String comment) {
		this.id = id;
		this.bookId = bookId;
		this.user = user;
		this.rating = rating;
		this.comment = comment;
	}

	public Long getId() {
		return id;
	}

	public Long getBookId() {
		return bookId;
	}

	public String getUser() {
		return user;
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
	public void setUser(String user) {
		this.user = user;
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
				", user='" + user + '\'' +
				", rating=" + rating +
				", comment='" + comment + '\'' +
				'}';
	}
}