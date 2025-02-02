// src/main/java/com/JBFinancial/JBFinancial_backend/domain/base/BaseRequestDTO.java

package com.JBFinancial.JBFinancial_backend.domain.base;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public record BaseRequestDTO(
        UUID contaId,
        @NotNull(message = "O valor é obrigatório")
        Double valor,
        @NotNull(message = "Impacta Caixa é obrigatório")
        Boolean impactaCaixa,
        @NotNull(message = "Impacta DRE é obrigatório")
        Boolean impactaDre,
        @NotNull(message = "A descrição é obrigatória")
        String descricao,
        @NotNull(message = "Debt/Cred é obrigatório")
        Boolean debtCred
) {
    public BaseRequestDTO {
        if (debtCred && valor < 0) {
            throw new IllegalArgumentException("Valor deve ser positivo para crédito.");
        } else if (!debtCred && valor > 0) {
            throw new IllegalArgumentException("Valor deve ser negativo para débito.");
        }
    }
}