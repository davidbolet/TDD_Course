package com.talant.bootcamp.booksservice.service;

import com.talant.bootcamp.booksservice.repository.BookRepository;
import com.talant.bootcamp.booksservice.model.Book;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryAlertService {

    private BookRepository bookRepository;

    public InventoryAlertService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getLowStockAlerts() {
        List<Book> allBooks = bookRepository.findAll();
        return allBooks.stream().filter(b -> b.getStock() < 5).collect(Collectors.toList()); 
    }
}
