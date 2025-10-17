package com.JBFinancial.JBFinancial_backend.repositories;

import com.JBFinancial.JBFinancial_backend.domain.segmento.Segmento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SegmentoRepository extends JpaRepository<Segmento, UUID> {
    List<Segmento> findByUserId(String userId);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM segmento s WHERE s.nome = :nome AND s.userId = :userId")
    boolean existsByNomeAndUserId(@Param("nome") String nome, @Param("userId") String userId);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM segmento s WHERE s.nome = :nome AND s.userId = :userId AND s.id <> :id")
    boolean existsByNomeAndUserIdAndIdNot(@Param("nome") String nome, @Param("userId") String userId, @Param("id") UUID id);
}

