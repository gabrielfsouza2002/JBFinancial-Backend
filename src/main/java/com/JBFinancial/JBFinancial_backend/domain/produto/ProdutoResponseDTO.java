package com.JBFinancial.JBFinancial_backend.domain.produto;

import java.util.UUID;

public record ProdutoResponseDTO(UUID id, String userId, String codigo, String nome_produto, String descricao, String categoria) {
    public ProdutoResponseDTO(Produto produto) {
        this(produto.getId(), produto.getUserId(), produto.getCodigo(), produto.getNome_produto(), produto.getDescricao(), produto.getCategoria());
    }
}