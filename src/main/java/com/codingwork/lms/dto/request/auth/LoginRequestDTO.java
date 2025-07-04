package com.codingwork.lms.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO for user login request.
 */
@Data
public class LoginRequestDTO {


    @NotBlank(message = "UserName is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}
