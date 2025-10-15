package com.JBFinancial.JBFinancial_backend.controller;

import com.JBFinancial.JBFinancial_backend.domain.fornecedor.*;
import com.JBFinancial.JBFinancial_backend.repositories.FornecedorRepository;
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
@RequestMapping("fornecedor")
public class FornecedorController {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private UserRepository userRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public void saveFornecedor(@Valid @RequestBody FornecedorRequestDTO data){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();

        if (fornecedorRepository.existsByNomeFornecedorAndUserId(data.nome_fornecedor().toUpperCase(), userId)) {
            throw new RuntimeException("Nome do fornecedor já existe.");
        }

        Fornecedor fornecedor = new Fornecedor(data);
        fornecedor.setUserId(userId);
        fornecedorRepository.save(fornecedor);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<FornecedorResponseDTO> getAll(){
        return fornecedorRepository.findAll().stream().map(FornecedorResponseDTO::new).toList();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/user")
    public List<FornecedorResponseDTO> getFornecedoresByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();
        return fornecedorRepository.findByUserId(userId).stream().map(FornecedorResponseDTO::new).toList();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public void updateFornecedor(@PathVariable UUID id, @Valid @RequestBody FornecedorRequestDTO data){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();

        Fornecedor fornecedor = fornecedorRepository.findById(id).orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));
        if (!fornecedor.getUserId().equals(userId)) {
            throw new RuntimeException("Usuário não autorizado a atualizar este fornecedor");
        }

        if (fornecedorRepository.existsByNomeFornecedorAndIdNotAndUserId(data.nome_fornecedor().toUpperCase(), id, userId)) {
            throw new RuntimeException("Nome do fornecedor já existe.");
        }

        fornecedor.setNome_fornecedor(data.nome_fornecedor());
        fornecedor.setDescricao(data.descricao());
        fornecedorRepository.save(fornecedor);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public void deleteFornecedor(@PathVariable UUID id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();

        Fornecedor fornecedor = fornecedorRepository.findById(id).orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));
        if (!fornecedor.getUserId().equals(userId)) {
            throw new RuntimeException("Usuário não autorizado a deletar este fornecedor");
        }

        fornecedorRepository.deleteById(id);
    }
}

