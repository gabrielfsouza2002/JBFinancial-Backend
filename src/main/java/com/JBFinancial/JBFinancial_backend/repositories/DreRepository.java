// src/main/java/com/JBFinancial/JBFinancial_backend/repositories/DreRepository.java

package com.JBFinancial.JBFinancial_backend.repositories;

import com.JBFinancial.JBFinancial_backend.domain.dre.Dre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

import java.util.Optional;

public interface DreRepository extends JpaRepository<Dre, UUID> {
    List<Dre> findByUserId(String userId);
    Optional<Dre> findByUserIdAndYearAndMonth(String userId, int year, int month);
}