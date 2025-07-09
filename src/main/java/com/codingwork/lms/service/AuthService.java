package com.codingwork.lms.service;


import com.codingwork.lms.dto.request.auth.LoginRequest;
import com.codingwork.lms.dto.request.auth.SignUpRequest;
import com.codingwork.lms.dto.response.user.UserResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    /**
     * Authenticates the user and sets JWT token in cookie.
     */
    UserResponse login(LoginRequest requestDTO, HttpServletResponse response);

    /**
     * Registers a new user.
     */
    UserResponse register(SignUpRequest requestDTO);

    /**
     * Logs out the user by removing JWT cookie.
     */
    void logout(HttpServletResponse response);


    UserResponse getCurrentUser();

}
