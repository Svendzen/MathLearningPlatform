package org.svendzen.userservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.svendzen.userservice.models.User;
import org.svendzen.userservice.repositories.UserRepository;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findUserByUsername(String username) {
        log.info("Searching for user with username: {}", username);

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            log.info("User found: {}", user.get());
        } else {
            log.warn("User not found with username {}", username);
        }

        return user;
    }

    public User registerUser(User user) {
        log.info("Registering new user: {}", user.getUsername());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        log.info("User registered successfully with ID: {}", savedUser.getId());
        return savedUser;
    }

    public Optional<User> findUserById(Long id) {
        log.info("Searching for user with ID: {}", id);

        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            log.info("User found: {}", user.get());
        } else {
            log.warn("User not found with ID: {}", id);
        }

        return user;
    }

    public void deleteUser(Long id) {
        log.info("Deleting user with ID: {}", id);

        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            log.info("User with ID: {} deleted successfully", id);
        } else {
            log.warn("User with ID: {} not found, cannot delete", id);
        }
    }

    public boolean authenticateUser(String username, String rawPassword) {
        log.info("Authenticating user with username: {}", username);

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            boolean matches = passwordEncoder.matches(rawPassword, user.get().getPassword());

            if (matches) {
                log.info("User authenticated successfully");
                return true;
            } else {
                log.warn("Password mismatch for user: {}", username);
            }
        } else {
            log.warn("User not found for authentication with username: {}", username);
        }

        return false;
    }
}
