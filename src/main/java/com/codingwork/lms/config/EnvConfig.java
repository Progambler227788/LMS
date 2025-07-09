package com.codingwork.lms.config;


import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {
    @Bean
    public Dotenv dotenv() {
        return Dotenv.configure()
                .ignoreIfMissing()  // Don't fail if .env is missing
                .load();
    }
}