package com.JBFinancial.JBFinancial_backend.controller;

import com.JBFinancial.JBFinancial_backend.domain.categoriaProduto.*;
import com.JBFinancial.JBFinancial_backend.repositories.CategoriaProdutoRepository;
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
@RequestMapping("categoria-produto")
public class CategoriaProdutoController {

    @Autowired
    private CategoriaProdutoRepository categoriaProdutoRepository;

    @Autowired
    private UserRepository userRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public void saveCategoriaProduto(@Valid @RequestBody CategoriaProdutoRequestDTO data){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();

        if (categoriaProdutoRepository.existsByNomeAndUserId(data.nome().toUpperCase(), userId)) {
            throw new RuntimeException("Categoria com este nome já existe.");
        }

        CategoriaProduto categoria = new CategoriaProduto(data);
        categoria.setUserId(userId);
        categoriaProdutoRepository.save(categoria);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<CategoriaProdutoResponseDTO> getAll(){
        return categoriaProdutoRepository.findAll().stream().map(CategoriaProdutoResponseDTO::new).toList();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/user")
    public List<CategoriaProdutoResponseDTO> getCategoriasByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();
        return categoriaProdutoRepository.findByUserId(userId).stream().map(CategoriaProdutoResponseDTO::new).toList();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public void updateCategoriaProduto(@PathVariable UUID id, @Valid @RequestBody CategoriaProdutoRequestDTO data){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();

        CategoriaProduto categoria = categoriaProdutoRepository.findById(id).orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        if (!categoria.getUserId().equals(userId)) {
            throw new RuntimeException("Usuário não autorizado a atualizar esta categoria");
        }

        if (categoriaProdutoRepository.existsByNomeAndUserIdAndIdNot(data.nome().toUpperCase(), userId, id)) {
            throw new RuntimeException("Categoria com este nome já existe.");
        }

        categoria.setNome(data.nome());
        categoria.setDescricao(data.descricao());
        categoriaProdutoRepository.save(categoria);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public void deleteCategoriaProduto(@PathVariable UUID id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();

        CategoriaProduto categoria = categoriaProdutoRepository.findById(id).orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        if (!categoria.getUserId().equals(userId)) {
            throw new RuntimeException("Usuário não autorizado a deletar esta categoria");
        }

        categoriaProdutoRepository.deleteById(id);
    }
}

