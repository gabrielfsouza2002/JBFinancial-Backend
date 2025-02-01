package com.JBFinancial.JBFinancial_backend.domain.user;

public record RegisterDTO(String login, String password, String email, String cnpj, String name, UserRole role) {
}