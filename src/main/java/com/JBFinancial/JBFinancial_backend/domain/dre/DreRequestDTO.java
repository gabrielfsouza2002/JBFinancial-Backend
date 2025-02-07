// src/main/java/com/JBFinancial/JBFinancial_backend/domain/dre/DreRequestDTO.java

package com.JBFinancial.JBFinancial_backend.domain.dre;

import jakarta.validation.constraints.NotNull;

public record DreRequestDTO(
        String userId,
        @NotNull Integer year,
        @NotNull Integer month,
        Double custoOperacional,
        Double propriedades,
        Double bustech,
        Double marketing,
        Double financasEJuridico,
        Double custosOperacionaisDiretos,
        Double impostosSimplesNacional,
        Double outrosImpostosETaxas,
        Double receitaLiquida,
        Double custoDosBensServicosVendidos,
        Double despesasReceitasOperacionais,
        Double despesasFinanceiras,
        Double resultadoBruto,
        Double ebitda,
        Double lucroLiquidoDoExercicio,
        Double margemBruta,
        Double margemEbitda,
        Double margemLiquida
) {}