// src/main/java/com/JBFinancial/JBFinancial_backend/Services/BaseService.java

package com.JBFinancial.JBFinancial_backend.Services;

import com.JBFinancial.JBFinancial_backend.domain.base.Base;
import com.JBFinancial.JBFinancial_backend.domain.base.BaseMatrixResponseDTO;
import com.JBFinancial.JBFinancial_backend.domain.conta.Conta;
import com.JBFinancial.JBFinancial_backend.repositories.BaseRepository;
import com.JBFinancial.JBFinancial_backend.repositories.ContaRepository;
import com.JBFinancial.JBFinancial_backend.repositories.GrupoRepository;
import com.JBFinancial.JBFinancial_backend.repositories.SubgrupoRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
        List<Base> baseEntries = baseRepository.findByUserIdWithDetails(userId);
        List<BaseMatrixResponseDTO> responseList = new ArrayList<>();

        for (Base base : baseEntries) {
            var conta = base.getConta();
            String contaNome = conta.getNome();
            String tipoConta = conta.getTipo();
            String valor = decimalFormat.format(Math.abs(base.getValor()));            String creditoDebito = base.getDebtCred() ? "credito" : "debito";
            String impactaCaixa = base.getImpactaCaixa() ? "Sim" : "Não";
            String impactaDre = base.getImpactaDre() ? "Sim" : "Não";
            String numeroConta = conta.getNumeroConta();
            String grupoNome = conta.getGrupo().getNome();
            String subgrupoNome = conta.getSubgrupo().getNome();

            BaseMatrixResponseDTO response = new BaseMatrixResponseDTO(base, contaNome, tipoConta, valor, creditoDebito, impactaCaixa, impactaDre, numeroConta, grupoNome, subgrupoNome);
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

    public void importBase(MultipartFile file, String userId) throws IOException, CsvException {
        List<Base> bases = new ArrayList<>();
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("pt", "BR"));

        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            List<String[]> rows = reader.readAll();
            if (rows.isEmpty()) {
                return;
            }

            // Read the header row and create a map of header names to indices
            String[] headers = rows.get(0);
            Map<String, Integer> headerMap = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                headerMap.put(headers[i].toLowerCase(), i);
            }

            for (int i = 1; i < rows.size(); i++) {
                String[] row = rows.get(i);
                if (row.length < 1) continue; // Ignore invalid rows

                try {
                    // Use headerMap to get the values
                    String nomeConta = row[headerMap.get("nome_conta")].toUpperCase();
                    double valor = parseValor(row[headerMap.get("valor")]);

                    boolean impactaCaixa = parseBoolean(row[headerMap.get("impacta_caixa")]);
                    boolean impactaDre = parseBoolean(row[headerMap.get("impacta_dre")]);
                    String descricao = row[headerMap.get("descricao")];
                    boolean debtCred = parseDebtCred(row[headerMap.get("debito_credito")]);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSSSSS");
                    LocalDateTime data = LocalDateTime.parse(row[headerMap.get("data")] + " 00:00:00.000000", formatter);

                    if (!debtCred) {
                        valor = -valor;
                    }

                    Optional<Conta> conta = contaRepository.findByNomeAndUserId(nomeConta, userId);
                    if (conta.isPresent()) {
                        Base base = new Base();
                        base.setConta(conta.get());
                        base.setContaId(conta.get().getId()); // Ensure conta_id is set
                        base.setValor(valor);
                        base.setImpactaCaixa(impactaCaixa);
                        base.setImpactaDre(impactaDre);
                        base.setDescricao(descricao);
                        base.setDebtCred(debtCred);
                        base.setData(data);
                        base.setUserId(userId);

                        bases.add(base);
                    } else {
                        System.err.println("Conta not found for nomeConta: " + nomeConta + " and userId: " + userId);
                    }
                    System.out.println("nomeConta: " + nomeConta + "  " + " valor: " + valor + "  " + " impactaCaixa: " + impactaCaixa + "  " + " impactaDre: " + impactaDre + "  " + " descricao: " + descricao + "  " + " debtCred: " + debtCred + "  " + " data: " + data);

                } catch (Exception e) {
                    System.err.println("Error processing row: " + Arrays.toString(row));
                    e.printStackTrace();
                }
            }
        }

        baseRepository.saveAll(bases);
    }

    private double parseValor(String valorStr) throws ParseException {
        if (valorStr == null || valorStr.isEmpty()) {
            return 0.0;
        }
        valorStr = valorStr.trim();
        // Remove all dots except the last one
        int lastCommaIndex = valorStr.lastIndexOf(',');
        int lastDotIndex = valorStr.lastIndexOf('.');
        if (lastCommaIndex > lastDotIndex) {
            valorStr = valorStr.replace(".", "");
            valorStr = valorStr.replace(",", ".");
        } else {
            valorStr = valorStr.replace(",", "");
        }
        BigDecimal valor = new BigDecimal(valorStr);
        valor = valor.setScale(2, RoundingMode.HALF_UP);
        return valor.doubleValue();
    }

    private boolean parseBoolean(String value) {
        value = value.trim().toLowerCase();
        switch (value) {
            case "sim":
            case "true":
            case "1":
                return true;
            case "nao":
            case "não":
            case "false":
            case "0":
                return false;
            default:
                throw new IllegalArgumentException("Invalid boolean value: " + value);
        }
    }

    private boolean parseDebtCred(String value) {
        value = value.trim().toLowerCase();
        switch (value) {
            case "credito":
            case "crédito":
            case "1":
            case "true":
                return true;
            case "debito":
            case "débito":
            case "0":
            case "false":
                return false;
            default:
                throw new IllegalArgumentException("Invalid debt/credit value: " + value);
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
                saidas.set(month, saidas.get(month) - base.getValor());
            }
        }

        matrix.add(months);
        matrix.add(entradas.stream().map(decimalFormat::format).collect(Collectors.toList()));
        matrix.add(saidas.stream().map(decimalFormat::format).collect(Collectors.toList()));

        return matrix;
    }
}