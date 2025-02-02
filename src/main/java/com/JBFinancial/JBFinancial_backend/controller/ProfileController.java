// src/main/java/com/JBFinancial/JBFinancial_backend/controller/ProfileController.java

package com.JBFinancial.JBFinancial_backend.controller;

import com.JBFinancial.JBFinancial_backend.domain.user.User;
import com.JBFinancial.JBFinancial_backend.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final UserRepository userRepository;

    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<User> getProfile() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByLogin(login);
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<User> updateProfile(@RequestBody User updatedUser) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByLogin(login);
        user.setLogin(updatedUser.getLogin());
        user.setEmail(updatedUser.getEmail());
        user.setCnpj(updatedUser.getCnpj());
        user.setName(updatedUser.getName());
        user.setSaldoInicial(updatedUser.getSaldoInicial());
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
}