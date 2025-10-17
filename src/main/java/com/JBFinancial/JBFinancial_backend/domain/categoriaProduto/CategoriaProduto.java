package com.JBFinancial.JBFinancial_backend.domain.categoriaProduto;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "categoria_produto", uniqueConstraints = {
        @UniqueConstraint(name = "unique_user_nome_categoria", columnNames = {"user_id", "nome"})
})
@Entity(name = "categoria_produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class CategoriaProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "descricao", length = 255)
    private String descricao;

    @PrePersist
    @PreUpdate
    private void convertToUpperCase() {
        this.nome = this.nome.toUpperCase();
    }

    public CategoriaProduto(CategoriaProdutoRequestDTO data) {
        this.nome = data.nome().toUpperCase();
        this.descricao = data.descricao();
    }
}

