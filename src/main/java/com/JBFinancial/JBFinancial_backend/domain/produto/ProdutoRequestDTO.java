package com.JBFinancial.JBFinancial_backend.domain.produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ProdutoRequestDTO(
        String userId,

        @NotBlank(message = "O código é obrigatório")
        @Size(max = 20, message = "O código deve ter no máximo 20 caracteres")
        String codigo,

        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 30, message = "O nome deve ter no máximo 30 caracteres")
        String nome_produto,

        @NotBlank(message = "A descrição é obrigatória")
        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
        String descricao,

        String categoria,

        UUID categoriaId
) {}