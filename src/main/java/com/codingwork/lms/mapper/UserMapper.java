package com.codingwork.lms.mapper;

import com.codingwork.lms.dto.response.user.UserResponse;
import com.codingwork.lms.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }
}

