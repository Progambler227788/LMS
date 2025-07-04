package com.codingwork.lms.dto.response;

import com.codingwork.lms.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO used for returning user details safely.
 */
@Data
@AllArgsConstructor
public class UserResponseDTO {
    private String id;
    private String email;
    private Role role;
}

