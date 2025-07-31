package com.JBFinancial.JBFinancial_backend.domain.produto;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "produto", uniqueConstraints = {
        @UniqueConstraint(name = "unique_user_codigo", columnNames = {"user_id", "codigo"}),
        @UniqueConstraint(name = "unique_user_nome", columnNames = {"user_id", "nome"})
})
@Entity(name = "produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "codigo", nullable = false, length = 20)
    private String codigo;

    @Column(name = "nome", nullable = false, length = 30)
    private String nome;

    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;

    @Column(name = "categoria", nullable = false, length = 50)
    private String categoria;

    @PrePersist
    @PreUpdate
    private void convertToUpperCase() {
        this.nome = this.nome.toUpperCase();
        this.codigo = this.codigo.toUpperCase();
        this.categoria = this.categoria.toUpperCase();
    }

    public Produto(ProdutoRequestDTO data) {
        this.codigo = data.codigo().toUpperCase();
        this.nome = data.nome().toUpperCase();
        this.descricao = data.descricao();
        this.categoria = data.categoria().toUpperCase();
    }
}