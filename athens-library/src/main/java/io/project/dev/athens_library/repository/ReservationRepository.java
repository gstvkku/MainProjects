package io.project.dev.athens_library.repository;

import io.project.dev.athens_library.model.Reservation;
import io.project.dev.athens_library.model.reservation_status.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<List<Reservation>> findByUserId(Long userId);
    Optional<List<Reservation>> findByBookId(Long bookId);
    Optional<List<Reservation>> findByStatusAndCreatedAtBefore(ReservationStatus status, LocalDateTime dateTime);
}
