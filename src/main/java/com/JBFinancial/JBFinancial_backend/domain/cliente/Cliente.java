package com.JBFinancial.JBFinancial_backend.domain.cliente;

import com.JBFinancial.JBFinancial_backend.domain.segmento.Segmento;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Table(name = "cliente", uniqueConstraints = {
        @UniqueConstraint(name = "unique_user_codigo", columnNames = {"user_id", "nome_cliente", "tipo_pessoa", "descricao"})
})
@Entity(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "nome_cliente", nullable = false)
    private String nome_cliente;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "tipo_pessoa", nullable = false)
    private String tipo_pessoa;

    @ManyToOne
    @JoinColumn(name = "id_segmento", nullable = true)
    private Segmento segmento;

    @PrePersist
    @PreUpdate
    private void convertToUpperCase() {
        this.nome_cliente = this.nome_cliente.toUpperCase();
        this.tipo_pessoa = this.tipo_pessoa.toUpperCase();
    }

    public Cliente(ClienteRequestDTO data) {
        this.nome_cliente = data.nome_cliente().toUpperCase();
        this.descricao = data.descricao();
        this.tipo_pessoa = data.tipo_pessoa().toUpperCase();
    }
}
