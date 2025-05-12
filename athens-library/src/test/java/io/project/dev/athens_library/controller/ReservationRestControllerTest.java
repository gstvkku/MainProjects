package io.project.dev.athens_library.controller;

import io.project.dev.athens_library.dto.ReservationDto;
import io.project.dev.athens_library.model.reservation_status.ReservationStatus;
import io.project.dev.athens_library.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationRestControllerTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationRestController reservationRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.out.println("Mocks initialized for ReservationRestControllerTest.");
    }

    @Test
    void getReservationById_ReservationExists_ShouldReturnReservationDto() {
        // Arrange
        Long reservationId = 1L;
        ReservationDto expectedReservationDto = new ReservationDto();
        expectedReservationDto.setId(reservationId);

        when(reservationService.getReservationById(reservationId)).thenReturn(expectedReservationDto);

        // Act
        ResponseEntity<ReservationDto> response = reservationRestController.getReservationById(reservationId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(expectedReservationDto.getId(), response.getBody().getId());
        verify(reservationService, times(1)).getReservationById(reservationId);
        System.out.println("Test 'getReservationById_ReservationExists_ShouldReturnReservationDto' passed successfully.");
    }

    @Test
    void getReservationById_ReservationDoesNotExist_ShouldReturnNotFound() {
        // Arrange
        Long reservationId = 1L;

        when(reservationService.getReservationById(reservationId)).thenThrow(new RuntimeException("Reservation not found"));

        // Act
        ResponseEntity<ReservationDto> response = reservationRestController.getReservationById(reservationId);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(reservationService, times(1)).getReservationById(reservationId);
        System.out.println("Test 'getReservationById_ReservationDoesNotExist_ShouldReturnNotFound' passed successfully.");
    }

    @Test
    void getReservationsByUser_UserExists_ShouldReturnReservations() {
        // Arrange
        Long userId = 1L;
        ReservationDto reservation1 = new ReservationDto();
        reservation1.setId(1L);
        ReservationDto reservation2 = new ReservationDto();
        reservation2.setId(2L);
        List<ReservationDto> expectedReservations = List.of(reservation1, reservation2);

        when(reservationService.getReservationsByUserId(userId)).thenReturn(expectedReservations);

        // Act
        ResponseEntity<List<ReservationDto>> response = reservationRestController.getReservationsByUser(userId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(expectedReservations.size(), response.getBody().size());
        verify(reservationService, times(1)).getReservationsByUserId(userId);
        System.out.println("Test 'getReservationsByUser_UserExists_ShouldReturnReservations' passed successfully.");
    }

    @Test
    void getReservationsByUser_UserDoesNotExist_ShouldReturnNotFound() {
        // Arrange
        Long userId = 1L;

        when(reservationService.getReservationsByUserId(userId)).thenThrow(new RuntimeException("Reservations not found"));

        // Act
        ResponseEntity<List<ReservationDto>> response = reservationRestController.getReservationsByUser(userId);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(reservationService, times(1)).getReservationsByUserId(userId);
        System.out.println("Test 'getReservationsByUser_UserDoesNotExist_ShouldReturnNotFound' passed successfully.");
    }

    @Test
    void createReservation_ValidRequest_ShouldReturnCreatedReservation() {
        // Arrange
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setUserId(1L);
        reservationDto.setBookId(2L);
        ReservationDto createdReservation = new ReservationDto();
        createdReservation.setId(10L);

        when(reservationService.createReservation(reservationDto)).thenReturn(createdReservation);

        // Act
        ResponseEntity<ReservationDto> response = reservationRestController.createReservation(reservationDto);

        // Assert
        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(createdReservation.getId(), response.getBody().getId());
        verify(reservationService, times(1)).createReservation(reservationDto);
        System.out.println("Test 'createReservation_ValidRequest_ShouldReturnCreatedReservation' passed successfully.");
    }

    @Test
    void createReservation_InvalidRequest_ShouldReturnNotFound() {
        // Arrange
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setUserId(1L);
        reservationDto.setBookId(2L);

        when(reservationService.createReservation(reservationDto)).thenThrow(new RuntimeException("User or book not found"));

        // Act
        ResponseEntity<ReservationDto> response = reservationRestController.createReservation(reservationDto);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(reservationService, times(1)).createReservation(reservationDto);
        System.out.println("Test 'createReservation_InvalidRequest_ShouldReturnNotFound' passed successfully.");
    }

    @Test
    void cancelReservation_ReservationExists_ShouldReturnUpdatedReservation() {
        // Arrange
        Long reservationId = 1L;
        ReservationDto updatedReservation = new ReservationDto();
        updatedReservation.setId(reservationId);
        updatedReservation.setStatus(ReservationStatus.CANCELED);

        when(reservationService.cancelReservation(reservationId)).thenReturn(updatedReservation);

        // Act
        ResponseEntity<ReservationDto> response = reservationRestController.cancelReservation(reservationId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(ReservationStatus.CANCELED, response.getBody().getStatus());
        verify(reservationService, times(1)).cancelReservation(reservationId);
        System.out.println("Test 'cancelReservation_ReservationExists_ShouldReturnUpdatedReservation' passed successfully.");
    }

    @Test
    void cancelReservation_ReservationDoesNotExist_ShouldReturnNotFound() {
        // Arrange
        Long reservationId = 1L;

        when(reservationService.cancelReservation(reservationId)).thenThrow(new RuntimeException("Reservation not found"));

        // Act
        ResponseEntity<ReservationDto> response = reservationRestController.cancelReservation(reservationId);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(reservationService, times(1)).cancelReservation(reservationId);
        System.out.println("Test 'cancelReservation_ReservationDoesNotExist_ShouldReturnNotFound' passed successfully.");
    }
}
