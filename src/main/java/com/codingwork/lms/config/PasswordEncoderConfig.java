package com.codingwork.lms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


// This configuration class defines a bean for password encoding using BCrypt.
// For example, it can be used to hash user passwords before storing them in the database.
// Algorithm: BCrypt is a strong hashing function that is resistant to brute-force attacks.
@Configuration
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

