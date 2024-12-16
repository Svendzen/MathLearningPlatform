package org.svendzen.userservice.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.svendzen.userservice.models.Role;
import org.svendzen.userservice.models.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotBlank(groups = {Registration.class, Authentication.class}, message = "Username is required")
    @Email(groups = {Registration.class}, message = "Please provide a valid e-mail address")
    private String username;

    @NotBlank(groups = {Registration.class, Authentication.class}, message = "Password is required")
    @Size(groups = {Registration.class}, min = 8, message = "Password must be at least 8 characters long")
    @Pattern(groups = {Registration.class},
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, and one digit")
    private String password;

    @NotBlank(groups = Registration.class, message = "First name is required")
    private String firstName;

    @NotBlank(groups = Registration.class, message = "Last name is required")
    private String lastName;

    @NotNull(groups = Registration.class, message = "Role is required")
    private Role role; // Enum type: STUDENT, TEACHER, PARENT

    // Validation group interfaces - for context-specific validation
    public interface Registration {}
    public interface Authentication {}

    // Converts from DTO to Entity
    public User convertToUser() {
        User user = new User();
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setFirst_name(this.firstName);
        user.setLast_name(this.lastName);
        user.setRole(this.role);
        return user;
    }
}
