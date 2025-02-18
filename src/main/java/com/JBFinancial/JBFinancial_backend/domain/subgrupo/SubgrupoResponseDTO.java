package com.JBFinancial.JBFinancial_backend.domain.subgrupo;

import java.util.UUID;

public record SubgrupoResponseDTO(UUID id, String idUser, String nome, UUID idGrupo, String digitoSubgrupo) {
    public SubgrupoResponseDTO(Subgrupo subgrupo) {
        this(subgrupo.getId(), subgrupo.getIdUser(), subgrupo.getNome(), subgrupo.getIdGrupo(), subgrupo.getDigitoSubgrupo());
    }
}