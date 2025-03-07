// src/main/java/com/JBFinancial/JBFinancial_backend/controller/AuthenticationController.java

package com.JBFinancial.JBFinancial_backend.controller;

import com.JBFinancial.JBFinancial_backend.Infra.security.TokenService;
import com.JBFinancial.JBFinancial_backend.domain.user.*;
import com.JBFinancial.JBFinancial_backend.repositories.RefreshTokenRepository;
import com.JBFinancial.JBFinancial_backend.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var user = (User) auth.getPrincipal();
        var accessToken = tokenService.generateAccessToken(user);
        var refreshToken = tokenService.generateRefreshToken(user);

        refreshTokenRepository.save(new RefreshToken(null, refreshToken, user.getId(), Instant.now().plusSeconds(7 * 24 * 60 * 60)));

        return ResponseEntity.ok(new LoginResponseDTO(accessToken, refreshToken));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.email(), data.cnpj(), data.name(), data.role(), data.saldoInicial());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            String refreshToken = request.getHeader("Authorization").replace("Bearer ", "");
            refreshTokenRepository.deleteByToken(refreshToken);
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity refresh(@RequestBody String refreshToken) {
        Optional<RefreshToken> optionalToken = refreshTokenRepository.findByToken(refreshToken);
        if (optionalToken.isPresent() && optionalToken.get().getExpiryDate().isAfter(Instant.now())) {
            var user = repository.findById(optionalToken.get().getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
            var newAccessToken = tokenService.generateAccessToken(user);
            return ResponseEntity.ok(new LoginResponseDTO(newAccessToken, refreshToken));
        } else {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
        }
    }

}