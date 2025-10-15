package com.JBFinancial.JBFinancial_backend.domain.fornecedor;

import java.util.UUID;

public record FornecedorResponseDTO(UUID id, String userId, String nome_fornecedor, String descricao) {
    public FornecedorResponseDTO(Fornecedor fornecedor) {
        this(fornecedor.getId(), fornecedor.getUserId(), fornecedor.getNome_fornecedor(), fornecedor.getDescricao());
    }
}

