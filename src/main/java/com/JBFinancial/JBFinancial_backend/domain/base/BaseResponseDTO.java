package com.JBFinancial.JBFinancial_backend.domain.base;

import java.time.LocalDateTime;
import java.util.UUID;

public record BaseResponseDTO(Long id, String userId, LocalDateTime data, UUID contaId, Double valor, Boolean impactaCaixa, Boolean impactaDre, String descricao) {
    public BaseResponseDTO(Base base) {
        this(base.getId(), base.getUserId(), base.getData(), base.getContaId(), base.getValor(), base.getImpactaCaixa(), base.getImpactaDre(), base.getDescricao());
    }
}