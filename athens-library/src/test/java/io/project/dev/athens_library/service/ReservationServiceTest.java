package io.project.dev.athens_library.service;

import io.project.dev.athens_library.converter.ReservationConverter;
import io.project.dev.athens_library.dto.ReservationDto;
import io.project.dev.athens_library.model.Book;
import io.project.dev.athens_library.model.Reservation;
import io.project.dev.athens_library.model.User;
import io.project.dev.athens_library.model.reservation_status.ReservationStatus;
import io.project.dev.athens_library.repository.BookRepository;
import io.project.dev.athens_library.repository.ReservationRepository;
import io.project.dev.athens_library.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private ReservationService reservationService;

    private MockedStatic<ReservationConverter> mockedReservationConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockedReservationConverter = mockStatic(ReservationConverter.class);
        System.out.println("Setup completed. Mocks initialized.");
    }

    @AfterEach
    void tearDown() {
        mockedReservationConverter.close(); // Clean up static mocks
        Mockito.clearAllCaches(); // Clear any remaining Mockito caches
        System.out.println("Mocks cleaned up.");
    }

    @Test
    void getReservationById_ReservationExists_ShouldReturnReservationDto() {
        // Arrange
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);

        ReservationDto expectedReservationDto = new ReservationDto();
        expectedReservationDto.setId(reservationId);

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        mockedReservationConverter.when(() -> ReservationConverter.toDto(reservation)).thenReturn(expectedReservationDto);

        // Act
        ReservationDto actualReservationDto = reservationService.getReservationById(reservationId);

        // Assert
        assertNotNull(actualReservationDto);
        assertEquals(expectedReservationDto.getId(), actualReservationDto.getId());
        verify(reservationRepository, times(1)).findById(reservationId);
        System.out.println("Test 'getReservationById_ReservationExists_ShouldReturnReservationDto' passed successfully.");
    }

    @Test
    void getReservationById_ReservationDoesNotExist_ShouldThrowException() {
        // Arrange
        Long reservationId = 1L;

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservationService.getReservationById(reservationId);
        });

        assertEquals("Reservation not found", exception.getMessage());
        verify(reservationRepository, times(1)).findById(reservationId);
        System.out.println("Test 'getReservationById_ReservationDoesNotExist_ShouldThrowException' passed successfully.");
    }

    @Test
    void getReservationsByUserId_ReservationsExist_ShouldReturnListOfReservationDtos() {
        // Arrange
        Long userId = 1L;
        Reservation reservation1 = new Reservation();
        reservation1.setId(1L);
        Reservation reservation2 = new Reservation();
        reservation2.setId(2L);

        List<Reservation> reservations = List.of(reservation1, reservation2);

        ReservationDto reservationDto1 = new ReservationDto();
        reservationDto1.setId(1L);
        ReservationDto reservationDto2 = new ReservationDto();
        reservationDto2.setId(2L);

        List<ReservationDto> expectedReservationDtos = List.of(reservationDto1, reservationDto2);

        when(reservationRepository.findByUserId(userId)).thenReturn(Optional.of(reservations));
        mockedReservationConverter.when(() -> ReservationConverter.toDto(reservation1)).thenReturn(reservationDto1);
        mockedReservationConverter.when(() -> ReservationConverter.toDto(reservation2)).thenReturn(reservationDto2);

        // Act
        List<ReservationDto> actualReservationDtos = reservationService.getReservationsByUserId(userId);

        // Assert
        assertNotNull(actualReservationDtos);
        assertEquals(expectedReservationDtos.size(), actualReservationDtos.size());
        verify(reservationRepository, times(1)).findByUserId(userId);
        System.out.println("Test 'getReservationsByUserId_ReservationsExist_ShouldReturnListOfReservationDtos' passed successfully.");
    }

    @Test
    void createReservation_ValidInput_ShouldCreateAndSaveReservation() {
        // Arrange
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setUserId(1L);
        reservationDto.setBookId(2L);

        User user = new User();
        user.setId(1L);

        Book book = new Book();
        book.setId(2L);
        book.setAvailableCopies(5);

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setBook(book);

        Reservation savedReservation = new Reservation();
        savedReservation.setId(10L);

        ReservationDto expectedReservationDto = new ReservationDto();
        expectedReservationDto.setId(10L);

        when(userRepository.findById(reservationDto.getUserId())).thenReturn(Optional.of(user));
        when(bookRepository.findById(reservationDto.getBookId())).thenReturn(Optional.of(book));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(savedReservation);
        mockedReservationConverter.when(() -> ReservationConverter.toDto(savedReservation)).thenReturn(expectedReservationDto);
        when(reservationRepository.findByUserId(1L)).thenReturn(Optional.of(List.of())); // No active reservations

        // Act
        ReservationDto actualReservationDto = reservationService.createReservation(reservationDto);

        // Assert
        assertNotNull(actualReservationDto);
        assertEquals(expectedReservationDto.getId(), actualReservationDto.getId());
        verify(reservationRepository, times(1)).save(any(Reservation.class));
        verify(bookRepository, times(1)).save(book);
        System.out.println("Test 'createReservation_ValidInput_ShouldCreateAndSaveReservation' passed successfully.");
    }

    @Test
    void cancelReservation_ReservationExists_ShouldCancelAndReturnReservationDto() {
        // Arrange
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setStatus(ReservationStatus.ACTIVE);

        Book book = new Book();
        book.setId(2L);
        book.setAvailableCopies(3);
        reservation.setBook(book);

        Reservation savedReservation = new Reservation();
        savedReservation.setId(reservationId);
        savedReservation.setStatus(ReservationStatus.CANCELED);

        ReservationDto expectedReservationDto = new ReservationDto();
        expectedReservationDto.setId(reservationId);
        expectedReservationDto.setStatus(ReservationStatus.CANCELED);

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(reservationRepository.save(reservation)).thenReturn(savedReservation);
        mockedReservationConverter.when(() -> ReservationConverter.toDto(savedReservation)).thenReturn(expectedReservationDto);

        // Act
        ReservationDto actualReservationDto = reservationService.cancelReservation(reservationId);

        // Assert
        assertNotNull(actualReservationDto, "The returned ReservationDto should not be null.");
        assertEquals(expectedReservationDto.getId(), actualReservationDto.getId());
        assertEquals(expectedReservationDto.getStatus(), actualReservationDto.getStatus());
        assertEquals(4, book.getAvailableCopies(), "Book's available copies should be incremented by 1.");
        verify(reservationRepository, times(1)).save(reservation);
        verify(bookRepository, times(1)).save(book);
        System.out.println("Test 'cancelReservation_ReservationExists_ShouldCancelAndReturnReservationDto' passed successfully.");
    }

    @Test
    void verifyLimitOfActiveReservations_UserWithinLimit_ShouldReturnTrue() {
        // Arrange
        Long userId = 1L;
        Reservation activeReservation = new Reservation();
        activeReservation.setStatus(ReservationStatus.ACTIVE);

        when(reservationRepository.findByUserId(userId)).thenReturn(Optional.of(List.of(activeReservation)));

        // Act
        boolean result = reservationService.verifyLimitOfActiveReservations(userId);

        // Assert
        assertTrue(result);
        verify(reservationRepository, times(1)).findByUserId(userId);
        System.out.println("Test 'verifyLimitOfActiveReservations_UserWithinLimit_ShouldReturnTrue' passed successfully.");
    }

}
