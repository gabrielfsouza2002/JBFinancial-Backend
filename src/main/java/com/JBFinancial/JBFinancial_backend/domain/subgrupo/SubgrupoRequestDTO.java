package com.JBFinancial.JBFinancial_backend.domain.subgrupo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record SubgrupoRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotNull(message = "O id do grupo é obrigatório")
        UUID idGrupo
) {
}