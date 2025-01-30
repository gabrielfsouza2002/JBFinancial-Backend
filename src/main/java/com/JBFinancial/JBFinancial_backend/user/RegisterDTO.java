package com.JBFinancial.JBFinancial_backend.user;

public record RegisterDTO(String login, String password, UserRole role) {
}
