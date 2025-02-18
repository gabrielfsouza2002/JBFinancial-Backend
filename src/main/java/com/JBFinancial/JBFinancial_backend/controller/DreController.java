// src/main/java/com/JBFinancial/JBFinancial_backend/controller/DreController.java

package com.JBFinancial.JBFinancial_backend.controller;

import com.JBFinancial.JBFinancial_backend.Services.DreResponseDTO;
import com.JBFinancial.JBFinancial_backend.Services.DreService;
import com.JBFinancial.JBFinancial_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dre")
public class DreController {
    @Autowired
    private DreService dreService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/calculo/{year}")
    public List<DreResponseDTO> calculateDre(@PathVariable int year) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();
        return dreService.calculateDre(userId, year);
    }
}

