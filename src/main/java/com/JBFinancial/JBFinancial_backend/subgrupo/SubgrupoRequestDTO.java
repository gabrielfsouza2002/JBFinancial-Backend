// src/main/java/com/JBFinancial/JBFinancial_backend/domain/subgrupo/SubgrupoRequestDTO.java

package com.JBFinancial.JBFinancial_backend.subgrupo;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record SubgrupoRequestDTO(
        String idUser,
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotBlank(message = "O id do grupo é obrigatório")
        UUID idGrupo
) {
}