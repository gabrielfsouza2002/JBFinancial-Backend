package com.JBFinancial.JBFinancial_backend.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @PostConstruct
    public void logDatabaseConfig() {
        System.out.println("===========================================");
        System.out.println("DATABASE CONFIGURATION:");
        System.out.println("URL: " + url);
        System.out.println("Username: " + username);
        System.out.println("Username length: " + username.length());
        System.out.println("===========================================");
    }
}

