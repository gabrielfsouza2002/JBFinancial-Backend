// src/main/java/com/JBFinancial/JBFinancial_backend/service/FinancialSummaryService.java

package com.JBFinancial.JBFinancial_backend.Services;

import com.JBFinancial.JBFinancial_backend.repositories.BaseRepository;
import com.JBFinancial.JBFinancial_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinancialSummaryService {

    @Autowired
    private BaseRepository baseRepository;

    @Autowired
    private UserRepository userRepository;

    public FinancialSummaryDTO calculateFinancialSummary(String userId) {
        Double sumEntradas = baseRepository.sumEntradas(userId);
        Double sumSaidas = baseRepository.sumSaidas(userId);
        Double saldoInicial = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")).getSaldoInicial().doubleValue();

        sumEntradas = sumEntradas != null ? sumEntradas : 0.0;
        sumSaidas = sumSaidas != null ? sumSaidas : 0.0;

        Double saldoTotal = saldoInicial + sumEntradas - sumSaidas;
        Double saldoOperacional = sumEntradas - sumSaidas;
        Double margem = saldoTotal != 0 ? ((saldoOperacional) / sumEntradas) * 100 : 0.0;


        return new FinancialSummaryDTO(sumEntradas, sumSaidas, saldoOperacional, saldoTotal, margem);
    }
}