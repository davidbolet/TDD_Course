package com.talant.bootcamp.booksservice.dto;

import com.talant.bootcamp.booksservice.model.BookCategory;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO for book request
 */
public class BookRequest {
    
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    private String title;
    
    @NotBlank(message = "Author is required")
    @Size(min = 1, max = 255, message = "Author must be between 1 and 255 characters")
    private String author;
    
    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = "^(?:[0-9]{10}|[0-9]{13})$", message = "ISBN must be 10 or 13 digits")
    private String isbn;
    
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;
    
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @DecimalMax(value = "9999.99", message = "Price cannot exceed 9999.99")
    private BigDecimal price;
    
    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock must be greater than 0")
    @Max(value = 999999, message = "Stock cannot exceed 999999")
    private Integer stock;
    
    @NotNull(message = "Category is required")	
    private BookCategory category;
    
    // Constructors
    public BookRequest() {}
    
    public BookRequest(String title, String author, String isbn, String description, 
                      BigDecimal price, Integer stock, BookCategory category) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }
    
    // Getters and Setters
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
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
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
    
    @Override
    public String toString() {
        return "BookRequest{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", category=" + category +
                '}';
    }
} 