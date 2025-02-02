// src/main/java/com/JBFinancial/JBFinancial_backend/domain/conta/ContaRequestDTO.java

package com.JBFinancial.JBFinancial_backend.domain.conta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ContaRequestDTO(
        String userId,
        @NotBlank(message = "O tipo é obrigatório")
        @Pattern(regexp = "Entrada|Saída", message = "Tipo de conta inválido. Deve ser 'Entrada' ou 'Saída'.")
        String tipo,

        @NotBlank(message = "O número da conta é obrigatório")
        @Pattern(regexp = "\\d+", message = "O número da conta deve conter apenas números.")
        String numeroConta,

        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 20, message = "O nome deve ter no máximo 20 caracteres")
        String nome
) {
}