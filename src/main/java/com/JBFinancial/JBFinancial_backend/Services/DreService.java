package com.JBFinancial.JBFinancial_backend.Services;

import com.JBFinancial.JBFinancial_backend.domain.base.Base;
import com.JBFinancial.JBFinancial_backend.domain.conta.Conta;
import com.JBFinancial.JBFinancial_backend.domain.grupo.Grupo;
import com.JBFinancial.JBFinancial_backend.domain.subgrupo.Subgrupo;
import com.JBFinancial.JBFinancial_backend.repositories.BaseRepository;
import com.JBFinancial.JBFinancial_backend.repositories.ContaRepository;
import com.JBFinancial.JBFinancial_backend.repositories.GrupoRepository;
import com.JBFinancial.JBFinancial_backend.repositories.SubgrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DreService {

    @Autowired
    private BaseRepository baseRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private SubgrupoRepository subgrupoRepository;

    // Define UUID constants
    private static final UUID RECEITA_BRUTA_UUID = UUID.fromString("3fec2d51-fb31-4bf5-9ab7-9c4cd8542c39");
    private static final UUID DEDUCOES_RECEITA_UUID = UUID.fromString("60dff836-0096-4135-82c6-3d6f3886ad4d");
    private static final UUID CUSTOS_BENS_SERVICOS_VENDIDOS_UUID = UUID.fromString("0d8364ef-b7c2-46f0-ab49-3b812dd47449");
    private static final UUID DESPESAS_OPERACIONAIS_UUID = UUID.fromString("3d6b0d31-7cb5-42aa-ade4-074e2c161f22");
    private static final UUID DESPESAS_RECEITAS_FINANCEIRAS_UUID = UUID.fromString("44fc2c54-2ed2-46d7-a918-ef2e14982f66");
    private static final UUID IMPOSTO_SOBRE_LUCRO_UUID = UUID.fromString("376e7904-87db-4c2d-9051-0357763961c3");

    private static final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);

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
        dreResponses.add(new DreResponseDTO(calculateDreValues(annualTotals)));

        for (int month = 1; month <= 12; month++) {
            Map<UUID, Double> monthlyTotal = new HashMap<>();
            for (UUID key : monthlyTotals.keySet()) {
                monthlyTotal.put(key, monthlyTotals.get(key).getOrDefault(month, 0.0));
            }
            dreResponses.add(new DreResponseDTO(calculateDreValues(monthlyTotal)));
        }

        return dreResponses;
    }

    private Map<String, Double> calculateDreValues(Map<UUID, Double> totals) {
        double receitaBruta = totals.getOrDefault(RECEITA_BRUTA_UUID, 0.0);
        double deducoesReceita = totals.getOrDefault(DEDUCOES_RECEITA_UUID, 0.0);
        double receitaLiquida = receitaBruta - deducoesReceita;

        double custosBensServicosVendidos = totals.getOrDefault(CUSTOS_BENS_SERVICOS_VENDIDOS_UUID, 0.0);
        double lucroBruto = receitaLiquida - custosBensServicosVendidos;

        double despesasOperacionais = totals.getOrDefault(DESPESAS_OPERACIONAIS_UUID, 0.0);
        double ebitda = lucroBruto - despesasOperacionais;
        double impostoSobreLucro = totals.getOrDefault(IMPOSTO_SOBRE_LUCRO_UUID, 0.0);
        double despesasReceitasFinanceiras = totals.getOrDefault(DESPESAS_RECEITAS_FINANCEIRAS_UUID, 0.0);
        double lucroLiquidoExercicio = ebitda - despesasReceitasFinanceiras - impostoSobreLucro;

        double margemBruta = receitaLiquida != 0 ? (lucroBruto / receitaLiquida) * 100 : 0;
        double margemEbitda = receitaLiquida != 0 ? (ebitda / receitaLiquida) * 100 : 0;
        double margemLiquida = receitaLiquida != 0 ? (lucroLiquidoExercicio / receitaLiquida) * 100 : 0;

        Map<String, Double> values = new LinkedHashMap<>();
        values.put("Receita Liquida", Double.valueOf(decimalFormat.format(receitaLiquida).replace(",", "")));
        values.put("Lucro Bruto", Double.valueOf(decimalFormat.format(lucroBruto).replace(",", "")));
        values.put("Ebitda", Double.valueOf(decimalFormat.format(ebitda).replace(",", "")));
        values.put("Lucro Liquido do Exercício", Double.valueOf(decimalFormat.format(lucroLiquidoExercicio).replace(",", "")));
        values.put("Margem Bruta", Double.valueOf(decimalFormat.format(margemBruta).replace(",", "")));
        values.put("Margem Ebitda", Double.valueOf(decimalFormat.format(margemEbitda).replace(",", "")));
        values.put("Margem Liquida", Double.valueOf(decimalFormat.format(margemLiquida).replace(",", "")));

        return values;
    }

    public List<DreGroupSubgroupResponseDTO> getTotalsByGroupAndSubgroup(String userId, int year) {
        List<Base> baseEntries = baseRepository.findByUserId(userId).stream()
                .filter(base -> base.getData().getYear() == year && base.getImpactaDre())
                .collect(Collectors.toList());

        List<Grupo> grupos = grupoRepository.findAll();
        List<Subgrupo> subgrupos = subgrupoRepository.findByIdUserOrIdUserIsNull(userId);

        Map<String, Double> annualTotals = new HashMap<>();
        Map<String, Map<Integer, Double>> monthlyTotals = new HashMap<>();

        for (Base base : baseEntries) {
            Conta conta = contaRepository.findById(base.getContaId()).orElseThrow();
            Grupo grupo = grupos.stream().filter(g -> g.getId().equals(conta.getIdGrupo())).findFirst().orElseThrow();
            Subgrupo subgrupo = subgrupos.stream().filter(s -> s.getId().equals(conta.getIdSubgrupo())).findFirst().orElseThrow();
            String groupKey = grupo.getNome();
            String subgroupKey = subgrupo.getNome();
            int month = base.getData().getMonthValue();

            annualTotals.putIfAbsent(groupKey, 0.0);
            annualTotals.put(groupKey, annualTotals.get(groupKey) + base.getValor());

            annualTotals.putIfAbsent(subgroupKey, 0.0);
            annualTotals.put(subgroupKey, annualTotals.get(subgroupKey) + base.getValor());

            monthlyTotals.putIfAbsent(groupKey, new HashMap<>());
            monthlyTotals.get(groupKey).putIfAbsent(month, 0.0);
            monthlyTotals.get(groupKey).put(month, monthlyTotals.get(groupKey).get(month) + base.getValor());

            monthlyTotals.putIfAbsent(subgroupKey, new HashMap<>());
            monthlyTotals.get(subgroupKey).putIfAbsent(month, 0.0);
            monthlyTotals.get(subgroupKey).put(month, monthlyTotals.get(subgroupKey).get(month) + base.getValor());
        }

        List<DreGroupSubgroupResponseDTO> responseList = new ArrayList<>();
        responseList.add(createResponseDTO(annualTotals, year, 0, grupos, subgrupos));

        for (int month = 1; month <= 12; month++) {
            Map<String, Double> monthlyTotal = new HashMap<>();
            for (String key : monthlyTotals.keySet()) {
                monthlyTotal.put(key, monthlyTotals.get(key).getOrDefault(month, 0.0));
            }
            responseList.add(createResponseDTO(monthlyTotal, year, month, grupos, subgrupos));
        }

        return responseList;
    }

    private DreGroupSubgroupResponseDTO createResponseDTO(Map<String, Double> totals, int year, int month, List<Grupo> grupos, List<Subgrupo> subgrupos) {
        Map<String, Double> groupTotals = new HashMap<>();
        for (Grupo grupo : grupos) {
            groupTotals.put(grupo.getNome(), totals.getOrDefault(grupo.getNome(), 0.0));
        }
        for (Subgrupo subgrupo : subgrupos) {
            groupTotals.put(subgrupo.getNome(), totals.getOrDefault(subgrupo.getNome(), 0.0));
        }

        return new DreGroupSubgroupResponseDTO(
                groupTotals
        );
    }
}