package com.talant.bootcamp.booksservice.config;

import com.talant.bootcamp.booksservice.model.Book;
import com.talant.bootcamp.booksservice.model.BookCategory;
import com.talant.bootcamp.booksservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    
    private final BookRepository bookRepository;
    
    @Autowired
    public DataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Only load data if there are no books in the database
        if (bookRepository.count() == 0) {
            loadSampleBooks();
        }
    }
    
    private void loadSampleBooks() {
        List<Book> sampleBooks = Arrays.asList(
            new Book(
                "The Lord of the Rings",
                "J.R.R. Tolkien",
                "9788445071405",
                "An epic fantasy story about the fight against evil",
                new BigDecimal("29.99"),
                50,
                BookCategory.FANTASY
            ),
            new Book(
                "1984",
                "George Orwell",
                "9788497594257",
                "A dystopia about totalitarian control",
                new BigDecimal("19.99"),
                30,
                BookCategory.FICTION
            ),
            new Book(
                "100 Years of Solitude",
                "Gabriel García Márquez",
                "9788497592208",
                "The story of the Buendía family in Macondo",
                new BigDecimal("24.99"),
                25,
                BookCategory.FICTION
            ),
            new Book(
                "The Little Prince",
                "Antoine de Saint-Exupéry",
                "9788497592796",
                "A poetic story about friendship and love",
                new BigDecimal("15.99"),
                100,
                BookCategory.CHILDREN
            ),
            new Book(
                "Don Quixote de la Mancha",
                "Miguel de Cervantes",
                "9788497594258",
                "The masterpiece of Spanish literature",
                new BigDecimal("34.99"),
                20,
                BookCategory.FICTION
            ),
            new Book(
                "Clean Code",
                "Robert C. Martin",
                "9780132350884",
                "Guide to writing clean and maintainable code",
                new BigDecimal("45.99"),
                15,
                BookCategory.TECHNOLOGY
            ),
            new Book(
                "Design Patterns",
                "Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides",
                "9780201633610",
                "Design patterns in object-oriented programming",
                new BigDecimal("55.99"),
                10,
                BookCategory.TECHNOLOGY
            ),
            new Book(
                "Steve Jobs",
                "Walter Isaacson",
                "9788499893404",
                "The authorized biography of the co-founder of Apple",
                new BigDecimal("39.99"),
                35,
                BookCategory.BIOGRAPHY
            ),
            new Book(
                "Sapiens: De animales a dioses",
                "Yuval Noah Harari",
                "9788499926223",
                "Brief history of humanity",
                new BigDecimal("27.99"),
                40,
                BookCategory.HISTORY
            ),
            new Book(
                "El arte de la guerra",
                "Sun Tzu",
                "9788497594259",
                "Chinese military treatise on strategy",
                new BigDecimal("18.99"),
                60,
                BookCategory.BUSINESS
            ),
            new Book(
                "The 7 Habits of Highly Effective People",
                "Stephen R. Covey",
                "9788497594260",
                "Guide for personal and professional development",
                new BigDecimal("22.99"),
                45,
                BookCategory.SELF_HELP
            ),
            new Book(
                "Cooking for Beginners",
                "María García",
                "9788497594261",
                "Easy and delicious recipes to start cooking",
                new BigDecimal("32.99"),
                25,
                BookCategory.COOKING
            ),
            new Book(
                "Traveling in Spain",
                "Carlos López",
                "9788497594262",
                "Complete guide to traveling in Spain",
                new BigDecimal("28.99"),
                30,
                BookCategory.TRAVEL
            ),
            new Book(
                "Harry Potter y la piedra filosofal",
                "J.K. Rowling",
                "9788497594263",
                "The first book in the Harry Potter saga",
                new BigDecimal("21.99"),
                80,
                BookCategory.YOUNG_ADULT
            ),
            new Book(
                "The Da Vinci Code",
                "Dan Brown",
                "9788497594264",
                "A thriller about a religious mystery",
                new BigDecimal("23.99"),
                55,
                BookCategory.THRILLER
            )
        );
        
        bookRepository.saveAll(sampleBooks);
        System.out.println("Loaded " + sampleBooks.size() + " sample books into the database.");
    }
} 