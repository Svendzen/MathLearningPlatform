package org.svendzen.userservice.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svendzen.userservice.models.User;
import org.svendzen.userservice.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findUserByUsername(String username) {
        log.info("Searching for user with username: {}", username);

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            log.info("User found: {}", user.get());
        }  else {
            log.warn("User not found with username {}", username);
        }

        return user;
    }

    public User registerUser(User user) {
        log.info("Registering new user: {}", user.getUsername());

        // Custom logic like password encryption can go here
        User savedUser = userRepository.save(user);

        log.info("User registered successfully with ID: {}", savedUser.getId());
        return savedUser;
    }

    public Optional<User> findById(Long id) {
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
}

