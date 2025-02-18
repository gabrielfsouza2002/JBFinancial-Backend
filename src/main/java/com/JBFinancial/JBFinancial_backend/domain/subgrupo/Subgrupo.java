package com.JBFinancial.JBFinancial_backend.domain.subgrupo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "subgrupo")
@Entity(name = "subgrupo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Subgrupo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "id_user")
    private String idUser;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "id_grupo", nullable = false)
    private UUID idGrupo;

    @Column(name = "digito_subgrupo", nullable = false)
    private String digitoSubgrupo;
}