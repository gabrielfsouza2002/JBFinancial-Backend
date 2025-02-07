package com.JBFinancial.JBFinancial_backend.Services;

import com.JBFinancial.JBFinancial_backend.domain.base.Base;
import com.JBFinancial.JBFinancial_backend.domain.dre.Dre;
import com.JBFinancial.JBFinancial_backend.domain.dre.DreResponseDTO;
import com.JBFinancial.JBFinancial_backend.repositories.BaseRepository;
import com.JBFinancial.JBFinancial_backend.repositories.ContaRepository;
import com.JBFinancial.JBFinancial_backend.repositories.DreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
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

    public void calculateAndSaveDre(String userId, UUID contaId, LocalDateTime date, Double valor) {
        int year = date.getYear();
        int month = date.getMonthValue();

        System.out.println("Data DREService: " + year + "/" + month);

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

    public List<DreResponseDTO> calculateAnnualTotals(String userId, int year) {

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

        DreResponseDTO annualTotals = new DreResponseDTO(
                uniqueId, // Use the generated unique ID
                userId,
                year,
                0, // Month is not applicable for annual totals
                custoOperacional,
                propriedades,
                bustech,
                marketing,
                financasEJuridico,
                custosOperacionaisDiretos,
                impostosSimplesNacional,
                outrosImpostosETaxas,
                receitaLiquida,
                custoDosBensServicosVendidos,
                despesasReceitasOperacionais,
                despesasFinanceiras,
                resultadoBruto,
                ebitda,
                lucroLiquidoDoExercicio,
                margemBruta,
                margemEbitda,
                margemLiquida
        );

        return List.of(annualTotals);
    }

    private double calculateTotal(Map<String, Double> totalsByAccount, String prefix) {
        return totalsByAccount.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(prefix))
                .mapToDouble(Map.Entry::getValue)
                .sum();
    }
}