package com.codingwork.lms.dto.request.user;


import jakarta.validation.constraints.*;

public record UpdateUserRequest(
        @Size(min=3, max=50) String username,
        @Email String email,
        @Size(min=8) String password,
        String firstName,
        String lastName
) {}
