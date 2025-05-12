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
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public ReservationDto getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        return ReservationConverter.toDto(reservation);
    }

    public List<ReservationDto> getReservationsByUserId(Long id) {
        List<Reservation> reservations = reservationRepository.findByUserId(id)
                .orElseThrow(() -> new RuntimeException("Reservations not found"));
        return reservations.stream()
                .map(ReservationConverter::toDto)
                .collect(Collectors.toList());
    }

    public List<ReservationDto> getReservationsByBookId(Long id) {
        List<Reservation> reservations = reservationRepository.findByBookId(id)
                .orElseThrow(() -> new RuntimeException("Reservations not found"));
        return reservations.stream()
                .map(ReservationConverter::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReservationDto createReservation(ReservationDto reservationDto) {
        if (!verifyLimitOfActiveReservations(reservationDto.getUserId())) {
            throw new RuntimeException("User cannot have more than 3 active reservations.");
        }

        if (!verifyAvailableCopies(reservationDto.getBookId())) {
            throw new RuntimeException("No copies available for this book.");
        }

        Reservation reservation = new Reservation();
        User user = userRepository.findById(reservationDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepository.findById(reservationDto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        reservation.setUser(user);
        reservation.setBook(book);
        reservation.setStatus(ReservationStatus.ACTIVE);

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        Reservation savedReservation = reservationRepository.save(reservation);

        return ReservationConverter.toDto(savedReservation);
    }

    public List<ReservationDto> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationConverter::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReservationDto cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservation.setStatus(ReservationStatus.CANCELED);

        Book book = bookRepository.findById(reservation.getBook().getId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        return ReservationConverter.toDto(reservationRepository.save(reservation));
    }

    @Transactional
    public ReservationDto completeReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (reservation.getStatus().equals(ReservationStatus.CANCELED) || reservation.getStatus().equals(ReservationStatus.EXPIRED)){
            throw new RuntimeException("You cannot complete canceled or expired reservations.");
        }

        reservation.setStatus(ReservationStatus.RETURNED);

        Book book = bookRepository.findById(reservation.getBook().getId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        return ReservationConverter.toDto(reservationRepository.save(reservation));
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void expireOldReservations() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);

        List<Reservation> oldReservations = reservationRepository
                .findByStatusAndCreatedAtBefore(ReservationStatus.ACTIVE, sevenDaysAgo)
                .orElseThrow(() -> new RuntimeException("Reservations to expire not found"));

        for (Reservation reservation : oldReservations) {
            reservation.setStatus(ReservationStatus.EXPIRED);
            Book book = reservation.getBook();
            book.setAvailableCopies(book.getAvailableCopies() + 1);
            bookRepository.save(book);
        }

        reservationRepository.saveAll(oldReservations);

        System.out.println("Expired " + oldReservations.size() + " reservations at midnight.");
    }

    public boolean verifyLimitOfActiveReservations(Long userId) {
        long activeReservationsCount = reservationRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Reservations not found"))
                .stream()
                .filter(reservation -> reservation.getStatus() == ReservationStatus.ACTIVE)
                .count();

        return activeReservationsCount < 3;
    }

    public boolean verifyAvailableCopies(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return book.getAvailableCopies() > 0;
    }
}
