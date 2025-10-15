// src/main/java/com/JBFinancial/JBFinancial_backend/domain/base/BaseResponseDTO.java

package com.JBFinancial.JBFinancial_backend.domain.base;

import java.time.LocalDateTime;
import java.util.UUID;

public record BaseResponseDTO(
        UUID id,
        String userId,
        LocalDateTime data,
        UUID contaId,
        Double valor,
        Boolean impactaCaixa,
        Boolean impactaDre,
        String descricao,
        Boolean debtCred,
        UUID idProduto,
        UUID idCliente,
        UUID idFornecedor
) {
    public BaseResponseDTO(Base base) {
        this(
            base.getId(),
            base.getUserId(),
            base.getData(),
            base.getContaId(),
            base.getValor(),
            base.getImpactaCaixa(),
            base.getImpactaDre(),
            base.getDescricao(),
            base.getDebtCred(),
            base.getIdProduto(),
            base.getIdCliente(),
            base.getIdFornecedor()
        );
    }
}