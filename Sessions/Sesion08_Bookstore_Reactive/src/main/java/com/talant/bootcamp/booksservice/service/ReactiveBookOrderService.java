package com.talant.bootcamp.booksservice.service;

import com.talant.bootcamp.booksservice.model.Book;
import com.talant.bootcamp.booksservice.repository.BookRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ReactiveBookOrderService {

    private final BookRepository bookRepository;

    public ReactiveBookOrderService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Mono<Boolean> checkStock(Long bookId) {
        if (bookId == null) {
            return Mono.error(new IllegalArgumentException("Book ID cannot be null"));
        }
        return Mono.fromSupplier(() -> {
            Book book = bookRepository.findById(bookId).orElse(null);
            return book != null && book.getStock() != null && book.getStock() > 0;
        });
    }
}
