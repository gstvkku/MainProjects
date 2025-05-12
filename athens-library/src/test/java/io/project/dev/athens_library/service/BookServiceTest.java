package io.project.dev.athens_library.service;

import io.project.dev.athens_library.converter.BookConverter;
import io.project.dev.athens_library.dto.BookDto;
import io.project.dev.athens_library.model.Book;
import io.project.dev.athens_library.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private MockedStatic<BookConverter> mockedBookConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockedBookConverter = mockStatic(BookConverter.class);
        System.out.println("Setup completed. Mocks initialized.");
    }

    @AfterEach
    void tearDown() {
        mockedBookConverter.close(); // Ensure static mocks are cleaned up
        Mockito.clearAllCaches(); // Clear any remaining Mockito caches
        System.out.println("Mocks cleaned up.");
    }

    @Test
    void getBookById_BookExists_ShouldReturnBookDto() {
        // Arrange
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Test Book");
        BookDto expectedBookDto = new BookDto();
        expectedBookDto.setId(bookId);
        expectedBookDto.setTitle("Test Book");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        mockedBookConverter.when(() -> BookConverter.toDto(book)).thenReturn(expectedBookDto);

        // Act
        BookDto actualBookDto = bookService.getBookById(bookId);

        // Assert
        assertNotNull(actualBookDto);
        assertEquals(expectedBookDto.getId(), actualBookDto.getId());
        verify(bookRepository, times(1)).findById(bookId);
        System.out.println("Test 'getBookById_BookExists_ShouldReturnBookDto' passed successfully.");
    }

    @Test
    void getBookById_BookDoesNotExist_ShouldThrowException() {
        // Arrange
        Long bookId = 1L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookService.getBookById(bookId);
        });

        assertEquals("Book not found", exception.getMessage());
        verify(bookRepository, times(1)).findById(bookId);
        System.out.println("Test 'getBookById_BookDoesNotExist_ShouldThrowException' passed successfully.");
    }

    @Test
    void getBookByTitle_BookExists_ShouldReturnBookDto() {
        // Arrange
        String title = "Test Book";
        Book book = new Book();
        book.setTitle(title);
        BookDto expectedBookDto = new BookDto();
        expectedBookDto.setTitle(title);

        when(bookRepository.findByTitle(title)).thenReturn(Optional.of(book));
        mockedBookConverter.when(() -> BookConverter.toDto(book)).thenReturn(expectedBookDto);

        // Act
        BookDto actualBookDto = bookService.getBookByTitle(title);

        // Assert
        assertNotNull(actualBookDto);
        assertEquals(expectedBookDto.getTitle(), actualBookDto.getTitle());
        verify(bookRepository, times(1)).findByTitle(title);
        System.out.println("Test 'getBookByTitle_BookExists_ShouldReturnBookDto' passed successfully.");
    }

    @Test
    void getBookByTitle_BookDoesNotExist_ShouldThrowException() {
        // Arrange
        String title = "Unknown Book";

        when(bookRepository.findByTitle(title)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookService.getBookByTitle(title);
        });

        assertEquals("Book not found", exception.getMessage());
        verify(bookRepository, times(1)).findByTitle(title);
        System.out.println("Test 'getBookByTitle_BookDoesNotExist_ShouldThrowException' passed successfully.");
    }

    @Test
    void addBook_BookDoesNotExist_ShouldSaveAndReturnBookDto() {
        // Arrange
        BookDto bookDto = new BookDto();
        bookDto.setIsbn("12345");
        bookDto.setTitle("New Book");
        Book book = new Book();
        book.setIsbn("12345");
        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setIsbn("12345");
        savedBook.setTitle("New Book");
        BookDto expectedBookDto = new BookDto();
        expectedBookDto.setId(1L);
        expectedBookDto.setIsbn("12345");
        expectedBookDto.setTitle("New Book");

        when(bookRepository.findByIsbn(bookDto.getIsbn())).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);
        mockedBookConverter.when(() -> BookConverter.toEntity(bookDto)).thenReturn(book);
        mockedBookConverter.when(() -> BookConverter.toDto(savedBook)).thenReturn(expectedBookDto);

        // Act
        BookDto actualBookDto = bookService.addBook(bookDto);

        // Assert
        assertNotNull(actualBookDto);
        assertEquals(expectedBookDto.getId(), actualBookDto.getId());
        verify(bookRepository, times(1)).findByIsbn(bookDto.getIsbn());
        verify(bookRepository, times(1)).save(any(Book.class));
        System.out.println("Test 'addBook_BookDoesNotExist_ShouldSaveAndReturnBookDto' passed successfully.");
    }

    @Test
    void addBook_BookAlreadyExists_ShouldThrowException() {
        // Arrange
        BookDto bookDto = new BookDto();
        bookDto.setIsbn("12345");

        when(bookRepository.findByIsbn(bookDto.getIsbn())).thenReturn(Optional.of(new Book()));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookService.addBook(bookDto);
        });

        assertEquals("Book already exists", exception.getMessage());
        verify(bookRepository, times(1)).findByIsbn(bookDto.getIsbn());
        System.out.println("Test 'addBook_BookAlreadyExists_ShouldThrowException' passed successfully.");
    }

    @Test
    void updateBook_BookExists_ShouldUpdateAndReturnBookDto() {
        // Arrange
        Long bookId = 1L;
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Updated Title");
        bookDto.setAuthor("Updated Author");
        bookDto.setIsbn("67890");
        bookDto.setAvailableCopies(5);

        Book existingBook = new Book();
        existingBook.setId(bookId);
        existingBook.setTitle("Old Title");
        existingBook.setAuthor("Old Author");
        existingBook.setIsbn("12345");

        Book updatedBook = new Book();
        updatedBook.setId(bookId);
        updatedBook.setTitle("Updated Title");
        updatedBook.setAuthor("Updated Author");
        updatedBook.setIsbn("67890");
        updatedBook.setAvailableCopies(5);

        BookDto expectedBookDto = new BookDto();
        expectedBookDto.setId(bookId);
        expectedBookDto.setTitle("Updated Title");
        expectedBookDto.setAuthor("Updated Author");
        expectedBookDto.setIsbn("67890");
        expectedBookDto.setAvailableCopies(5);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(existingBook)).thenReturn(updatedBook);
        mockedBookConverter.when(() -> BookConverter.toDto(updatedBook)).thenReturn(expectedBookDto);

        // Act
        BookDto actualBookDto = bookService.updateBook(bookId, bookDto);

        // Assert
        assertNotNull(actualBookDto);
        assertEquals(expectedBookDto.getTitle(), actualBookDto.getTitle());
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(existingBook);
        System.out.println("Test 'updateBook_BookExists_ShouldUpdateAndReturnBookDto' passed successfully.");
    }

    @Test
    void getAllBooks_BooksExist_ShouldReturnListOfBookDtos() {
        // Arrange
        Book book1 = new Book();
        book1.setId(1L);
        Book book2 = new Book();
        book2.setId(2L);
        List<Book> books = List.of(book1, book2);

        BookDto bookDto1 = new BookDto();
        bookDto1.setId(1L);
        BookDto bookDto2 = new BookDto();
        bookDto2.setId(2L);
        List<BookDto> expectedBookDtos = List.of(bookDto1, bookDto2);

        when(bookRepository.findAll()).thenReturn(books);
        mockedBookConverter.when(() -> BookConverter.toDto(book1)).thenReturn(bookDto1);
        mockedBookConverter.when(() -> BookConverter.toDto(book2)).thenReturn(bookDto2);

        // Act
        List<BookDto> actualBookDtos = bookService.getAllBooks();

        // Assert
        assertNotNull(actualBookDtos);
        assertEquals(expectedBookDtos.size(), actualBookDtos.size());
        verify(bookRepository, times(1)).findAll();
        System.out.println("Test 'getAllBooks_BooksExist_ShouldReturnListOfBookDtos' passed successfully.");
    }
}