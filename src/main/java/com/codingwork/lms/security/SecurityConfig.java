package com.codingwork.lms.security;

import com.codingwork.lms.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
// Defines security rules and adds the JWT filter to the security filter chain.

/*
Disable CSRF (since JWT is stateless).
Define public and protected routes (e.g., /api/auth/** is public, /api/auth/user/** is restricted).
Use JwtFilter before UsernamePasswordAuthenticationFilter to process JWT authentication.
*/
public class SecurityConfig {

    // Constructor Base Injection
    private final JwtFilter authFilter;

    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(JwtFilter jwtFilter, PasswordEncoder passwordEncoder) {
        this.authFilter = jwtFilter;
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl(); // Return the user details service
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("*")); // Allow all origins
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                    config.setAllowedHeaders(List.of("*"));
                    return config;
                }))
                .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**",
                                "/v3/**", "/swagger-ui/**", "/swagger-ui.html", "/swagger/**", "/webjars/**", "/lms/**", "/").permitAll()  // Allow all auth routes and swagger
                        .requestMatchers("/api/instructor/**").hasRole("INSTRUCTOR")  // Restrict instructor routes
                        .requestMatchers("/api/user/**").hasRole("USER")  // Restrict user routes
                        .anyRequest().authenticated() // Protect all other endpoints
                )
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No sessions
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter,  UsernamePasswordAuthenticationFilter.class); // Add JWT filter

        return http.build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

}

