package com.JBFinancial.JBFinancial_backend.domain.categoriaProduto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaProdutoRequestDTO(
        String userId,

        @NotBlank(message = "O nome da categoria é obrigatório")
        @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres")
        String nome,

        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
        String descricao
) {}

