package com.JBFinancial.JBFinancial_backend.Services;

import com.JBFinancial.JBFinancial_backend.domain.base.Base;
import com.JBFinancial.JBFinancial_backend.domain.dre.Dre;

import java.text.DecimalFormat;
import com.JBFinancial.JBFinancial_backend.repositories.BaseRepository;
import com.JBFinancial.JBFinancial_backend.repositories.ContaRepository;
import com.JBFinancial.JBFinancial_backend.repositories.DreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DreService {
    @Autowired
    private DreRepository dreRepository;

    @Autowired
    private BaseRepository baseRepository;

    @Autowired
    private ContaRepository contaRepository;

    public List<Dre> getAllDre() {
        return dreRepository.findAll();
    }

    public List<Dre> getDreByUserId(String userId) {
        return dreRepository.findByUserId(userId);
    }

    public Dre getDreById(UUID id) {
        return dreRepository.findById(id).orElse(null);
    }

    public Dre saveDre(Dre dre) {
        return dreRepository.save(dre);
    }

    public void deleteDre(UUID id) {
        dreRepository.deleteById(id);
    }

    private UUID generateUniqueId(String userId, int year) {
        String uniqueString = userId + "-" + year;
        return UUID.nameUUIDFromBytes(uniqueString.getBytes(StandardCharsets.UTF_8));
    }

    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public void calculateAndSaveDre(String userId, UUID contaId, LocalDateTime date, Double valor) {
        int year = date.getYear();
        int month = date.getMonthValue();

        List<Base> baseEntries = baseRepository.findByUserId(userId).stream()
                .filter(base -> base.getData().getYear() == year && base.getData().getMonthValue() == month)
                .collect(Collectors.toList());

        if (baseEntries.isEmpty()) {
            throw new RuntimeException("No base entries found for the specified month and year.");
        }

        Map<String, Double> totalsByAccount = baseEntries.stream()
                .collect(Collectors.groupingBy(
                        base -> contaRepository.findById(base.getContaId()).orElseThrow().getNumeroConta(),
                        Collectors.summingDouble(Base::getValor)
                ));

        double custoOperacional = calculateTotal(totalsByAccount, "3.1");
        double propriedades = calculateTotal(totalsByAccount, "3.2");
        double bustech = calculateTotal(totalsByAccount, "3.3");
        double marketing = calculateTotal(totalsByAccount, "3.4");
        double financasEJuridico = calculateTotal(totalsByAccount, "3.5");
        double custosOperacionaisDiretos = calculateTotal(totalsByAccount, "3.6");
        double impostosSimplesNacional = calculateTotal(totalsByAccount, "4.1");
        double outrosImpostosETaxas = calculateTotal(totalsByAccount, "4.2");
        double receitaLiquida = calculateTotal(totalsByAccount, "1.0");
        double custoDosBensServicosVendidos = calculateTotal(totalsByAccount, "2.0");

        double despesasReceitasOperacionais = custoOperacional + propriedades + bustech + marketing + financasEJuridico + custosOperacionaisDiretos;
        double despesasFinanceiras = impostosSimplesNacional + outrosImpostosETaxas;
        double resultadoBruto = receitaLiquida - custoDosBensServicosVendidos;
        double ebitda = resultadoBruto - despesasReceitasOperacionais;
        double lucroLiquidoDoExercicio = ebitda - despesasFinanceiras;

        double margemBruta = receitaLiquida != 0 ? (resultadoBruto / receitaLiquida) * 100 : 0;
        double margemEbitda = receitaLiquida != 0 ? (ebitda / receitaLiquida) * 100 : 0;
        double margemLiquida = receitaLiquida != 0 ? (lucroLiquidoDoExercicio / receitaLiquida) * 100 : 0;

        Dre dre = dreRepository.findByUserIdAndYearAndMonth(userId, year, month)
                .orElse(new Dre());

        dre.setUserId(userId);
        dre.setYear(year);
        dre.setMonth(month);
        dre.setCustoOperacional(custoOperacional);
        dre.setPropriedades(propriedades);
        dre.setBustech(bustech);
        dre.setMarketing(marketing);
        dre.setFinancasEJuridico(financasEJuridico);
        dre.setCustosOperacionaisDiretos(custosOperacionaisDiretos);
        dre.setImpostosSimplesNacional(impostosSimplesNacional);
        dre.setOutrosImpostosETaxas(outrosImpostosETaxas);
        dre.setReceitaLiquida(receitaLiquida);
        dre.setCustoDosBensServicosVendidos(custoDosBensServicosVendidos);
        dre.setDespesasReceitasOperacionais(despesasReceitasOperacionais);
        dre.setDespesasFinanceiras(despesasFinanceiras);
        dre.setResultadoBruto(resultadoBruto);
        dre.setEbitda(ebitda);
        dre.setLucroLiquidoDoExercicio(lucroLiquidoDoExercicio);
        dre.setMargemBruta(margemBruta);
        dre.setMargemEbitda(margemEbitda);
        dre.setMargemLiquida(margemLiquida);

        dreRepository.save(dre);
    }

    public List<Dre> calculateAnnualTotals(String userId, int year) {
        UUID uniqueId = generateUniqueId(userId, year);
        List<Dre> dreEntries = dreRepository.findByUserId(userId).stream()
                .filter(dre -> dre.getYear() == year)
                .collect(Collectors.toList());

        double custoOperacional = 0;
        double propriedades = 0;
        double bustech = 0;
        double marketing = 0;
        double financasEJuridico = 0;
        double custosOperacionaisDiretos = 0;
        double impostosSimplesNacional = 0;
        double outrosImpostosETaxas = 0;
        double receitaLiquida = 0;
        double custoDosBensServicosVendidos = 0;
        double despesasReceitasOperacionais = 0;
        double despesasFinanceiras = 0;
        double resultadoBruto = 0;
        double ebitda = 0;
        double lucroLiquidoDoExercicio = 0;
        double margemBruta = 0;
        double margemEbitda = 0;
        double margemLiquida = 0;

        for (Dre dre : dreEntries) {
            custoOperacional += dre.getCustoOperacional();
            propriedades += dre.getPropriedades();
            bustech += dre.getBustech();
            marketing += dre.getMarketing();
            financasEJuridico += dre.getFinancasEJuridico();
            custosOperacionaisDiretos += dre.getCustosOperacionaisDiretos();
            impostosSimplesNacional += dre.getImpostosSimplesNacional();
            outrosImpostosETaxas += dre.getOutrosImpostosETaxas();
            receitaLiquida += dre.getReceitaLiquida();
            custoDosBensServicosVendidos += dre.getCustoDosBensServicosVendidos();
        }

        despesasReceitasOperacionais = custoOperacional + propriedades + bustech + marketing + financasEJuridico + custosOperacionaisDiretos;
        despesasFinanceiras = impostosSimplesNacional + outrosImpostosETaxas;
        resultadoBruto = receitaLiquida - custoDosBensServicosVendidos;
        ebitda = resultadoBruto - despesasReceitasOperacionais;
        lucroLiquidoDoExercicio = ebitda - despesasFinanceiras;

        if (receitaLiquida != 0) {
            margemBruta = (resultadoBruto / receitaLiquida) * 100;
            margemEbitda = (ebitda / receitaLiquida) * 100;
            margemLiquida = (lucroLiquidoDoExercicio / receitaLiquida) * 100;
        }

        Dre annualTotals = new Dre();
        annualTotals.setId(uniqueId);
        annualTotals.setUserId(userId);
        annualTotals.setYear(year);
        annualTotals.setMonth(0); // Month is not applicable for annual totals
        annualTotals.setCustoOperacional(custoOperacional);
        annualTotals.setPropriedades(propriedades);
        annualTotals.setBustech(bustech);
        annualTotals.setMarketing(marketing);
        annualTotals.setFinancasEJuridico(financasEJuridico);
        annualTotals.setCustosOperacionaisDiretos(custosOperacionaisDiretos);
        annualTotals.setImpostosSimplesNacional(impostosSimplesNacional);
        annualTotals.setOutrosImpostosETaxas(outrosImpostosETaxas);
        annualTotals.setReceitaLiquida(receitaLiquida);
        annualTotals.setCustoDosBensServicosVendidos(custoDosBensServicosVendidos);
        annualTotals.setDespesasReceitasOperacionais(despesasReceitasOperacionais);
        annualTotals.setDespesasFinanceiras(despesasFinanceiras);
        annualTotals.setResultadoBruto(resultadoBruto);
        annualTotals.setEbitda(ebitda);
        annualTotals.setLucroLiquidoDoExercicio(lucroLiquidoDoExercicio);
        annualTotals.setMargemBruta(margemBruta);
        annualTotals.setMargemEbitda(margemEbitda);
        annualTotals.setMargemLiquida(margemLiquida);

        return List.of(annualTotals);
    }

    private double calculateTotal(Map<String, Double> totalsByAccount, String prefix) {
        return totalsByAccount.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(prefix))
                .mapToDouble(Map.Entry::getValue)
                .sum();
    }

    public List<Dre> getDreByUserIdAndYearAndMonth(String userId, int year, int month) {
        return dreRepository.findByUserIdAndYearAndMonth(userId, year, month)
                .map(List::of)
                .orElse(List.of());
    }

    public List<List<Object>> getDreMatrix(String userId, int year) {
        List<List<Object>> matrix = new ArrayList<>();
        matrix.add(List.of("", "Total no Ano", "Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"));

        String[] categories = {
                "Custo Operacional", "Propriedades", "Bustech", "Marketing", "Finanças e Jurídico",
                "Custos Operacionais Diretos", "Impostos Simples Nacional", "Outros Impostos e Taxas",
                "Receita Líquida", "Custo dos Bens/Serviços Vendidos", "Despesas/Receitas Operacionais",
                "Despesas Financeiras", "Resultado Bruto", "EBITDA", "Lucro Líquido do Exercício",
                "Margem Bruta (%)", "Margem EBITDA (%)", "Margem Líquida (%)"
        };

        for (String category : categories) {
            List<Object> row = new ArrayList<>();
            row.add(category);
            for (int i = 0; i < 13; i++) {
                row.add("-");
            }
            matrix.add(row);
        }

        // Fill the matrix with annual data
        List<Dre> annualData = calculateAnnualTotals(userId, year);
        if (!annualData.isEmpty()) {
            Dre annualDre = annualData.get(0);
            fillRowWithDreData(matrix, annualDre, 1);
        }

        // Fill the matrix with monthly data
        for (int month = 1; month <= 12; month++) {
            List<Dre> monthlyData = getDreByUserIdAndYearAndMonth(userId, year, month);
            if (!monthlyData.isEmpty()) {
                Dre monthlyDre = monthlyData.get(0);
                fillRowWithDreData(matrix, monthlyDre, month + 1);
            }
        }

        return matrix;
    }

    private void fillRowWithDreData(List<List<Object>> matrix, Dre dre, int columnIndex) {
        matrix.get(1).set(columnIndex, formatValue(dre.getCustoOperacional()));
        matrix.get(2).set(columnIndex, formatValue(dre.getPropriedades()));
        matrix.get(3).set(columnIndex, formatValue(dre.getBustech()));
        matrix.get(4).set(columnIndex, formatValue(dre.getMarketing()));
        matrix.get(5).set(columnIndex, formatValue(dre.getFinancasEJuridico()));
        matrix.get(6).set(columnIndex, formatValue(dre.getCustosOperacionaisDiretos()));
        matrix.get(7).set(columnIndex, formatValue(dre.getImpostosSimplesNacional()));
        matrix.get(8).set(columnIndex, formatValue(dre.getOutrosImpostosETaxas()));
        matrix.get(9).set(columnIndex, formatValue(dre.getReceitaLiquida()));
        matrix.get(10).set(columnIndex, formatValue(dre.getCustoDosBensServicosVendidos()));
        matrix.get(11).set(columnIndex, formatValue(dre.getDespesasReceitasOperacionais()));
        matrix.get(12).set(columnIndex, formatValue(dre.getDespesasFinanceiras()));
        matrix.get(13).set(columnIndex, formatValue(dre.getResultadoBruto()));
        matrix.get(14).set(columnIndex, formatValue(dre.getEbitda()));
        matrix.get(15).set(columnIndex, formatValue(dre.getLucroLiquidoDoExercicio()));
        matrix.get(16).set(columnIndex, formatValue(dre.getMargemBruta(), true));
        matrix.get(17).set(columnIndex, formatValue(dre.getMargemEbitda(), true));
        matrix.get(18).set(columnIndex, formatValue(dre.getMargemLiquida(), true));
    }

    private String formatValue(double value) {
        return value == 0 ? "-" : String.valueOf(value);
    }

    private String formatValue(double value, boolean isPercentage) {
        if (value == 0) {
            return "-";
        }
        String formattedValue = decimalFormat.format(value);
        return isPercentage ? formattedValue + "%" : formattedValue;
    }

}