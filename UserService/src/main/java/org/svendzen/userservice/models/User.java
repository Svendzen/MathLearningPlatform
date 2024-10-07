package org.svendzen.userservice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE) // To prevent the ID being set manually
    private Long id;

    @NotBlank(message = "Username is required")
    @Email(message = "Please provide a valid e-mail address")
    @Column(unique = true)          // Unique usernames is enforced
    private String username;    // Must be a valid e-mail address


    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter")
    @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least one lowercase letter")
    @Pattern(regexp = ".*\\d.*", message = "Password must contain at least one digit")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @NotBlank(message = "First Name is required")
    private String first_name;

    @NotBlank(message = "Last Name is required")
    private String last_name;
}
