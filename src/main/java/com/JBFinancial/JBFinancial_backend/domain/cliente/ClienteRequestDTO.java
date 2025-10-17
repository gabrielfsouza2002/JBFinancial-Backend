package com.JBFinancial.JBFinancial_backend.domain.cliente;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ClienteRequestDTO(
        String userId,

        @NotBlank(message = "O nome do cliente é obrigatório")
        String nome_cliente,

        @NotBlank(message = "A descrição é obrigatória")
        String descricao,

        @NotBlank(message = "O tipo de pessoa é obrigatório")
        String tipo_pessoa,

        UUID id_segmento
) {}
