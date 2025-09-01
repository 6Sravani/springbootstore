package com.Ecommerce.store.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 255,min = 0,message = "Name must be less than 255 characters")
    private String name;

    @NotBlank(message = "Email is requires")
    @Email(message = "Email must be valid")
    @LowerCase(message = "Email must be in lower case")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 6,max = 25, message ="Password must be at least 6 to 25  characters")
    private String password;
}
