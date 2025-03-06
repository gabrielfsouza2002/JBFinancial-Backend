package com.JBFinancial.JBFinancial_backend.domain.grupo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "grupo")
@Entity(name = "grupo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "digito_grupo", nullable = false)
    private String digitoGrupo;

    @Column(name = "tipo", nullable = false)
    private String tipo; // Novo campo tipo
}