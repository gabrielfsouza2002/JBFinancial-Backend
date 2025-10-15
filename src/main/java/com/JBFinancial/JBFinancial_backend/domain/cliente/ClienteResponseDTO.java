package com.JBFinancial.JBFinancial_backend.domain.cliente;

import java.util.UUID;

public record ClienteResponseDTO(UUID id, String userId, String nome_cliente, String descricao, String tipo_pessoa) {
    public ClienteResponseDTO(Cliente cliente) {
        this(cliente.getId(), cliente.getUserId(), cliente.getNome_cliente(), cliente.getDescricao(), cliente.getTipo_pessoa());
    }
}

