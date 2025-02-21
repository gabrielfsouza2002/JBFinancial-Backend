// src/main/java/com/JBFinancial/JBFinancial_backend/domain/conta/Conta.java

package com.JBFinancial.JBFinancial_backend.domain.conta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "contas")
@Entity(name = "contas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "numero_conta", nullable = false)
    private String numeroConta;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "id_grupo", nullable = false)
    private UUID idGrupo;

    @Column(name = "id_subgrupo", nullable = false)
    private UUID idSubgrupo;

    @PrePersist
    @PreUpdate
    private void convertToUpperCase() {
        this.nome = this.nome.toUpperCase();
    }

    public Conta(ContaRequestDTO data) {
        this.userId = data.userId();
        this.tipo = data.tipo();
        this.numeroConta = data.numeroConta();
        this.nome = data.nome().toUpperCase();
        this.idGrupo = data.idGrupo();
        this.idSubgrupo = data.idSubgrupo();
    }
}