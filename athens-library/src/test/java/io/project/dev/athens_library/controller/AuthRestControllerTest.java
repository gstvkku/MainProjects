package io.project.dev.athens_library.controller;

import io.project.dev.athens_library.dto.LoginRequestDto;
import io.project.dev.athens_library.dto.UserDto;
import io.project.dev.athens_library.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthRestControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthRestController authRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.out.println("Mocks initialized for AuthRestControllerTest.");
    }

    @Test
    void login_ValidCredentials_ShouldReturnUserDto() {
        // Arrange
        LoginRequestDto loginRequestDto = new LoginRequestDto("test@example.com", "password123");
        UserDto expectedUserDto = new UserDto();
        expectedUserDto.setEmail("test@example.com");

        when(authService.authLoginRequest(loginRequestDto)).thenReturn(expectedUserDto);

        // Act
        ResponseEntity<UserDto> response = authRestController.login(loginRequestDto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(expectedUserDto.getEmail(), response.getBody().getEmail());
        verify(authService, times(1)).authLoginRequest(loginRequestDto);
        System.out.println("Test 'login_ValidCredentials_ShouldReturnUserDto' passed successfully.");
    }

    @Test
    void login_InvalidCredentials_ShouldReturnUnauthorized() {
        // Arrange
        LoginRequestDto loginRequestDto = new LoginRequestDto("invalid@example.com", "wrongpassword");

        when(authService.authLoginRequest(loginRequestDto)).thenReturn(null);

        // Act
        ResponseEntity<UserDto> response = authRestController.login(loginRequestDto);

        // Assert
        assertNotNull(response);
        assertEquals(401, response.getStatusCodeValue()); // Unauthorized
        assertNull(response.getBody());
        verify(authService, times(1)).authLoginRequest(loginRequestDto);
        System.out.println("Test 'login_InvalidCredentials_ShouldReturnUnauthorized' passed successfully.");
    }
}
