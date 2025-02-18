// src/main/java/com/JBFinancial/JBFinancial_backend/repositories/SubgrupoRepository.java

package com.JBFinancial.JBFinancial_backend.repositories;

import com.JBFinancial.JBFinancial_backend.subgrupo.Subgrupo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SubgrupoRepository extends JpaRepository<Subgrupo, UUID> {
    List<Subgrupo> findByIdUser(String idUser);
}