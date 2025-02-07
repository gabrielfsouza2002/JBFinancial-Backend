package com.JBFinancial.JBFinancial_backend.domain.base;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
        Boolean debtCred,
        LocalDateTime data // Campo opcional para definir a data manualmente
) {
    public BaseRequestDTO {
        if (debtCred && valor < 0) {
            throw new IllegalArgumentException("Valor deve ser positivo para crédito.");
        } else if (!debtCred && valor > 0) {
            throw new IllegalArgumentException("Valor deve ser negativo para débito.");
        }
    }
}