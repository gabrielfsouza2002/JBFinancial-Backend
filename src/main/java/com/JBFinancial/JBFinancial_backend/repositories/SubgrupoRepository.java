package com.JBFinancial.JBFinancial_backend.repositories;

import com.JBFinancial.JBFinancial_backend.domain.subgrupo.Subgrupo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SubgrupoRepository extends JpaRepository<Subgrupo, UUID> {
    List<Subgrupo> findByIdUser(String userId);
    List<Subgrupo> findByIdUserOrIdUserIsNull(String userId);
    long countByIdUserAndIdGrupo(String userId, UUID idGrupo);
}