package com.JBFinancial.JBFinancial_backend.controller;

import com.JBFinancial.JBFinancial_backend.domain.grupo.*;
import com.JBFinancial.JBFinancial_backend.repositories.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("grupo")
public class GrupoController {

    @Autowired
    private GrupoRepository grupoRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public void saveGrupo(@Valid @RequestBody GrupoRequestDTO data) {
        Grupo grupo = new Grupo();
        grupo.setNome(data.nome());
        grupo.setDigitoGrupo(generateNextGrupoDigit());
        grupoRepository.save(grupo);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<GrupoResponseDTO> getAll() {
        return grupoRepository.findAll().stream().map(GrupoResponseDTO::new).toList();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public void updateGrupo(@PathVariable UUID id, @Valid @RequestBody GrupoRequestDTO data) {
        Grupo grupo = grupoRepository.findById(id).orElseThrow(() -> new RuntimeException("Grupo not found"));
        grupo.setNome(data.nome());
        grupoRepository.save(grupo);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public void deleteGrupo(@PathVariable UUID id) {
        grupoRepository.deleteById(id);
    }

    private String generateNextGrupoDigit() {
        long count = grupoRepository.count();
        return String.valueOf((count + 1) % 10);
    }
}