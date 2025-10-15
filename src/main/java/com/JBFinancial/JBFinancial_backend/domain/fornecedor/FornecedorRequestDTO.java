package com.JBFinancial.JBFinancial_backend.domain.fornecedor;

import jakarta.validation.constraints.NotBlank;

public record FornecedorRequestDTO(
        String userId,

        @NotBlank(message = "O nome do fornecedor é obrigatório")
        String nome_fornecedor,

        @NotBlank(message = "A descrição é obrigatória")
        String descricao
) {}

