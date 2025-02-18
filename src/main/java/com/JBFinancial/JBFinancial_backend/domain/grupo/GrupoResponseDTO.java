package com.JBFinancial.JBFinancial_backend.domain.grupo;

import java.util.UUID;

public record GrupoResponseDTO(UUID id, String nome, String digitoGrupo) {
    public GrupoResponseDTO(Grupo grupo) {
        this(grupo.getId(), grupo.getNome(), grupo.getDigitoGrupo());
    }
}