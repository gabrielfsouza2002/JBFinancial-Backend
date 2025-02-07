package com.JBFinancial.JBFinancial_backend.domain.dre;

import java.util.UUID;

public record DreResponseDTO(
        UUID id,
        String userId,
        int year,
        int month,
        double custoOperacional,
        double propriedades,
        double bustech,
        double marketing,
        double financasEJuridico,
        double custosOperacionaisDiretos,
        double impostosSimplesNacional,
        double outrosImpostosETaxas,
        double receitaLiquida,
        double custoDosBensServicosVendidos,
        double despesasReceitasOperacionais,
        double despesasFinanceiras,
        double resultadoBruto,
        double ebitda,
        double lucroLiquidoDoExercicio,
        double margemBruta,
        double margemEbitda,
        double margemLiquida
) {
    public DreResponseDTO(Dre dre) {
        this(
                dre.getId(),
                dre.getUserId(),
                dre.getYear(),
                dre.getMonth(),
                dre.getCustoOperacional(),
                dre.getPropriedades(),
                dre.getBustech(),
                dre.getMarketing(),
                dre.getFinancasEJuridico(),
                dre.getCustosOperacionaisDiretos(),
                dre.getImpostosSimplesNacional(),
                dre.getOutrosImpostosETaxas(),
                dre.getReceitaLiquida(),
                dre.getCustoDosBensServicosVendidos(),
                dre.getDespesasReceitasOperacionais(),
                dre.getDespesasFinanceiras(),
                dre.getResultadoBruto(),
                dre.getEbitda(),
                dre.getLucroLiquidoDoExercicio(),
                dre.getMargemBruta(),
                dre.getMargemEbitda(),
                dre.getMargemLiquida()
        );
    }
}