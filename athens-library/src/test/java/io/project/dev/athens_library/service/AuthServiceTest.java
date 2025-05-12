package io.project.dev.athens_library.service;

import io.project.dev.athens_library.converter.UserConverter;
import io.project.dev.athens_library.dto.LoginRequestDto;
import io.project.dev.athens_library.dto.UserDto;
import io.project.dev.athens_library.model.User;
import io.project.dev.athens_library.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.out.println("Setup completed. Mocks initialized.");
    }

    @Test
    void authLoginRequest_UserFoundAndPasswordCorrect_ShouldReturnUserDto() {

        String email = "test@example.com";
        String password = "password123";
        LoginRequestDto loginRequestDto = new LoginRequestDto(email, password);

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        UserDto expectedUserDto = new UserDto();
        expectedUserDto.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        mockStatic(UserConverter.class);
        when(UserConverter.toDto(user)).thenReturn(expectedUserDto);

        // Act
        UserDto actualUserDto = authService.authLoginRequest(loginRequestDto);

        // Assert
        assertNotNull(actualUserDto);
        assertEquals(expectedUserDto.getEmail(), actualUserDto.getEmail());
        verify(userRepository, times(1)).findByEmail(email);
        System.out.println("Test 'authLoginRequest_UserFoundAndPasswordCorrect_ShouldReturnUserDto' passed successfully.");
    }

    @Test
    void authLoginRequest_UserNotFound_ShouldThrowRuntimeException() {
        // Arrange
        String email = "notfound@example.com";
        String password = "password123";
        LoginRequestDto loginRequestDto = new LoginRequestDto(email, password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.authLoginRequest(loginRequestDto);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findByEmail(email);
        System.out.println("Test 'authLoginRequest_UserNotFound_ShouldThrowRuntimeException' passed successfully.");
    }
}