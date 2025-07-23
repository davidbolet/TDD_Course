package com.talant.bootcamp.booksservice.dto;

import com.talant.bootcamp.booksservice.model.BookCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for book notifications received via Kafka
 */
public class BookNotification {
    
    @JsonProperty("isbn")
    private String isbn;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("author")
    private String author;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("price")
    private BigDecimal price;
    
    @JsonProperty("stock")
    private Integer stock;
    
    @JsonProperty("category")
    private BookCategory category;
    
    @JsonProperty("notification_type")
    private NotificationType notificationType;
    
    @JsonProperty("timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    
    public enum NotificationType {
        NEW_BOOK,
        STOCK_UPDATE,
        BOOK_UPDATE
    }
    
    // Constructors
    public BookNotification() {}
    
    public BookNotification(String isbn, String title, String author, String description,
                           BigDecimal price, Integer stock, BookCategory category,
                           NotificationType notificationType, LocalDateTime timestamp) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.notificationType = notificationType;
        this.timestamp = timestamp;
    }
    
    // Getters and Setters
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public Integer getStock() {
        return stock;
    }
    
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    
    public BookCategory getCategory() {
        return category;
    }
    
    public void setCategory(BookCategory category) {
        this.category = category;
    }
    
    public NotificationType getNotificationType() {
        return notificationType;
    }
    
    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public String toString() {
        return "BookNotification{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", category=" + category +
                ", notificationType=" + notificationType +
                ", timestamp=" + timestamp +
                '}';
    }
} 