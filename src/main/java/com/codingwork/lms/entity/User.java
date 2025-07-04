package com.codingwork.lms.entity;


import com.codingwork.lms.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;   // Unique username for login, JWT claims
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role; // Enum: STUDENT, INSTRUCTOR
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
