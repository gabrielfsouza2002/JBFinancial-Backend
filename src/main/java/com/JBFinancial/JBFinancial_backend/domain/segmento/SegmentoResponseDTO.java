package com.JBFinancial.JBFinancial_backend.domain.segmento;

import java.util.UUID;

public record SegmentoResponseDTO(UUID id, String userId, String nome) {
    public SegmentoResponseDTO(Segmento segmento) {
        this(segmento.getId(), segmento.getUserId(), segmento.getNome());
    }
}

