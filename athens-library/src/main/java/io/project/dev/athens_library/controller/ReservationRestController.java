package io.project.dev.athens_library.controller;

import io.project.dev.athens_library.dto.ReservationDto;
import io.project.dev.athens_library.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@AllArgsConstructor
public class ReservationRestController {
    private final ReservationService reservationService;

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> getReservationById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(reservationService.getReservationById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationDto>> getReservationsByUser(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(reservationService.getReservationsByUserId(userId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<ReservationDto>> getReservationsByBook(@PathVariable Long bookId) {
        try {
            return ResponseEntity.ok(reservationService.getReservationsByBookId(bookId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @PostMapping
    public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationDto reservationDto) {
        try {
            ReservationDto created = reservationService.createReservation(reservationDto);
            return ResponseEntity.status(201).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<ReservationDto> cancelReservation(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(reservationService.cancelReservation(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<ReservationDto> completeReservation(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(reservationService.completeReservation(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }

}
