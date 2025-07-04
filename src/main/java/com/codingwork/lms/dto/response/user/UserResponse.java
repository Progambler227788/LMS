package com.codingwork.lms.dto.response.user;


import com.codingwork.lms.entity.enums.Role;
import java.time.LocalDateTime;

public record UserResponse(
        String id,
        String username,
        String email,
        Role role,
        String firstName,
        String lastName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
