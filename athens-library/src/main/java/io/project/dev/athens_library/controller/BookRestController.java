package io.project.dev.athens_library.controller;

import io.project.dev.athens_library.dto.BookDto;
import io.project.dev.athens_library.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookRestController {

    private final BookService bookService;

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        try {
            BookDto bookDto = bookService.getBookById(id);
            return ResponseEntity.ok(bookDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @GetMapping("/title")
    public ResponseEntity<BookDto> getBookByTitle(@RequestParam String title) {
        try {
            BookDto bookDto = bookService.getBookByTitle(title);
            return ResponseEntity.ok(bookDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @GetMapping("/author")
    public ResponseEntity<List<BookDto>> getBooksByAuthor(@RequestParam String author) {
        try {
            List<BookDto> books = bookService.getBooksByAuthor(author);
            return ResponseEntity.ok(books);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @PostMapping
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto) {
        try {
            BookDto createdBook = bookService.addBook(bookDto);
            return ResponseEntity.status(201).body(createdBook);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
        try {
            BookDto updatedBook = bookService.updateBook(id, bookDto);
            return ResponseEntity.ok(updatedBook);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }
}
