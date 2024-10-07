package org.svendzen.userservice.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UsernameValidationTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void whenUsernameIsValid_thenNoConstraintViolations() {
        User user = new User();
        user.setUsername("validuser@mail.com"); // Valid email format
        user.setPassword("Valid123");
        user.setRole(Role.STUDENT);
        user.setFirst_name("John");
        user.setLast_name("Doe");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "Valid username should not produce constraint violations");
    }

    @Test
    public void whenUsernameIsMissing_thenValidationFails() {
        User user = new User();
        user.setPassword("Valid123");
        user.setRole(Role.STUDENT);
        user.setFirst_name("John");
        user.setLast_name("Doe");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("Username is required"), "Missing username should fail validation");
    }

    @Test
    public void whenUsernameIsInvalidEmail_thenValidationFails() {
        User user = new User();
        user.setUsername("invalid-email@"); // Invalid email format
        user.setPassword("Valid123");
        user.setRole(Role.STUDENT);
        user.setFirst_name("John");
        user.setLast_name("Doe");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Print the violation messages
        for (ConstraintViolation<User> violation : violations) {
            System.out.println("Violation: " + violation.getMessage());
        }

        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("Please provide a valid e-mail address"),
                "Invalid email should fail validation");
    }

    @Test
    public void whenUsernameIsEmpty_thenValidationFails() {
        User user = new User();
        user.setUsername(""); // Empty email
        user.setPassword("Valid123");
        user.setRole(Role.STUDENT);
        user.setFirst_name("John");
        user.setLast_name("Doe");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("Username is required"), "Empty username should fail validation");
    }
}
