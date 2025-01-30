package com.JBFinancial.JBFinancial_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.JBFinancial.JBFinancial_backend.domain.user.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByLogin(String login);
}
