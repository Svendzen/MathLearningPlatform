package org.svendzen.userservice.controllers;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.svendzen.userservice.dtos.UserDTO;
import org.svendzen.userservice.models.User;
import org.svendzen.userservice.security.JwtUtil;
import org.svendzen.userservice.services.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.findUserByUsername(username);
        if (user.isPresent()) {
            // Map to UserDTO without exposing sensitive information like password
            UserDTO userDTO = new UserDTO(
                    user.get().getUsername(),
                    null, // Do not expose the password
                    user.get().getFirst_name(),
                    user.get().getLast_name(),
                    user.get().getRole()
            );
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findUserById(id);
        if (user.isPresent()) {
            // Map to UserDTO without exposing sensitive information
            UserDTO userDTO = new UserDTO(
                    user.get().getUsername(),
                    null, // Do not expose the password
                    user.get().getFirst_name(),
                    user.get().getLast_name(),
                    user.get().getRole()
            );
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated(UserDTO.Registration.class) @RequestBody UserDTO userDTO) {
        try {
            User registeredUser = userService.registerUser(userDTO.convertToUser());
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();  // HTTP status 204 returned on successful delete
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@Validated(UserDTO.Authentication.class) @RequestBody UserDTO userDTO) {
        boolean isAuthenticated = userService.authenticateUser(userDTO.getUsername(), userDTO.getPassword());

        if (isAuthenticated) {
            Optional<User> user = userService.findUserByUsername(userDTO.getUsername());
            if (user.isPresent()) {
                // Generate token with username and role
                String token = jwtUtil.generateToken(user.get().getUsername(), user.get().getRole().name());
                return ResponseEntity.ok(new AuthResponse(token, user.get().getRole().name()));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    // Inner class to return both token and role
    @Getter
    public static class AuthResponse {
        private final String token;
        private final String role;

        public AuthResponse(String token, String role) {
            this.token = token;
            this.role = role;
        }

    }

}
