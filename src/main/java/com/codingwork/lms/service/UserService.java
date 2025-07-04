package com.codingwork.lms.service;

import com.codingwork.lms.dto.request.user.*;
import com.codingwork.lms.dto.response.user.*;
import java.util.List;

public interface UserService {
    // Creates a new user
    UserResponse createUser(CreateUserRequest request);
    // Retrieves a user by id
    UserResponse getUserById(String id);
    // Retrieves all users
    List<UserResponse> getAllUsers();
    // Updates a user
    UserResponse updateUser(String id, UpdateUserRequest request);
    // Deletes a user
    void deleteUser(String id);
    // Retrieves a user by username
    UserResponse findByUsername(String username);
    // Retrieves a user by email
    UserResponse findByEmail(String email);
}