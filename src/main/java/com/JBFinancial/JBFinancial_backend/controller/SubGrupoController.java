package com.JBFinancial.JBFinancial_backend.controller;

import com.JBFinancial.JBFinancial_backend.subgrupo.*;
import com.JBFinancial.JBFinancial_backend.repositories.SubgrupoRepository;
import com.JBFinancial.JBFinancial_backend.repositories.UserRepository;
import com.JBFinancial.JBFinancial_backend.subgrupo.SubgrupoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("subgrupo")
public class SubGrupoController {

    @Autowired
    private SubgrupoRepository subgrupoRepository;

    @Autowired
    private UserRepository userRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/user")
    public void saveSubgrupoPorUsuario(@Valid @RequestBody SubgrupoRequestDTO data) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();

        Subgrupo subgrupo = new Subgrupo();
        subgrupo.setIdUser(userId);
        subgrupo.setNome(data.nome());
        subgrupo.setIdGrupo(data.idGrupo());
        subgrupoRepository.save(subgrupo);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/user")
    public List<SubgrupoResponseDTO> getSubgruposByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();
        return subgrupoRepository.findByIdUser(userId).stream().map(SubgrupoResponseDTO::new).toList();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<SubgrupoResponseDTO> getAll() {
        return subgrupoRepository.findAll().stream().map(SubgrupoResponseDTO::new).toList();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public void updateSubgrupo(@PathVariable UUID id, @Valid @RequestBody SubgrupoRequestDTO data) {
        Subgrupo subgrupo = subgrupoRepository.findById(id).orElseThrow(() -> new RuntimeException("Subgrupo not found"));
        subgrupo.setIdUser(data.idUser());
        subgrupo.setNome(data.nome());
        subgrupo.setIdGrupo(data.idGrupo());
        subgrupoRepository.save(subgrupo);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public void deleteSubgrupo(@PathVariable UUID id) {
        subgrupoRepository.deleteById(id);
    }
}