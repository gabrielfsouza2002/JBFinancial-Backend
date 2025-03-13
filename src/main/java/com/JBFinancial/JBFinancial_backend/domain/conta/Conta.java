// src/main/java/com/JBFinancial/JBFinancial_backend/domain/conta/Conta.java

package com.JBFinancial.JBFinancial_backend.domain.conta;

import com.JBFinancial.JBFinancial_backend.domain.grupo.Grupo;
import com.JBFinancial.JBFinancial_backend.domain.subgrupo.Subgrupo;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_grupo", nullable = false)
    private Grupo grupo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_subgrupo", nullable = false)
    private Subgrupo subgrupo;

    @PrePersist
    @PreUpdate
    private void convertToUpperCase() {
        this.nome = this.nome.toUpperCase();
    }

    // src/main/java/com/JBFinancial/JBFinancial_backend/domain/conta/Conta.java
    public UUID getIdGrupo() {
        return grupo != null ? grupo.getId() : null;
    }

    public UUID getIdSubgrupo() {
        return subgrupo != null ? subgrupo.getId() : null;
    }

    public Conta(ContaRequestDTO data, String tipo) {
        this.userId = data.userId();
        this.tipo = tipo; // Setando o tipo da conta com o tipo do grupo
        this.numeroConta = data.numeroConta();
        this.nome = data.nome().toUpperCase();
        this.grupo = data.grupo();
        this.subgrupo = data.subgrupo();
    }
}