// src/main/java/com/JBFinancial/JBFinancial_backend/Services/BaseService.java

package com.JBFinancial.JBFinancial_backend.Services;

import com.JBFinancial.JBFinancial_backend.domain.base.Base;
import com.JBFinancial.JBFinancial_backend.domain.base.BaseMatrixResponseDTO;
import com.JBFinancial.JBFinancial_backend.repositories.BaseRepository;
import com.JBFinancial.JBFinancial_backend.repositories.ContaRepository;
import com.JBFinancial.JBFinancial_backend.repositories.GrupoRepository;
import com.JBFinancial.JBFinancial_backend.repositories.SubgrupoRepository;
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

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private SubgrupoRepository subgrupoRepository;

    private static final DecimalFormat decimalFormat = new DecimalFormat("#.00");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public List<BaseMatrixResponseDTO> getBaseMatrix(String userId) {
        List<Object[]> results = baseRepository.findBaseMatrixByUserId(userId);
        List<BaseMatrixResponseDTO> responseList = new ArrayList<>();

        for (Object[] row : results) {
            BaseMatrixResponseDTO response = new BaseMatrixResponseDTO(
                    ((java.sql.Timestamp) row[2]).toLocalDateTime().getYear(), // ano
                    ((java.sql.Timestamp) row[2]).toLocalDateTime().getMonthValue(), // mes
                    ((java.sql.Timestamp) row[2]).toLocalDateTime().format(dateFormatter), // data
                    ((java.sql.Timestamp) row[2]).toLocalDateTime().format(timeFormatter), // horario
                    (String) row[9], // contaNome
                    (String) row[10], // tipoConta
                    decimalFormat.format((Double) row[4]), // valor
                    (Boolean) row[8] ? "credito" : "debito", // creditoDebito
                    (Boolean) row[5] ? "Sim" : "Não", // impactaCaixa
                    (Boolean) row[6] ? "Sim" : "Não", // impactaDre
                    (String) row[7], // descricao
                    (String) row[11], // numeroConta
                    (String) row[12], // grupoNome
                    (String) row[13] // subgrupoNome
            );
            responseList.add(response);
        }

        return responseList;
    }

    public static class BaseResponse {
        private int ano;
        private int mes;
        private String data;
        private String horario;
        private String nomeDaConta;
        private String valor;
        private String creditoDebito;
        private String impactaCaixa;
        private String impactaDre;
        private String descricao;

        public BaseResponse(int ano, int mes, String data, String horario, String nomeDaConta, String valor, String creditoDebito, String impactaCaixa, String impactaDre, String descricao) {
            this.ano = ano;
            this.mes = mes;
            this.data = data;
            this.horario = horario;
            this.nomeDaConta = nomeDaConta;
            this.valor = valor;
            this.creditoDebito = creditoDebito;
            this.impactaCaixa = impactaCaixa;
            this.impactaDre = impactaDre;
            this.descricao = descricao;
        }

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
            } else if (contaTipo.equals("Saida")) {
                saidas.set(month, saidas.get(month) + base.getValor());
            }
        }

        matrix.add(months);
        matrix.add(entradas.stream().map(decimalFormat::format).collect(Collectors.toList()));
        matrix.add(saidas.stream().map(decimalFormat::format).collect(Collectors.toList()));

        return matrix;
    }
}