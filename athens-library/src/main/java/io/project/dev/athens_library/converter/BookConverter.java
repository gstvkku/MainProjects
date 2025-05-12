package io.project.dev.athens_library.converter;

import io.project.dev.athens_library.dto.BookDto;
import io.project.dev.athens_library.model.Book;

public class BookConverter {

    public static BookDto toDto(Book book) {
        if (book == null) {
            return null;
        }

        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setAvailableCopies(book.getAvailableCopies());

        return bookDto;
    }

    public static Book toEntity(BookDto bookDto) {
        if (bookDto == null) {
            return null;
        }

        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());
        book.setAvailableCopies(bookDto.getAvailableCopies());

        return book;
    }
}