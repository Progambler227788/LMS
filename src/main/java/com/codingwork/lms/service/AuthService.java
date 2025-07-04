package com.codingwork.lms.service;


import com.codingwork.lms.dto.request.auth.LoginRequestDTO;
import com.codingwork.lms.dto.request.auth.SignUpRequestDTO;
import com.codingwork.lms.dto.response.UserResponseDTO;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    /**
     * Authenticates the user and sets JWT token in cookie.
     */
    UserResponseDTO login(LoginRequestDTO requestDTO, HttpServletResponse response);

    /**
     * Registers a new user.
     */
    UserResponseDTO register(SignUpRequestDTO requestDTO);

    /**
     * Logs out the user by removing JWT cookie.
     */
    void logout(HttpServletResponse response);
}
