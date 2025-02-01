package com.JBFinancial.JBFinancial_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.JBFinancial.JBFinancial_backend.domain.user.User;

public interface UserRepository extends JpaRepository<User, String> {
    User findByLogin(String login);
}