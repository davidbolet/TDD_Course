package com.talant.bootcamp.booksservice.model;

import java.time.LocalDate;

public class BookOrder {
    private Long id;
    private Long bookId;
    private int quantity;
    private LocalDate orderDate;

    public double calculateTotal(double price) {
        return price * quantity; // logic here instead of service
    }

    // Getters and Setters omitted
}
