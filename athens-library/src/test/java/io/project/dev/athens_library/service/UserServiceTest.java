package io.project.dev.athens_library.service;

import io.project.dev.athens_library.converter.UserConverter;
import io.project.dev.athens_library.dto.UserDto;
import io.project.dev.athens_library.model.User;
import io.project.dev.athens_library.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private MockedStatic<UserConverter> mockedUserConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockedUserConverter = mockStatic(UserConverter.class);
        System.out.println("Setup completed. Mocks initialized.");
    }

    @AfterEach
    void tearDown() {
        mockedUserConverter.close(); // Clean up static mocks
        Mockito.clearAllCaches(); // Clear any remaining Mockito caches
        System.out.println("Mocks cleaned up.");
    }

    @Test
    void getUserById_UserExists_ShouldReturnUserDto() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setName("John Doe");
        UserDto expectedUserDto = new UserDto();
        expectedUserDto.setId(userId);
        expectedUserDto.setName("John Doe");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        mockedUserConverter.when(() -> UserConverter.toDto(user)).thenReturn(expectedUserDto);

        // Act
        UserDto actualUserDto = userService.getUserById(userId);

        // Assert
        assertNotNull(actualUserDto);
        assertEquals(expectedUserDto.getId(), actualUserDto.getId());
        verify(userRepository, times(1)).findById(userId);
        System.out.println("Test 'getUserById_UserExists_ShouldReturnUserDto' passed successfully.");
    }

    @Test
    void getUserById_UserDoesNotExist_ShouldThrowException() {
        // Arrange
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserById(userId);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        System.out.println("Test 'getUserById_UserDoesNotExist_ShouldThrowException' passed successfully.");
    }

    @Test
    void createUser_ShouldSaveAndReturnUserDto() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setName("Jane Doe");
        User user = new User();
        user.setName("Jane Doe");
        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("Jane Doe");
        UserDto expectedUserDto = new UserDto();
        expectedUserDto.setId(1L);
        expectedUserDto.setName("Jane Doe");

        mockedUserConverter.when(() -> UserConverter.toEntity(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        mockedUserConverter.when(() -> UserConverter.toDto(savedUser)).thenReturn(expectedUserDto);

        // Act
        UserDto actualUserDto = userService.createUser(userDto);

        // Assert
        assertNotNull(actualUserDto);
        assertEquals(expectedUserDto.getId(), actualUserDto.getId());
        verify(userRepository, times(1)).save(user);
        System.out.println("Test 'createUser_ShouldSaveAndReturnUserDto' passed successfully.");
    }

    @Test
    void deleteUser_UserExists_ShouldDeleteUser() {
        // Arrange
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(true);
        doNothing().when(userRepository).deleteById(userId);

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).deleteById(userId);
        System.out.println("Test 'deleteUser_UserExists_ShouldDeleteUser' passed successfully.");
    }

    @Test
    void deleteUser_UserDoesNotExist_ShouldThrowException() {
        // Arrange
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.deleteUser(userId);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).existsById(userId);
        System.out.println("Test 'deleteUser_UserDoesNotExist_ShouldThrowException' passed successfully.");
    }

    @Test
    void updateUser_UserExists_ShouldUpdateAndReturnUserDto() {
        // Arrange
        Long userId = 1L;
        UserDto userDto = new UserDto();
        userDto.setName("Updated Name");
        userDto.setEmail("updated@example.com");

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setName("Old Name");
        existingUser.setEmail("old@example.com");

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setName("Updated Name");
        updatedUser.setEmail("updated@example.com");

        UserDto expectedUserDto = new UserDto();
        expectedUserDto.setId(userId);
        expectedUserDto.setName("Updated Name");
        expectedUserDto.setEmail("updated@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);
        mockedUserConverter.when(() -> UserConverter.toDto(updatedUser)).thenReturn(expectedUserDto);

        // Act
        UserDto actualUserDto = userService.updateUser(userId, userDto);

        // Assert
        assertNotNull(actualUserDto);
        assertEquals(expectedUserDto.getName(), actualUserDto.getName());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(existingUser);
        System.out.println("Test 'updateUser_UserExists_ShouldUpdateAndReturnUserDto' passed successfully.");
    }

    @Test
    void updateUserPass_UserExistsAndPasswordMatches_ShouldUpdatePassword() {
        // Arrange
        Long userId = 1L;
        String oldPass = "oldPassword";
        String newPass = "newPassword";

        User user = new User();
        user.setId(userId);
        user.setPassword(oldPass);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        // Act
        String updatedPass = userService.updateUserPass(userId, oldPass, newPass);

        // Assert
        assertNotNull(updatedPass);
        assertEquals(newPass, updatedPass);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(user);
        System.out.println("Test 'updateUserPass_UserExistsAndPasswordMatches_ShouldUpdatePassword' passed successfully.");
    }

    @Test
    void updateUserPass_UserExistsButPasswordDoesNotMatch_ShouldReturnNull() {
        // Arrange
        Long userId = 1L;
        String oldPass = "wrongPassword";
        String newPass = "newPassword";

        User user = new User();
        user.setId(userId);
        user.setPassword("correctPassword");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        String updatedPass = userService.updateUserPass(userId, oldPass, newPass);

        // Assert
        assertNull(updatedPass);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(user);
        System.out.println("Test 'updateUserPass_UserExistsButPasswordDoesNotMatch_ShouldReturnNull' passed successfully.");
    }

    @Test
    void getAllUsers_UsersExist_ShouldReturnListOfUserDtos() {
        // Arrange
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);
        List<User> users = List.of(user1, user2);

        UserDto userDto1 = new UserDto();
        userDto1.setId(1L);
        UserDto userDto2 = new UserDto();
        userDto2.setId(2L);
        List<UserDto> expectedUserDtos = List.of(userDto1, userDto2);

        when(userRepository.findAll()).thenReturn(users);
        mockedUserConverter.when(() -> UserConverter.toDto(user1)).thenReturn(userDto1);
        mockedUserConverter.when(() -> UserConverter.toDto(user2)).thenReturn(userDto2);

        // Act
        List<UserDto> actualUserDtos = userService.getAllUsers();

        // Assert
        assertNotNull(actualUserDtos);
        assertEquals(expectedUserDtos.size(), actualUserDtos.size());
        verify(userRepository, times(1)).findAll();
        System.out.println("Test 'getAllUsers_UsersExist_ShouldReturnListOfUserDtos' passed successfully.");
    }
}