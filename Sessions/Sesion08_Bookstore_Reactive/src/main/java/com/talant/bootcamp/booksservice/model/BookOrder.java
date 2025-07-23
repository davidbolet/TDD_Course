package com.talant.bootcamp.booksservice.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "book_orders")
public class BookOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bookId;
    private int quantity;
    private LocalDate orderDate;

    public double calculateTotal(double price) {
        return price * quantity; 
    }

	public Long getId() {
		return id;
	}	
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public LocalDate getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public BookOrder(Long id, Long bookId, int quantity, LocalDate orderDate) {
		this.id = id;
		this.bookId = bookId;
		this.quantity = quantity;
		this.orderDate = orderDate;
	}

	public BookOrder() {
		// Default constructor
	}

	@Override
	public String toString() {
		return "BookOrder{" +
				"id=" + id +
				", bookId=" + bookId +
				", quantity=" + quantity +
				", orderDate=" + orderDate +
				'}';
	}
}
