package com.JBFinancial.JBFinancial_backend.domain.fornecedor;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "fornecedor", uniqueConstraints = {
        @UniqueConstraint(name = "unique_fornecedor", columnNames = {"user_id", "nome_fornecedor"})
})
@Entity(name = "fornecedor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "nome_fornecedor", nullable = false)
    private String nome_fornecedor;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @PrePersist
    @PreUpdate
    private void convertToUpperCase() {
        this.nome_fornecedor = this.nome_fornecedor.toUpperCase();
    }

    public Fornecedor(FornecedorRequestDTO data) {
        this.nome_fornecedor = data.nome_fornecedor().toUpperCase();
        this.descricao = data.descricao();
    }
}

