package io.project.dev.athens_library.converter;

import io.project.dev.athens_library.dto.ReservationDto;
import io.project.dev.athens_library.model.Book;
import io.project.dev.athens_library.model.Reservation;
import io.project.dev.athens_library.model.User;

public class ReservationConverter {

    public static ReservationDto toDto(Reservation reservation) {
        if (reservation == null) {
            return null;
        }

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(reservation.getId());
        reservationDto.setUserId(reservation.getUser().getId());  // Pega o ID do usuário
        reservationDto.setBookId(reservation.getBook().getId());  // Pega o ID do livro
        reservationDto.setCreatedAt(reservation.getCreatedAt());
        reservationDto.setStatus(reservation.getStatus());

        return reservationDto;
    }

    public static Reservation toEntity(ReservationDto reservationDto, User user, Book book) {
        if (reservationDto == null) {
            return null;
        }

        Reservation reservation = new Reservation();

        reservation.setUser(user);
        reservation.setBook(book);
        reservation.setStatus(reservationDto.getStatus());

        return reservation;
    }
}