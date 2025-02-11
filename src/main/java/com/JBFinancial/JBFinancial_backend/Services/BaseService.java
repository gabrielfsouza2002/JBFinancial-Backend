// src/main/java/com/JBFinancial/JBFinancial_backend/Services/BaseService.java

package com.JBFinancial.JBFinancial_backend.Services;

import com.JBFinancial.JBFinancial_backend.domain.base.Base;
import com.JBFinancial.JBFinancial_backend.repositories.BaseRepository;
import com.JBFinancial.JBFinancial_backend.repositories.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BaseService {
    @Autowired
    private BaseRepository baseRepository;

    @Autowired
    private ContaRepository contaRepository;

    private static final DecimalFormat decimalFormat = new DecimalFormat("#.00");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public List<List<Object>> getBaseMatrix(String userId) {
        List<Base> baseEntries = baseRepository.findByUserId(userId);
        List<List<Object>> matrix = new ArrayList<>();
        matrix.add(List.of("Ano", "Mês", "Data", "Horário", "Nome da Conta", "Valor", "Crédito/Debito", "Impacta Caixa", "Impacta DRE", "Descrição"));

        for (Base base : baseEntries) {
            String contaNome = contaRepository.findById(base.getContaId()).orElseThrow().getNome();
            String valor = decimalFormat.format(base.getValor());
            String creditoDebito = base.getDebtCred() ? "credito" : "debito";
            matrix.add(List.of(
                    base.getData().getYear(),
                    base.getData().getMonthValue(),
                    base.getData().format(dateFormatter),
                    base.getData().format(timeFormatter),
                    contaNome,
                    valor,
                    creditoDebito,
                    base.getImpactaCaixa() ? "Sim" : "Não",
                    base.getImpactaDre() ? "Sim" : "Não",
                    base.getDescricao()
            ));
        }

        return matrix;
    }

    public List<List<Object>> getMonthlySummaryByUser(int year, boolean impactaCaixa, String userId) {
        List<Base> baseEntries;
        if (impactaCaixa) {
            baseEntries = baseRepository.findByYearAndImpactaCaixaAndUserId(year, true, userId);
        } else {
            baseEntries = baseRepository.findByYearAndUserId(year, userId);
        }

        List<List<Object>> matrix = new ArrayList<>();
        List<Object> months = List.of("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro");
        List<Double> entradas = new ArrayList<>(Collections.nCopies(12, 0.0));
        List<Double> saidas = new ArrayList<>(Collections.nCopies(12, 0.0));

        for (Base base : baseEntries) {
            int month = base.getData().getMonthValue() - 1;
            String contaTipo = contaRepository.findById(base.getContaId()).orElseThrow().getTipo();

            if (contaTipo.equals("Entrada")) {
                entradas.set(month, entradas.get(month) + base.getValor());
            } else if (contaTipo.equals("Saída")) {
                saidas.set(month, saidas.get(month) + base.getValor());
            }
        }

        matrix.add(months);
        matrix.add(entradas.stream().map(decimalFormat::format).collect(Collectors.toList()));
        matrix.add(saidas.stream().map(decimalFormat::format).collect(Collectors.toList()));

        return matrix;
    }
}