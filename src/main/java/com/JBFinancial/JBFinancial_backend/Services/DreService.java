package com.JBFinancial.JBFinancial_backend.Services;

import com.JBFinancial.JBFinancial_backend.domain.base.Base;
import com.JBFinancial.JBFinancial_backend.domain.conta.Conta;
import com.JBFinancial.JBFinancial_backend.repositories.BaseRepository;
import com.JBFinancial.JBFinancial_backend.repositories.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DreService {

    @Autowired
    private BaseRepository baseRepository;

    @Autowired
    private ContaRepository contaRepository;

    // Define UUID constants
    private static final UUID RECEITA_BRUTA_UUID = UUID.fromString("3fec2d51-fb31-4bf5-9ab7-9c4cd8542c39");
    private static final UUID DEDUCOES_RECEITA_UUID = UUID.fromString("60dff836-0096-4135-82c6-3d6f3886ad4d");
    private static final UUID CUSTOS_BENS_SERVICOS_VENDIDOS_UUID = UUID.fromString("0d8364ef-b7c2-46f0-ab49-3b812dd47449");
    private static final UUID DESPESAS_OPERACIONAIS_UUID = UUID.fromString("3d6b0d31-7cb5-42aa-ade4-074e2c161f22");
    private static final UUID DESPESAS_RECEITAS_FINANCEIRAS_UUID = UUID.fromString("44fc2c54-2ed2-46d7-a918-ef2e14982f66");
    private static final UUID IMPOSTO_SOBRE_LUCRO_UUID = UUID.fromString("376e7904-87db-4c2d-9051-0357763961c3");

    public List<DreResponseDTO> calculateDre(String userId, int year) {
        List<Base> baseEntries = baseRepository.findByUserId(userId).stream()
                .filter(base -> base.getData().getYear() == year && base.getImpactaDre())
                .collect(Collectors.toList());

        Map<UUID, Double> annualTotals = new HashMap<>();
        Map<UUID, Map<Integer, Double>> monthlyTotals = new HashMap<>();

        for (Base base : baseEntries) {
            Conta conta = contaRepository.findById(base.getContaId()).orElseThrow();
            UUID groupId = conta.getIdGrupo();
            UUID subGroupId = conta.getIdSubgrupo();
            int month = base.getData().getMonthValue();

            annualTotals.putIfAbsent(groupId, 0.0);
            annualTotals.put(groupId, annualTotals.get(groupId) + base.getValor());

            monthlyTotals.putIfAbsent(groupId, new HashMap<>());
            monthlyTotals.get(groupId).putIfAbsent(month, 0.0);
            monthlyTotals.get(groupId).put(month, monthlyTotals.get(groupId).get(month) + base.getValor());

            annualTotals.putIfAbsent(subGroupId, 0.0);
            annualTotals.put(subGroupId, annualTotals.get(subGroupId) + base.getValor());

            monthlyTotals.putIfAbsent(subGroupId, new HashMap<>());
            monthlyTotals.get(subGroupId).putIfAbsent(month, 0.0);
            monthlyTotals.get(subGroupId).put(month, monthlyTotals.get(subGroupId).get(month) + base.getValor());
        }

        List<DreResponseDTO> dreResponses = new ArrayList<>();
        dreResponses.add(calculateDreValues(annualTotals, monthlyTotals, year, 0));

        for (int month = 1; month <= 12; month++) {
            Map<UUID, Double> monthlyTotal = new HashMap<>();
            for (UUID key : monthlyTotals.keySet()) {
                monthlyTotal.put(key, monthlyTotals.get(key).getOrDefault(month, 0.0));
            }
            dreResponses.add(calculateDreValues(monthlyTotal, monthlyTotals, year, month));
        }

        return dreResponses;
    }

    private DreResponseDTO calculateDreValues(Map<UUID, Double> totals, Map<UUID, Map<Integer, Double>> monthlyTotals, int year, int month) {
        double receitaBruta = totals.getOrDefault(RECEITA_BRUTA_UUID, 0.0);
        double deducoesReceita = totals.getOrDefault(DEDUCOES_RECEITA_UUID, 0.0);
        double receitaLiquida = receitaBruta - deducoesReceita;

        double custosBensServicosVendidos = totals.getOrDefault(CUSTOS_BENS_SERVICOS_VENDIDOS_UUID, 0.0);
        double lucroBruto = receitaLiquida - custosBensServicosVendidos;

        double despesasOperacionais = totals.getOrDefault(DESPESAS_OPERACIONAIS_UUID, 0.0);
        double ebitda = lucroBruto - despesasOperacionais;

        double despesasReceitasFinanceiras = totals.getOrDefault(DESPESAS_RECEITAS_FINANCEIRAS_UUID, 0.0);
        double lucroLiquidoExercicio = ebitda - despesasReceitasFinanceiras;

        double margemBruta = receitaLiquida != 0 ? (lucroBruto / receitaLiquida) * 100 : 0;
        double margemEbitda = receitaLiquida != 0 ? (ebitda / receitaLiquida) * 100 : 0;
        double margemLiquida = receitaLiquida != 0 ? (lucroLiquidoExercicio / receitaLiquida) * 100 : 0;

        return new DreResponseDTO(
                receitaLiquida, lucroBruto, ebitda, lucroLiquidoExercicio,
                margemBruta, margemEbitda, margemLiquida, year, month
        );
    }
}