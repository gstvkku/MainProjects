package io.project.dev.athens_library.service;

import io.project.dev.athens_library.converter.BookConverter;
import io.project.dev.athens_library.dto.BookDto;
import io.project.dev.athens_library.model.Book;
import io.project.dev.athens_library.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return BookConverter.toDto(book);
    }

    public BookDto getBookByTitle(String title) {
        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return BookConverter.toDto(book);
    }

    public List<BookDto> getBooksByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthor(author)
                .orElseThrow(() -> new RuntimeException("Books not found"));
        return books.stream()
                .map(BookConverter::toDto)
                .collect(Collectors.toList());
    }

    public BookDto addBook(BookDto bookDto) {
        if (bookRepository.findByIsbn(bookDto.getIsbn()).isPresent()) {
            throw new RuntimeException("Book already exists");
        }
        Book book = BookConverter.toEntity(bookDto);
        Book savedBook = bookRepository.save(book);
        return BookConverter.toDto(savedBook);
    }

    @Transactional
    public BookDto updateBook(Long id, BookDto bookDto) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        existingBook.setTitle(bookDto.getTitle());
        existingBook.setAuthor(bookDto.getAuthor());
        existingBook.setAvailableCopies(bookDto.getAvailableCopies());

        bookRepository.findByIsbn(bookDto.getIsbn())
                .filter(bookWithSameIsbn -> !bookWithSameIsbn.getId().equals(id))
                .ifPresent(book -> {
                    throw new RuntimeException("Another book with the same ISBN already exists");
                });
        existingBook.setIsbn(bookDto.getIsbn());

        return BookConverter.toDto(bookRepository.save(existingBook));
    }

    public List<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookConverter::toDto)
                .collect(Collectors.toList());
    }
}
