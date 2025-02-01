package com.JBFinancial.JBFinancial_backend.domain.base;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "base")
@Entity(name = "base")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "data", nullable = false)
    private LocalDateTime data;

    @Column(name = "conta_id", nullable = false)
    private UUID contaId;

    @Column(name = "valor", nullable = false)
    private Double valor;

    @Column(name = "impacta_caixa", nullable = false)
    private Boolean impactaCaixa;

    @Column(name = "impacta_dre", nullable = false)
    private Boolean impactaDre;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    public Base(BaseRequestDTO data) {
        this.userId = data.userId();
        this.contaId = data.contaId();
        this.valor = data.valor();
        this.impactaCaixa = data.impactaCaixa();
        this.impactaDre = data.impactaDre();
        this.descricao = data.descricao();
    }

    @PrePersist
    protected void onCreate() {
        data = LocalDateTime.now();
    }
}