package com.JBFinancial.JBFinancial_backend.domain.segmento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SegmentoRequestDTO(
        String userId,

        @NotBlank(message = "O nome do segmento é obrigatório")
        @Size(max = 20, message = "O nome do segmento deve ter no máximo 20 caracteres")
        String nome
) {}

