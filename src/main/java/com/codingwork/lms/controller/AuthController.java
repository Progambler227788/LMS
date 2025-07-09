package com.codingwork.lms.controller;


import com.codingwork.lms.dto.request.auth.LoginRequest;
import com.codingwork.lms.dto.request.auth.SignUpRequest;
import com.codingwork.lms.dto.response.user.UserResponse;
import com.codingwork.lms.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Handles user authentication (login), registration, and logout.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentication", description = "Handles user login, registration, and logout")
public class AuthController {

    private final AuthService authService;

    /**
     * Authenticates a user and sets JWT token as an HttpOnly cookie.
     * @param requestDTO Login credentials (email, password)
     * @param response HttpServletResponse for setting cookies
     * @return Authenticated user info
     */
    @Operation(summary = "Login", description = "Authenticate user and return JWT token in cookie")
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest requestDTO,
                                                 HttpServletResponse response) {
        log.info("Login request: {}", requestDTO);
        UserResponse user = authService.login(requestDTO, response);
        return ResponseEntity.ok(user);
    }

    /**
     * Registers a new user.
     * @param requestDTO User registration data
     * @return Created user info
     */
    @Operation(summary = "Register", description = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody SignUpRequest requestDTO) {
        log.info("Register request: {}", requestDTO);
        UserResponse createdUser = authService.register(requestDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Logs the user out by clearing the JWT cookie.
     * @param response HttpServletResponse for clearing cookies
     */
    @Operation(summary = "Logout", description = "Logout user and clear JWT cookie")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        authService.logout(response);
        return ResponseEntity.ok().build();
    }



    @Operation(summary = "Get current user", description = "Returns currently authenticated user from JWT token")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        log.info("Get current user");
        return ResponseEntity.ok(authService.getCurrentUser());
    }

}

