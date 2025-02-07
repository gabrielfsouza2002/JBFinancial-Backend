// src/main/java/com/JBFinancial/JBFinancial_backend/domain/dre/Dre.java

package com.JBFinancial.JBFinancial_backend.domain.dre;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "dre")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Dre {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(name = "month", nullable = false)
    private int month;

    @Column(name = "custo_operacional")
    private double custoOperacional;

    @Column(name = "propriedades")
    private double propriedades;

    @Column(name = "bustech")
    private double bustech;

    @Column(name = "marketing")
    private double marketing;

    @Column(name = "financas_e_juridico")
    private double financasEJuridico;

    @Column(name = "custos_operacionais_diretos")
    private double custosOperacionaisDiretos;

    @Column(name = "impostos_simples_nacional")
    private double impostosSimplesNacional;

    @Column(name = "outros_impostos_e_taxas")
    private double outrosImpostosETaxas;

    @Column(name = "receita_liquida")
    private double receitaLiquida;

    @Column(name = "custo_dos_bens_servicos_vendidos")
    private double custoDosBensServicosVendidos;

    @Column(name = "despesas_receitas_operacionais")
    private double despesasReceitasOperacionais;

    @Column(name = "despesas_financeiras")
    private double despesasFinanceiras;

    @Column(name = "resultado_bruto")
    private double resultadoBruto;

    @Column(name = "ebitda")
    private double ebitda;

    @Column(name = "lucro_liquido_do_exercicio")
    private double lucroLiquidoDoExercicio;

    @Column(name = "margem_bruta")
    private double margemBruta;

    @Column(name = "margem_ebitda")
    private double margemEbitda;

    @Column(name = "margem_liquida")
    private double margemLiquida;

    public Dre(DreRequestDTO data) {
        this.userId = data.userId();
        this.year = data.year();
        this.month = data.month();
        this.custoOperacional = data.custoOperacional();
        this.propriedades = data.propriedades();
        this.bustech = data.bustech();
        this.marketing = data.marketing();
        this.financasEJuridico = data.financasEJuridico();
        this.custosOperacionaisDiretos = data.custosOperacionaisDiretos();
        this.impostosSimplesNacional = data.impostosSimplesNacional();
        this.outrosImpostosETaxas = data.outrosImpostosETaxas();
        this.receitaLiquida = data.receitaLiquida();
        this.custoDosBensServicosVendidos = data.custoDosBensServicosVendidos();
        this.despesasReceitasOperacionais = data.despesasReceitasOperacionais();
        this.despesasFinanceiras = data.despesasFinanceiras();
        this.resultadoBruto = data.resultadoBruto();
        this.ebitda = data.ebitda();
        this.lucroLiquidoDoExercicio = data.lucroLiquidoDoExercicio();
        this.margemBruta = data.margemBruta();
        this.margemEbitda = data.margemEbitda();
        this.margemLiquida = data.margemLiquida();
    }
}