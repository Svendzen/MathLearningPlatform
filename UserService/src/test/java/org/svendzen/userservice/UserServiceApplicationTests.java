package org.svendzen.userservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.svendzen.userservice.models.Role;
import org.svendzen.userservice.models.User;
import org.svendzen.userservice.services.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceApplicationTests {

	@Autowired
	private UserService userService;

	private User student;
	private User teacher;

	@BeforeEach
	void setUp() {
		student = new User();
		student.setUsername("student@mail.com");
		student.setPassword("password123");
		student.setRole(Role.STUDENT);
		student.setFirst_name("Ted");
		student.setLast_name("Mosby");
		userService.registerUser(student);

		teacher = new User();
		teacher.setUsername("teacher@mail.com");
		teacher.setPassword("password123");
		teacher.setRole(Role.TEACHER);
		teacher.setFirst_name("Lily");
		teacher.setLast_name("Aldrin");
		userService.registerUser(teacher);
	}

	@Test
	void contextLoads() {
		// Ensure that the application context loads successfully
		assertNotNull(userService);
	}

	@Test
	void shouldRegisterUser() {
		// Test user registration
		User parent = new User();
		parent.setUsername("parent@mail.com");
		parent.setPassword("password123");
		parent.setRole(Role.PARENT);
		parent.setFirst_name("Marshall");
		parent.setLast_name("Eriksen");

		User savedParent = userService.registerUser(parent);

		assertNotNull(savedParent.getId());
		assertEquals("parent@mail.com", savedParent.getUsername());
	}

	@Test
	void shouldFindUserByUsername() {
		// Test finding a user by username
		Optional<User> foundUser = userService.findUserByUsername("student@mail.com");
		assertTrue(foundUser.isPresent());
		assertEquals("Ted", foundUser.get().getFirst_name());
	}

	@Test
	void shouldFindUserById() {
		// Test finding a user by ID
		Optional<User> foundUser = userService.findById(student.getId());
		assertTrue(foundUser.isPresent());
		assertEquals("Ted", foundUser.get().getFirst_name());
	}

	@Test
	void shouldDeleteUserById() {
		// Test deleting a user by ID
		userService.deleteUser(teacher.getId());
		Optional<User> deletedUser = userService.findById(teacher.getId());
		assertFalse(deletedUser.isPresent());
	}

	@Test
	void shouldNotFindNonExistentUser() {
		// Test finding a non-existent user
		Optional<User> nonExistentUser = userService.findUserByUsername("nonexistent@mail.com");
		assertFalse(nonExistentUser.isPresent());
	}
}
