package org.svendzen.userservice.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.svendzen.userservice.models.Role;
import org.svendzen.userservice.models.User;
import org.svendzen.userservice.repositories.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User student;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create a test user (no id)
        student = new User();
        student.setUsername("student@mail.com");
        student.setPassword("password123");
        student.setRole(Role.STUDENT);
        student.setFirst_name("Ted");
        student.setLast_name("Mosby");
    }

    @Test
    void shouldFindUserByUsername_WhenUserExists() {
        when(userRepository.findByUsername("student@mail.com")).thenReturn(Optional.of(student));

        Optional<User> foundUser = userService.findUserByUsername("student@mail.com");

        assertTrue(foundUser.isPresent());
        assertEquals("student@mail.com", foundUser.get().getUsername());
        verify(userRepository, times(1)).findByUsername("student@mail.com");
    }

    @Test
    void shouldNotFindUserByUsername_WhenUserDoesNotExist() {
        when(userRepository.findByUsername("nonexistent@mail.com")).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.findUserByUsername("nonexistent@mail.com");

        assertFalse(foundUser.isPresent());
        verify(userRepository, times(1)).findByUsername("nonexistent@mail.com");
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        when(userRepository.save(any(User.class))).thenReturn(student);

        User registeredUser = userService.registerUser(student);

        assertNotNull(registeredUser);
        assertEquals("student@mail.com", registeredUser.getUsername());
        verify(userRepository, times(1)).save(student);
    }

    @Test
    void shouldFindUserById_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(student));

        Optional<User> foundUser = userService.findUserById(1L);

        assertTrue(foundUser.isPresent());
        assertEquals("Ted", foundUser.get().getFirst_name());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void shouldNotFindUserById_WhenUserDoesNotExist() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.findUserById(99L);

        assertFalse(foundUser.isPresent());
        verify(userRepository, times(1)).findById(99L);
    }

    @Test
    void shouldDeleteUser_WhenUserExists() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldNotDeleteUser_WhenUserDoesNotExist() {
        when(userRepository.existsById(99L)).thenReturn(false);

        userService.deleteUser(99L);

        verify(userRepository, never()).deleteById(99L);
        verify(userRepository, times(1)).existsById(99L);
    }
}
