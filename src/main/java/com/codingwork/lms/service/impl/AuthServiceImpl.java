package com.codingwork.lms.service.impl;

import com.codingwork.lms.dto.request.auth.LoginRequest;
import com.codingwork.lms.dto.request.auth.SignUpRequest;
import com.codingwork.lms.dto.response.user.UserResponse;
import com.codingwork.lms.entity.User;
import com.codingwork.lms.exception.DuplicateResourceException;
import com.codingwork.lms.exception.InvalidCredentialsException;
import com.codingwork.lms.repository.UserRepository;
import com.codingwork.lms.security.JwtUtil;
import com.codingwork.lms.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public UserResponse login(LoginRequest requestDTO, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDTO.getUsername(),
                            requestDTO.getPassword()
                    )
            );

            // Extract the first authority (role) for token generation
            String role = authentication.getAuthorities().stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElseThrow(() -> new InvalidCredentialsException("No roles assigned"));

            String token = jwtUtil.generateToken(authentication.getName(), role);

            // Set secure cookie
            Cookie cookie = new Cookie("jwt_token", token);
            cookie.setHttpOnly(true); // JS can't access it
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(86400); // 1 day
            response.addCookie(cookie);

            User user = userRepository.findByUsername(requestDTO.getUsername())
                    .orElseThrow(() -> new InvalidCredentialsException("User not found"));

            return new UserResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole()
            );
        } catch (Exception e) {
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }

    @Override
    public UserResponse register(SignUpRequest requestDTO) {
        // Check for existing username
        if ( userRepository.findByUsername(requestDTO.getUsername()).isPresent() ) {
            throw new DuplicateResourceException("Username already taken");
        }
         // Check for existing email
        if ( userRepository.findByEmail(requestDTO.getEmail()).isPresent() ) {
            throw new DuplicateResourceException("Email already registered");
        }

        User newUser = User.builder()
                .email(requestDTO.getEmail())
                .username(requestDTO.getUsername())
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .firstName(requestDTO.getFirstName())
                .lastName(requestDTO.getLastName())
                .role(requestDTO.getRole())
                .build();

        User savedUser = userRepository.save(newUser);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }

    @Override
    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt_token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // JS can't access it
        cookie.setPath("/"); // Remove cookie from all paths
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}