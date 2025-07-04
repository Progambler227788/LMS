package com.codingwork.lms.dto.request.user;


import com.codingwork.lms.entity.enums.Role;
import jakarta.validation.constraints.*;

public record CreateUserRequest(
        @NotBlank @Size(min=3, max=50) String username,
        @NotBlank @Email String email,
        @NotBlank @Size(min=8) String password,
        @NotNull Role role,
        @NotBlank String firstName,
        String lastName
) {}
