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

public class UserPasswordValidationTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void whenPasswordIsValid_thenNoConstraintViolations() {
        User user = new User();
        user.setUsername("validuser@mail.com");
        user.setPassword("Valid123"); // Password meets all rules
        user.setRole(Role.STUDENT);
        user.setFirst_name("John");
        user.setLast_name("Doe");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "Valid password should not produce constraint violations");
    }

    @Test
    public void whenPasswordTooShort_thenValidationFails() {
        User user = new User();
        user.setUsername("validuser@mail.com");
        user.setPassword("Short1"); // Too short
        user.setRole(Role.STUDENT);
        user.setFirst_name("John");
        user.setLast_name("Doe");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("at least 8 characters"), "Password too short");
    }

    @Test
    public void whenPasswordMissingUppercase_thenValidationFails() {
        User user = new User();
        user.setUsername("validuser@mail.com");
        user.setPassword("valid123"); // No uppercase letter
        user.setRole(Role.STUDENT);
        user.setFirst_name("John");
        user.setLast_name("Doe");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("one uppercase letter"), "Password missing uppercase letter");
    }

    @Test
    public void whenPasswordMissingLowercase_thenValidationFails() {
        User user = new User();
        user.setUsername("validuser@mail.com");
        user.setPassword("VALID123"); // No lowercase letter
        user.setRole(Role.STUDENT);
        user.setFirst_name("John");
        user.setLast_name("Doe");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("one lowercase letter"), "Password missing lowercase letter");
    }

    @Test
    public void whenPasswordMissingDigit_thenValidationFails() {
        User user = new User();
        user.setUsername("validuser@mail.com");
        user.setPassword("Validpass"); // No digit
        user.setRole(Role.STUDENT);
        user.setFirst_name("John");
        user.setLast_name("Doe");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("one digit"), "Password missing digit");
    }
}
