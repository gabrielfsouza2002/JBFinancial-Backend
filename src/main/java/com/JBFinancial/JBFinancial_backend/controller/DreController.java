// src/main/java/com/JBFinancial/JBFinancial_backend/controller/DreController.java

package com.JBFinancial.JBFinancial_backend.controller;

import com.JBFinancial.JBFinancial_backend.domain.dre.DreRequestDTO;
import com.JBFinancial.JBFinancial_backend.domain.dre.DreResponseDTO;
import com.JBFinancial.JBFinancial_backend.Services.DreService;
import com.JBFinancial.JBFinancial_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dre")
public class DreController {
    @Autowired
    private DreService dreService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<DreResponseDTO> getAllDre() {
        return dreService.getAllDre().stream().map(DreResponseDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/user")
    public List<DreResponseDTO> getDreByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();
        return dreService.getDreByUserId(userId).stream().map(DreResponseDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public DreResponseDTO getDreById(@PathVariable UUID id) {
        return new DreResponseDTO(dreService.getDreById(id));
    }

    @GetMapping("/calculate/{year}")
    public List<DreResponseDTO> calculateDre(@PathVariable int year) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();
        return dreService.calculateAnnualTotals(userId, year);
    }

    @GetMapping("/user/{year}")
    public List<DreResponseDTO> getDreByYear(@PathVariable int year) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();
        return dreService.getDreByUserId(userId).stream()
                .filter(dre -> dre.getYear() == year)
                .map(DreResponseDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/user/{year}/{month}")
    public List<DreResponseDTO> getDreByYearAndMonth(@PathVariable int year, @PathVariable int month) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();
        return dreService.getDreByUserId(userId).stream()
                .filter(dre -> dre.getYear() == year && dre.getMonth() == month)
                .map(DreResponseDTO::new)
                .collect(Collectors.toList());
    }
}