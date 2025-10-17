package com.JBFinancial.JBFinancial_backend.controller;

import com.JBFinancial.JBFinancial_backend.domain.segmento.*;
import com.JBFinancial.JBFinancial_backend.repositories.SegmentoRepository;
import com.JBFinancial.JBFinancial_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("segmento")
public class SegmentoController {

    @Autowired
    private SegmentoRepository segmentoRepository;

    @Autowired
    private UserRepository userRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public void saveSegmento(@Valid @RequestBody SegmentoRequestDTO data){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();

        if (segmentoRepository.existsByNomeAndUserId(data.nome().toUpperCase(), userId)) {
            throw new RuntimeException("Segmento com este nome já existe.");
        }

        Segmento segmento = new Segmento(data);
        segmento.setUserId(userId);
        segmentoRepository.save(segmento);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<SegmentoResponseDTO> getAll(){
        return segmentoRepository.findAll().stream().map(SegmentoResponseDTO::new).toList();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/user")
    public List<SegmentoResponseDTO> getSegmentosByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();
        return segmentoRepository.findByUserId(userId).stream().map(SegmentoResponseDTO::new).toList();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public void updateSegmento(@PathVariable UUID id, @Valid @RequestBody SegmentoRequestDTO data){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();

        Segmento segmento = segmentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Segmento não encontrado"));
        if (!segmento.getUserId().equals(userId)) {
            throw new RuntimeException("Usuário não autorizado a atualizar este segmento");
        }

        if (segmentoRepository.existsByNomeAndUserIdAndIdNot(data.nome().toUpperCase(), userId, id)) {
            throw new RuntimeException("Segmento com este nome já existe.");
        }

        segmento.setNome(data.nome());
        segmentoRepository.save(segmento);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public void deleteSegmento(@PathVariable UUID id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();

        Segmento segmento = segmentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Segmento não encontrado"));
        if (!segmento.getUserId().equals(userId)) {
            throw new RuntimeException("Usuário não autorizado a deletar este segmento");
        }
        segmentoRepository.deleteById(id);
    }
}
