package io.project.dev.athens_library.controller;

import io.project.dev.athens_library.dto.ChangePassRequestDto;
import io.project.dev.athens_library.dto.UserDto;
import io.project.dev.athens_library.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRestControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserRestController userRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.out.println("Mocks initialized for UserRestControllerTest.");
    }

    @Test
    void getUserById_UserExists_ShouldReturnUserDto() {
        // Arrange
        Long userId = 1L;
        UserDto expectedUser = new UserDto();
        expectedUser.setId(userId);

        when(userService.getUserById(userId)).thenReturn(expectedUser);

        // Act
        ResponseEntity<UserDto> response = userRestController.getUserById(userId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(expectedUser.getId(), response.getBody().getId());
        verify(userService, times(1)).getUserById(userId);
        System.out.println("Test 'getUserById_UserExists_ShouldReturnUserDto' passed successfully.");
    }

    @Test
    void getUserById_UserDoesNotExist_ShouldReturnNotFound() {
        // Arrange
        Long userId = 1L;

        when(userService.getUserById(userId)).thenThrow(new RuntimeException("User not found"));

        // Act
        ResponseEntity<UserDto> response = userRestController.getUserById(userId);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(userService, times(1)).getUserById(userId);
        System.out.println("Test 'getUserById_UserDoesNotExist_ShouldReturnNotFound' passed successfully.");
    }

    @Test
    void createUser_ValidRequest_ShouldReturnCreatedUser() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setName("New User");
        UserDto createdUser = new UserDto();
        createdUser.setId(1L);
        createdUser.setName("New User");

        when(userService.createUser(userDto)).thenReturn(createdUser);

        // Act
        ResponseEntity<UserDto> response = userRestController.createUser(userDto);

        // Assert
        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(createdUser.getId(), response.getBody().getId());
        verify(userService, times(1)).createUser(userDto);
        System.out.println("Test 'createUser_ValidRequest_ShouldReturnCreatedUser' passed successfully.");
    }

    @Test
    void updateUser_UserExists_ShouldReturnUpdatedUser() {
        // Arrange
        Long userId = 1L;
        UserDto userDto = new UserDto();
        userDto.setName("Updated Name");
        UserDto updatedUser = new UserDto();
        updatedUser.setId(userId);
        updatedUser.setName("Updated Name");

        when(userService.updateUser(userId, userDto)).thenReturn(updatedUser);

        // Act
        ResponseEntity<UserDto> response = userRestController.updateUser(userId, userDto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(updatedUser.getId(), response.getBody().getId());
        verify(userService, times(1)).updateUser(userId, userDto);
        System.out.println("Test 'updateUser_UserExists_ShouldReturnUpdatedUser' passed successfully.");
    }

    @Test
    void updateUser_UserDoesNotExist_ShouldReturnNotFound() {
        // Arrange
        Long userId = 1L;
        UserDto userDto = new UserDto();

        when(userService.updateUser(userId, userDto)).thenThrow(new RuntimeException("User not found"));

        // Act
        ResponseEntity<UserDto> response = userRestController.updateUser(userId, userDto);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(userService, times(1)).updateUser(userId, userDto);
        System.out.println("Test 'updateUser_UserDoesNotExist_ShouldReturnNotFound' passed successfully.");
    }

    @Test
    void deleteUser_UserExists_ShouldReturnNoContent() {
        // Arrange
        Long userId = 1L;

        doNothing().when(userService).deleteUser(userId);

        // Act
        ResponseEntity<Void> response = userRestController.deleteUser(userId);

        // Assert
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(userService, times(1)).deleteUser(userId);
        System.out.println("Test 'deleteUser_UserExists_ShouldReturnNoContent' passed successfully.");
    }

    @Test
    void deleteUser_UserDoesNotExist_ShouldReturnNotFound() {
        // Arrange
        Long userId = 1L;

        doThrow(new RuntimeException("User not found")).when(userService).deleteUser(userId);

        // Act
        ResponseEntity<Void> response = userRestController.deleteUser(userId);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(userService, times(1)).deleteUser(userId);
        System.out.println("Test 'deleteUser_UserDoesNotExist_ShouldReturnNotFound' passed successfully.");
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        // Arrange
        UserDto user1 = new UserDto();
        user1.setId(1L);
        UserDto user2 = new UserDto();
        user2.setId(2L);
        List<UserDto> expectedUsers = List.of(user1, user2);

        when(userService.getAllUsers()).thenReturn(expectedUsers);

        // Act
        ResponseEntity<List<UserDto>> response = userRestController.getAllUsers();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(expectedUsers.size(), response.getBody().size());
        verify(userService, times(1)).getAllUsers();
        System.out.println("Test 'getAllUsers_ShouldReturnListOfUsers' passed successfully.");
    }

    @Test
    void updateUserPassword_ValidRequest_ShouldReturnUpdatedPassword() {
        // Arrange
        Long userId = 1L;
        ChangePassRequestDto request = new ChangePassRequestDto("oldPass", "newPass");
        String updatedPassword = "Password updated successfully.";

        when(userService.updateUserPass(userId, request.getOldPass(), request.getNewPass())).thenReturn(updatedPassword);

        // Act
        ResponseEntity<String> response = userRestController.updateUserPassword(userId, request);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(updatedPassword, response.getBody());
        verify(userService, times(1)).updateUserPass(userId, request.getOldPass(), request.getNewPass());
        System.out.println("Test 'updateUserPassword_ValidRequest_ShouldReturnUpdatedPassword' passed successfully.");
    }

    @Test
    void updateUserPassword_InvalidRequest_ShouldReturnBadRequest() {
        // Arrange
        Long userId = 1L;
        ChangePassRequestDto request = new ChangePassRequestDto("oldPass", "newPass");

        when(userService.updateUserPass(userId, request.getOldPass(), request.getNewPass())).thenReturn(null);

        // Act
        ResponseEntity<String> response = userRestController.updateUserPassword(userId, request);

        // Assert
        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(userService, times(1)).updateUserPass(userId, request.getOldPass(), request.getNewPass());
        System.out.println("Test 'updateUserPassword_InvalidRequest_ShouldReturnBadRequest' passed successfully.");
    }
}