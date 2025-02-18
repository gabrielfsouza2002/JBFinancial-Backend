// src/main/java/com/JBFinancial/JBFinancial_backend/domain/subgrupo/SubgrupoResponseDTO.java

package com.JBFinancial.JBFinancial_backend.subgrupo;

import java.util.UUID;

public record SubgrupoResponseDTO(UUID id, String idUser, String nome, UUID idGrupo) {
    public SubgrupoResponseDTO(Subgrupo subgrupo) {
        this(subgrupo.getId(), subgrupo.getIdUser(), subgrupo.getNome(), subgrupo.getIdGrupo());
    }
}