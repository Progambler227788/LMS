package com.codingwork.lms.controller;


import com.codingwork.lms.dto.request.auth.LoginRequestDTO;
import com.codingwork.lms.dto.request.auth.SignUpRequestDTO;
import com.codingwork.lms.dto.response.UserResponseDTO;
import com.codingwork.lms.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Handles user authentication (login), registration, and logout.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Authenticates a user and sets JWT token as an HttpOnly cookie.
     * @param requestDTO Login credentials (email, password)
     * @param response HttpServletResponse for setting cookies
     * @return Authenticated user info
     */
    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@Valid @RequestBody LoginRequestDTO requestDTO,
                                                 HttpServletResponse response) {
        UserResponseDTO user = authService.login(requestDTO, response);
        return ResponseEntity.ok(user);
    }

    /**
     * Registers a new user.
     * @param requestDTO User registration data
     * @return Created user info
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody SignUpRequestDTO requestDTO) {
        UserResponseDTO createdUser = authService.register(requestDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Logs the user out by clearing the JWT cookie.
     * @param response HttpServletResponse for clearing cookies
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        authService.logout(response);
        return ResponseEntity.ok().build();
    }
}

