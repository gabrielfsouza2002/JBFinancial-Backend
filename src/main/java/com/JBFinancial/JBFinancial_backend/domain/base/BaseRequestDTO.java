package com.JBFinancial.JBFinancial_backend.domain.base;

import java.util.UUID;

public record BaseRequestDTO(String userId, UUID contaId, Double valor, Boolean impactaCaixa, Boolean impactaDre, String descricao) {
}