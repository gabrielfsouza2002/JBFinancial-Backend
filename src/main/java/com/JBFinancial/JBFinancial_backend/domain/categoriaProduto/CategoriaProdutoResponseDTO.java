package com.JBFinancial.JBFinancial_backend.domain.categoriaProduto;

import java.util.UUID;

public record CategoriaProdutoResponseDTO(UUID id, String userId, String nome, String descricao) {
    public CategoriaProdutoResponseDTO(CategoriaProduto categoria) {
        this(categoria.getId(), categoria.getUserId(), categoria.getNome(), categoria.getDescricao());
    }
}

