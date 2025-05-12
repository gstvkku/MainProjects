package io.project.dev.athens_library.repository;

import io.project.dev.athens_library.model.Book;
import io.project.dev.athens_library.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);
    Optional<Book> findByIsbn(String isbn);
    Optional<List<Book>> findByAuthor(String author);
}
