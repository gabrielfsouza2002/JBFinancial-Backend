package com.JBFinancial.JBFinancial_backend.domain.produto;

import com.JBFinancial.JBFinancial_backend.domain.categoriaProduto.CategoriaProduto;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "produto", uniqueConstraints = {
        @UniqueConstraint(name = "unique_user_codigo", columnNames = {"user_id", "codigo"}),
        @UniqueConstraint(name = "unique_user_nome_produto", columnNames = {"user_id", "nome_produto"})
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

    @Column(name = "nome_produto", nullable = false, length = 30)
    private String nome_produto;

    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;

    @Column(name = "categoria", length = 50)
    private String categoria;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", referencedColumnName = "id")
    private CategoriaProduto categoriaProduto;


    @PrePersist
    @PreUpdate
    private void convertToUpperCase() {
        this.nome_produto = this.nome_produto.toUpperCase();
        this.codigo = this.codigo.toUpperCase();
        if (this.categoria != null) {
            this.categoria = this.categoria.toUpperCase();
        }
    }

    public Produto(ProdutoRequestDTO data) {
        this.codigo = data.codigo().toUpperCase();
        this.nome_produto = data.nome_produto().toUpperCase();
        this.descricao = data.descricao();
        if (data.categoria() != null) {
            this.categoria = data.categoria().toUpperCase();
        }
    }
}