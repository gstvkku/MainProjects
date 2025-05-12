package io.project.dev.athens_library.controller;

import io.project.dev.athens_library.dto.BookDto;
import io.project.dev.athens_library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookRestControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookRestController bookRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.out.println("Mocks initialized for BookRestControllerTest.");
    }

    @Test
    void getBookById_BookExists_ShouldReturnBookDto() {
        // Arrange
        Long bookId = 1L;
        BookDto expectedBook = new BookDto();
        expectedBook.setId(bookId);

        when(bookService.getBookById(bookId)).thenReturn(expectedBook);

        // Act
        ResponseEntity<BookDto> response = bookRestController.getBookById(bookId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(expectedBook.getId(), response.getBody().getId());
        verify(bookService, times(1)).getBookById(bookId);
        System.out.println("Test 'getBookById_BookExists_ShouldReturnBookDto' passed successfully.");
    }

    @Test
    void getBookById_BookDoesNotExist_ShouldReturnNotFound() {
        // Arrange
        Long bookId = 1L;

        when(bookService.getBookById(bookId)).thenThrow(new RuntimeException("Book not found"));

        // Act
        ResponseEntity<BookDto> response = bookRestController.getBookById(bookId);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(bookService, times(1)).getBookById(bookId);
        System.out.println("Test 'getBookById_BookDoesNotExist_ShouldReturnNotFound' passed successfully.");
    }

    @Test
    void getBookByTitle_BookExists_ShouldReturnBookDto() {
        // Arrange
        String title = "Test Book";
        BookDto expectedBook = new BookDto();
        expectedBook.setTitle(title);

        when(bookService.getBookByTitle(title)).thenReturn(expectedBook);

        // Act
        ResponseEntity<BookDto> response = bookRestController.getBookByTitle(title);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(expectedBook.getTitle(), response.getBody().getTitle());
        verify(bookService, times(1)).getBookByTitle(title);
        System.out.println("Test 'getBookByTitle_BookExists_ShouldReturnBookDto' passed successfully.");
    }

    @Test
    void getBookByTitle_BookDoesNotExist_ShouldReturnNotFound() {
        // Arrange
        String title = "Unknown Book";

        when(bookService.getBookByTitle(title)).thenThrow(new RuntimeException("Book not found"));

        // Act
        ResponseEntity<BookDto> response = bookRestController.getBookByTitle(title);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(bookService, times(1)).getBookByTitle(title);
        System.out.println("Test 'getBookByTitle_BookDoesNotExist_ShouldReturnNotFound' passed successfully.");
    }

    @Test
    void getBooksByAuthor_AuthorExists_ShouldReturnListOfBooks() {
        // Arrange
        String author = "Test Author";
        BookDto book1 = new BookDto();
        book1.setAuthor(author);
        BookDto book2 = new BookDto();
        book2.setAuthor(author);
        List<BookDto> expectedBooks = List.of(book1, book2);

        when(bookService.getBooksByAuthor(author)).thenReturn(expectedBooks);

        // Act
        ResponseEntity<List<BookDto>> response = bookRestController.getBooksByAuthor(author);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(expectedBooks.size(), response.getBody().size());
        verify(bookService, times(1)).getBooksByAuthor(author);
        System.out.println("Test 'getBooksByAuthor_AuthorExists_ShouldReturnListOfBooks' passed successfully.");
    }

    @Test
    void getBooksByAuthor_AuthorDoesNotExist_ShouldReturnNotFound() {
        // Arrange
        String author = "Unknown Author";

        when(bookService.getBooksByAuthor(author)).thenThrow(new RuntimeException("Books not found"));

        // Act
        ResponseEntity<List<BookDto>> response = bookRestController.getBooksByAuthor(author);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(bookService, times(1)).getBooksByAuthor(author);
        System.out.println("Test 'getBooksByAuthor_AuthorDoesNotExist_ShouldReturnNotFound' passed successfully.");
    }

    @Test
    void addBook_ValidRequest_ShouldReturnCreatedBook() {
        // Arrange
        BookDto bookDto = new BookDto();
        bookDto.setTitle("New Book");
        BookDto createdBook = new BookDto();
        createdBook.setId(1L);
        createdBook.setTitle("New Book");

        when(bookService.addBook(bookDto)).thenReturn(createdBook);

        // Act
        ResponseEntity<BookDto> response = bookRestController.addBook(bookDto);

        // Assert
        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(createdBook.getId(), response.getBody().getId());
        verify(bookService, times(1)).addBook(bookDto);
        System.out.println("Test 'addBook_ValidRequest_ShouldReturnCreatedBook' passed successfully.");
    }

    @Test
    void addBook_InvalidRequest_ShouldReturnBadRequest() {
        // Arrange
        BookDto bookDto = new BookDto();

        when(bookService.addBook(bookDto)).thenThrow(new RuntimeException("Invalid book data"));

        // Act
        ResponseEntity<BookDto> response = bookRestController.addBook(bookDto);

        // Assert
        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(bookService, times(1)).addBook(bookDto);
        System.out.println("Test 'addBook_InvalidRequest_ShouldReturnBadRequest' passed successfully.");
    }

    @Test
    void getAllBooks_ShouldReturnListOfBooks() {
        // Arrange
        BookDto book1 = new BookDto();
        book1.setId(1L);
        BookDto book2 = new BookDto();
        book2.setId(2L);
        List<BookDto> expectedBooks = List.of(book1, book2);

        when(bookService.getAllBooks()).thenReturn(expectedBooks);

        // Act
        ResponseEntity<List<BookDto>> response = bookRestController.getAllBooks();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(expectedBooks.size(), response.getBody().size());
        verify(bookService, times(1)).getAllBooks();
        System.out.println("Test 'getAllBooks_ShouldReturnListOfBooks' passed successfully.");
    }
}
