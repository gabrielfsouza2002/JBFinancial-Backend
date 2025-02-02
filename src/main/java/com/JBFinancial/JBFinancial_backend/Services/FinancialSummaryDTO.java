// src/main/java/com/JBFinancial/JBFinancial_backend/service/FinancialSummaryDTO.java

package com.JBFinancial.JBFinancial_backend.Services;

public record FinancialSummaryDTO(Double sumEntradas, Double sumSaidas, Double saldoTotal, Double margem) {
}