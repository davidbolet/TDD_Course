package com.talant.bootcamp.booksservice.service;

import com.talant.bootcamp.booksservice.model.Book;
import com.talant.bootcamp.booksservice.repository.BookRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class ReactiveBookSearchService {

    private final BookRepository bookRepository;

    public ReactiveBookSearchService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Flux<Book> findByTitleContains(String keyword) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(keyword);
        return Flux.fromIterable(books);
    }
}
