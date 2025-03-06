// src/main/java/com/JBFinancial/JBFinancial_backend/domain/grupo/GrupoRequestDTO.java

package com.JBFinancial.JBFinancial_backend.domain.grupo;

import jakarta.validation.constraints.NotBlank;

public record GrupoRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotBlank(message = "O tipo é obrigatório")
        String tipo // Novo campo tipo
) {
}