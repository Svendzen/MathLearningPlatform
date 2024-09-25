package org.svendzen.userservice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;


@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE) // To prevent the ID being set manually
    private Long id;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    @Setter(AccessLevel.NONE) // I don't want generated setter here, use custom setter with encryption method instead
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    /*
    public void setPassword(@NotBlank(message = "Password is required") String password) {
        this.password = password; // Use encryption method to set password here
    }
    */
}
