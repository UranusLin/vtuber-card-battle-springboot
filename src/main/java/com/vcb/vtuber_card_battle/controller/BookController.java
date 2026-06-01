package com.vcb.vtuber_card_battle.controller;

import com.vcb.vtuber_card_battle.config.ShopProperties;
import com.vcb.vtuber_card_battle.entity.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private List<Book> books = new ArrayList<>();
    private Long nextId = 1L;


    // get all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {return ResponseEntity.ok(books);}

    // get book by id
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = books.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();

        return book.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // api/books
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        book.setId(nextId++);
        books.add(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    // api/books/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id,
                                           @RequestBody Book updateBook) {
        for (Book book: books) {
            if (book.getId().equals(id)) {
                book.setTitle(updateBook.getTitle());
                book.setAuthor(updateBook.getAuthor());
                book.setIsbn(updateBook.getIsbn());
                book.setPrice(updateBook.getPrice());
                book.setStock(updateBook.getStock());
                return ResponseEntity.ok(book);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // /api/books/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean removed = books.removeIf(b -> b.getId().equals(id));
        if (removed) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // /api/books/search
    @GetMapping("/search")
    public ResponseEntity<Book> getBookByTitle(@RequestParam String title) {
        Optional<Book> book = books.stream()
                .filter(b -> b.getTitle().equals(title))
                .findFirst();

        return book.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
