package com.codingwork.lms.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public String secretKey(Dotenv dotenv) {
        return dotenv.get("SECRET_KEY");
    }

    @Bean
    public String mongoUri(Dotenv dotenv) {
        return dotenv.get("MONGO_URI");
    }

    // Then inject these beans where needed
}
