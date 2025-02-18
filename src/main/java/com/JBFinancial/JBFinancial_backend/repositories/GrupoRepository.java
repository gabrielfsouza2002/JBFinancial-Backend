// src/main/java/com/JBFinancial/JBFinancial_backend/repositories/GrupoRepository.java

package com.JBFinancial.JBFinancial_backend.repositories;

import com.JBFinancial.JBFinancial_backend.domain.grupo.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GrupoRepository extends JpaRepository<Grupo, UUID> {
}