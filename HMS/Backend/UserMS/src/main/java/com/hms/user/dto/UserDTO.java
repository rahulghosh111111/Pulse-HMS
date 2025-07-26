package com.hms.user.dto;

import com.hms.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;
    private Integer phone;
    @NotBlank(message = "Password cannot be blank")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$", message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character and be between 8 and 15 characters long")
    private String password;
    private Roles role;

    public User toEntity() {
        return new User(
                this.id,
                this.name,
                this.email,
                this.phone,
                this.password,
                this.role);
    }
}
