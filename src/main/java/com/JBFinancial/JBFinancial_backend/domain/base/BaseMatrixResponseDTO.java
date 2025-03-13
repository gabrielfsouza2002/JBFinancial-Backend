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
        String numeroConta,
        String grupo,
        String subgrupo,
        String valor,
        String creditoDebito,
        String impactaCaixa,
        String impactaDre,
        String descricao

) {
    public BaseMatrixResponseDTO(Base base, String nomeDaConta, String tipoConta, String valor, String creditoDebito, String impactaCaixa, String impactaDre, String numeroConta, String grupo, String subgrupo) {
        this(
                base.getData().getYear(),
                base.getData().getMonthValue(),
                base.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                base.getData().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                nomeDaConta,
                tipoConta,
                numeroConta,
                grupo,
                subgrupo,
                valor,
                creditoDebito,
                impactaCaixa,
                impactaDre,
                base.getDescricao()
        );
    }
}