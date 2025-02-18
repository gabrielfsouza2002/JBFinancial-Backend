package com.JBFinancial.JBFinancial_backend.Services;

public record DreResponseDTO(
        double receitaLiquidaAnual,
        double lucroBrutoAnual,
        double ebitdaAnual,
        double lucroLiquidoExercicioAnual,
        double margemBruta,
        double margemEbitda,
        double margemLiquida,
        int year,
        int month
) {}