package com.JBFinancial.JBFinancial_backend.domain.segmento;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "segmento", uniqueConstraints = {
        @UniqueConstraint(name = "unique_segmento_user", columnNames = {"nome", "user_id"})
})
@Entity(name = "segmento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Segmento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", nullable = false, length = 20)
    private String nome;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @PrePersist
    @PreUpdate
    private void convertToUpperCase() {
        if (this.nome != null) {
            this.nome = this.nome.toUpperCase();
        }
    }

    public Segmento(SegmentoRequestDTO data) {
        this.nome = data.nome().toUpperCase();
    }
}

