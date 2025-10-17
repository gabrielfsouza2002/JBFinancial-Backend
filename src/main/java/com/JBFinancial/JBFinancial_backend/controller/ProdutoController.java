package com.JBFinancial.JBFinancial_backend.controller;

import com.JBFinancial.JBFinancial_backend.domain.produto.*;
import com.JBFinancial.JBFinancial_backend.repositories.CategoriaProdutoRepository;
import com.JBFinancial.JBFinancial_backend.repositories.ProdutoRepository;
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
@RequestMapping("produto")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoriaProdutoRepository categoriaProdutoRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public void saveProduto(@Valid @RequestBody ProdutoRequestDTO data){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();

        if (produtoRepository.existsByCodigoAndUserId(data.codigo().toUpperCase(), userId)) {
            throw new RuntimeException("Código do produto já existe.");
        }

        if (produtoRepository.existsByNomeProdutoAndUserId(data.nome_produto().toUpperCase(), userId)) {
            throw new RuntimeException("Nome do produto já existe.");
        }

        Produto produto = new Produto(data);
        produto.setUserId(userId);

        // Set categoria relationship if categoriaId is provided
        if (data.categoriaId() != null) {
            produto.setCategoriaProduto(categoriaProdutoRepository.findById(data.categoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada")));
        }

        produtoRepository.save(produto);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<ProdutoResponseDTO> getAll(){
        return produtoRepository.findAll().stream().map(ProdutoResponseDTO::new).toList();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/user")
    public List<ProdutoResponseDTO> getProdutosByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();
        return produtoRepository.findByUserId(userId).stream().map(ProdutoResponseDTO::new).toList();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public void updateProduto(@PathVariable UUID id, @Valid @RequestBody ProdutoRequestDTO data){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();

        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        if (!produto.getUserId().equals(userId)) {
            throw new RuntimeException("Usuário não autorizado a atualizar este produto");
        }

        if (produtoRepository.existsByCodigoAndIdNotAndUserId(data.codigo().toUpperCase(), id, userId)) {
            throw new RuntimeException("Código do produto já existe.");
        }

        if (produtoRepository.existsByNomeProdutoAndIdNotAndUserId(data.nome_produto().toUpperCase(), id, userId)) {
            throw new RuntimeException("Nome do produto já existe.");
        }

        produto.setCodigo(data.codigo());
        produto.setNome_produto(data.nome_produto());
        produto.setDescricao(data.descricao());

        // Atualizar categoria antiga (String) apenas se fornecida
        if (data.categoria() != null) {
            produto.setCategoria(data.categoria());
        } else {
            produto.setCategoria(null);
        }

        // Set categoria relationship if categoriaId is provided
        if (data.categoriaId() != null) {
            produto.setCategoriaProduto(categoriaProdutoRepository.findById(data.categoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada")));
        } else {
            produto.setCategoriaProduto(null);
        }

        produtoRepository.save(produto);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public void deleteProduto(@PathVariable UUID id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();

        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        if (!produto.getUserId().equals(userId)) {
            throw new RuntimeException("Usuário não autorizado a deletar este produto");
        }

        produtoRepository.deleteById(id);
    }
}