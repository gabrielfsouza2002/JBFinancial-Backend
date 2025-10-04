package com.JBFinancial.JBFinancial_backend.domain.produto;

import java.util.UUID;

public record ProdutoResponseDTO(UUID id, String userId, String codigo, String nome, String descricao, String categoria) {
    public ProdutoResponseDTO(Produto produto) {
        this(produto.getId(), produto.getUserId(), produto.getCodigo(), produto.getNome(), produto.getDescricao(), produto.getCategoria());
    }
}