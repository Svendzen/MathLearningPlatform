package org.svendzen.userservice.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.svendzen.userservice.models.Role;
import org.svendzen.userservice.models.User;
import org.svendzen.userservice.repositories.UserRepository;
import org.svendzen.userservice.services.UserService;

@Component
public class TestDataLoader {

    private final UserService userService;

    public TestDataLoader(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void loadTestData() {
        User student1 = new User();
        student1.setUsername("student1@mail.com");
        student1.setPassword("password123");
        student1.setRole(Role.STUDENT);
        student1.setFirst_name("Timmy");
        student1.setLast_name("Turner");

        User student2 = new User();
        student2.setUsername("student2@mail.com");
        student2.setPassword("password123");
        student2.setRole(Role.STUDENT);
        student2.setFirst_name("Trixie");
        student2.setLast_name("Tang");

        User teacher = new User();
        teacher.setUsername("teacher@mail.com");
        teacher.setPassword("password123");
        teacher.setRole(Role.TEACHER);
        teacher.setFirst_name("Denzel");
        teacher.setLast_name("Crocker");

        User parent = new User();
        parent.setUsername("parent@mail.com");
        parent.setPassword("password123");
        parent.setRole(Role.PARENT);
        parent.setFirst_name("Wanda");
        parent.setLast_name("Cosmo");

        // Create test data users with userService
        userService.registerUser(student1);
        userService.registerUser(student2);
        userService.registerUser(teacher);
        userService.registerUser(parent);
    }
}
