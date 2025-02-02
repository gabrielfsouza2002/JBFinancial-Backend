// src/main/java/com/JBFinancial/JBFinancial_backend/domain/conta/ContaResponseDTO.java

package com.JBFinancial.JBFinancial_backend.domain.conta;

import java.util.UUID;

public record ContaResponseDTO(UUID id, String userId, String tipo, String numeroConta, String nome) {
    public ContaResponseDTO(Conta conta) {
        this(conta.getId(), conta.getUserId(), conta.getTipo(), conta.getNumeroConta(), conta.getNome());
    }
}