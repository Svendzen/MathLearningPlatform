package org.svendzen.userservice.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.svendzen.userservice.models.Role;
import org.svendzen.userservice.models.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    // declare instance variables
    private User student;
    private User teacher;
    private User parent;

    @BeforeEach
    void setUp() {
        student = new User();
        student.setUsername("student@mail.com");
        student.setPassword("password123");
        student.setRole(Role.STUDENT);
        student.setFirst_name("Ted");
        student.setLast_name("Mosby");
        userRepository.save(student);

        teacher = new User();
        teacher.setUsername("teacher@mail.com");
        teacher.setPassword("password123");
        teacher.setRole(Role.TEACHER);
        teacher.setFirst_name("Lily");
        teacher.setLast_name("Aldrin");
        userRepository.save(teacher);

        parent = new User();
        parent.setUsername("parent@mail.com");
        parent.setPassword("password123");
        parent.setRole(Role.PARENT);
        parent.setFirst_name("Marshall");
        parent.setLast_name("Eriksen");
        userRepository.save(parent);
    }

    @Test
    void userShouldHaveUsername() {
        assertEquals("student@mail.com", student.getUsername());
    }

    @Test
    void userShouldHaveFirstName() {
        assertEquals("Lily", teacher.getFirst_name());
    }

    @Test
    void userShouldHaveLastName() {
        assertEquals("Mosby", student.getLast_name());
    }

    @Test
    void userShouldHavePassword() {
        assertEquals("password123", parent.getPassword());
    }

    @Test
    void allRolesShouldExist() {
        assertEquals(Role.STUDENT, student.getRole());
        assertEquals(Role.TEACHER, teacher.getRole());
        assertEquals(Role.PARENT, parent.getRole());
    }

    @Test
    void shouldFindByUsername() {
        Optional<User> foundUser = userRepository.findByUsername("student@mail.com");
        assertTrue(foundUser.isPresent());
    }

    @Test
    void shouldFindById() {
        Optional<User> foundUser = userRepository.findById(student.getId());
        assertTrue(foundUser.isPresent());
        assertEquals("Ted", foundUser.get().getFirst_name());
    }

    @Test
    void shouldDeleteUserById() {
        userRepository.deleteById(student.getId());
        Optional<User> deletedUser = userRepository.findById(student.getId());
        assertFalse(deletedUser.isPresent());
    }

    @Test
    void shouldFindAllUsers() {
        List<User> users = userRepository.findAll();
        assertEquals(3, users.size());  // Since I create 3 users in setUp()
    }

}

