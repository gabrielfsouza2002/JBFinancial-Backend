// src/main/java/com/JBFinancial/JBFinancial_backend/controller/ContaController.java

package com.JBFinancial.JBFinancial_backend.controller;

import com.JBFinancial.JBFinancial_backend.domain.conta.*;
import com.JBFinancial.JBFinancial_backend.repositories.ContaRepository;
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
@RequestMapping("conta")
public class ContaController {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private UserRepository userRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public void saveConta(@Valid @RequestBody ContaRequestDTO data){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();

        if (contaRepository.existsByNumeroContaAndUserId(data.numeroConta(), userId)) {
            throw new RuntimeException("Número da conta já existe.");
        }

        if (contaRepository.existsByNomeAndUserId(data.nome(), userId)) {
            throw new RuntimeException("Nome da conta já existe.");
        }

        Conta contaData = new Conta(data);
        contaData.setUserId(userId);
        contaRepository.save(contaData);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<ContaResponseDTO> getAll(){
        return contaRepository.findAll().stream().map(ContaResponseDTO::new).toList();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/user")
    public List<ContaResponseDTO> getContasByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();
        return contaRepository.findByUserId(userId).stream().map(ContaResponseDTO::new).toList();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public void updateConta(@PathVariable UUID id, @Valid @RequestBody ContaRequestDTO data){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();

        Conta contaData = contaRepository.findById(id).orElseThrow(() -> new RuntimeException("Conta not found"));
        if (!contaData.getUserId().equals(userId)) {
            throw new RuntimeException("User not authorized to update this account");
        }

        if (contaRepository.existsByNumeroContaAndIdNotAndUserId(data.numeroConta(), id, userId)) {
            throw new RuntimeException("Número da conta já existe.");
        }

        if (contaRepository.existsByNomeAndIdNotAndUserId(data.nome(), id, userId)) {
            throw new RuntimeException("Nome da conta já existe.");
        }

        contaData.setTipo(data.tipo());
        contaData.setNumeroConta(data.numeroConta());
        contaData.setNome(data.nome());
        contaData.setIdGrupo(data.idGrupo());
        contaData.setIdSubgrupo(data.idSubgrupo());
        contaRepository.save(contaData);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public void deleteConta(@PathVariable UUID id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();

        Conta contaData = contaRepository.findById(id).orElseThrow(() -> new RuntimeException("Conta not found"));
        if (!contaData.getUserId().equals(userId)) {
            throw new RuntimeException("User not authorized to delete this account");
        }

        contaRepository.deleteById(id);
    }
}