package com.JBFinancial.JBFinancial_backend.domain.produto;

import java.util.UUID;

public record ProdutoResponseDTO(
        UUID id,
        String userId,
        String codigo,
        String nome_produto,
        String descricao,
        String categoria,
        UUID categoriaId,
        String categoriaNome
) {
    public ProdutoResponseDTO(Produto produto) {
        this(
                produto.getId(),
                produto.getUserId(),
                produto.getCodigo(),
                produto.getNome_produto(),
                produto.getDescricao(),
                produto.getCategoria(),
                produto.getCategoriaProduto() != null ? produto.getCategoriaProduto().getId() : null,
                produto.getCategoriaProduto() != null ? produto.getCategoriaProduto().getNome() : null
        );
    }
}