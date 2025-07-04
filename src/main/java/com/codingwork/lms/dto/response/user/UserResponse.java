package com.codingwork.lms.dto.response.user;


import com.codingwork.lms.entity.enums.Role;

public record UserResponse(
        String id,
        String username,
        String email,
        Role role
) {}
