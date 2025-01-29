package com.JBFinancial.JBFinancial_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.JBFinancial.JBFinancial_backend.user.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    UserDetails findByEmail(String email);
}
