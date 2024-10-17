package org.svendzen.userservice.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.svendzen.userservice.dtos.UserDTO;
import org.svendzen.userservice.models.User;
import org.svendzen.userservice.services.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.findUserByUsername(username);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findUserById(id);
        if(user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Validated(UserDTO.Registration.class) @RequestBody UserDTO userDTO) {
        User registeredUser = userService.registerUser(userDTO.convertToUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();  // HTTP status 204 returned on successful delete
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticateUser(@Validated(UserDTO.Authentication.class) @RequestBody UserDTO userDTO) {
        boolean isAuthenticated = userService.authenticateUser(userDTO.convertToUser().getUsername(), userDTO.convertToUser().getPassword());

        if(isAuthenticated) {
            return ResponseEntity.ok("User Authenticated Successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
