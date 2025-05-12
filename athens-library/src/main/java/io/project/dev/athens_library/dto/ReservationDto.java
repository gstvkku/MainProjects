package io.project.dev.athens_library.dto;

import io.project.dev.athens_library.model.reservation_status.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ReservationDto {
    private Long id;

    private Long userId;

    private Long bookId;

    private LocalDateTime createdAt;

    private ReservationStatus status;
}
