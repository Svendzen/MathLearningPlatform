package org.svendzen.userservice.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.svendzen.userservice.models.Role;
import org.svendzen.userservice.models.User;
import org.svendzen.userservice.services.UserService;

@Component
public class TestDataLoader {

    private final UserService userService;

    public TestDataLoader(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void loadTestData() {
        if (userService.findUserByUsername("student1@mail.com").isEmpty()) {
            User student1 = new User();
            student1.setUsername("student1@mail.com");
            student1.setPassword("password123");
            student1.setRole(Role.STUDENT);
            student1.setFirst_name("Timmy");
            student1.setLast_name("Turner");
            userService.registerUser(student1);
        }

        if (userService.findUserByUsername("student2@mail.com").isEmpty()) {
            User student2 = new User();
            student2.setUsername("student2@mail.com");
            student2.setPassword("password123");
            student2.setRole(Role.STUDENT);
            student2.setFirst_name("Trixie");
            student2.setLast_name("Tang");
            userService.registerUser(student2);
        }

        if (userService.findUserByUsername("teacher@mail.com").isEmpty()) {
            User teacher = new User();
            teacher.setUsername("teacher@mail.com");
            teacher.setPassword("password123");
            teacher.setRole(Role.TEACHER);
            teacher.setFirst_name("Denzel");
            teacher.setLast_name("Crocker");
            userService.registerUser(teacher);
        }

        if (userService.findUserByUsername("parent@mail.com").isEmpty()){
            User parent = new User();
            parent.setUsername("parent@mail.com");
            parent.setPassword("password123");
            parent.setRole(Role.PARENT);
            parent.setFirst_name("Wanda");
            parent.setLast_name("Cosmo");
            userService.registerUser(parent);
        }
    }
}
