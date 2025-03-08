// src/main/java/com/JBFinancial/JBFinancial_backend/domain/base/BaseMatrixResponseDTO.java

package com.JBFinancial.JBFinancial_backend.domain.base;

import java.time.format.DateTimeFormatter;

public record BaseMatrixResponseDTO(
        int ano,
        int mes,
        String data,
        String horario,
        String nomeDaConta,
        String tipoConta,
        String valor,
        String creditoDebito,
        String impactaCaixa,
        String impactaDre,
        String descricao,
        String numeroConta,
        String grupo,
        String subgrupo
) {
    public BaseMatrixResponseDTO(
            int ano,
            int mes,
            String data,
            String horario,
            String nomeDaConta,
            String tipoConta,
            String valor,
            String creditoDebito,
            String impactaCaixa,
            String impactaDre,
            String descricao,
            String numeroConta,
            String grupo,
            String subgrupo
    ) {
        this.ano = ano;
        this.mes = mes;
        this.data = data;
        this.horario = horario;
        this.nomeDaConta = nomeDaConta;
        this.tipoConta = tipoConta;
        this.valor = valor;
        this.creditoDebito = creditoDebito;
        this.impactaCaixa = impactaCaixa;
        this.impactaDre = impactaDre;
        this.descricao = descricao;
        this.numeroConta = numeroConta;
        this.grupo = grupo;
        this.subgrupo = subgrupo;
    }
}