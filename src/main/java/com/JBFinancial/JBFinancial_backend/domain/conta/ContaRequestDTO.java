// src/main/java/com/JBFinancial/JBFinancial_backend/domain/conta/ContaRequestDTO.java

package com.JBFinancial.JBFinancial_backend.domain.conta;

import com.JBFinancial.JBFinancial_backend.domain.grupo.Grupo;
import com.JBFinancial.JBFinancial_backend.domain.subgrupo.Subgrupo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ContaRequestDTO(
        String userId,
        @NotBlank(message = "O tipo é obrigatório")
        @Pattern(regexp = "Entrada|Saida", message = "Tipo de conta inválido. Deve ser 'Entrada' ou 'Saida'.")
        String tipo,

        @NotBlank(message = "O número da conta é obrigatório")
        @Pattern(regexp = "\\d+\\.\\d+\\.\\d{4}", message = "O número da conta deve estar no formato n.n.nnnn.")
        String numeroConta,

        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 30, message = "O nome deve ter no máximo 30 caracteres")
        String nome,

        UUID idGrupo,
        UUID idSubgrupo,
        Grupo grupo, // Add this field
        Subgrupo subgrupo // Add this field
) {
}