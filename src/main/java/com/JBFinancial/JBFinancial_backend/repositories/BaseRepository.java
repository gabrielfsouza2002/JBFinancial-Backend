// src/main/java/com/JBFinancial/JBFinancial_backend/repositories/BaseRepository.java

package com.JBFinancial.JBFinancial_backend.repositories;

import com.JBFinancial.JBFinancial_backend.domain.base.Base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BaseRepository extends JpaRepository<Base, UUID> {
    List<Base> findByUserId(String userId);

    @Query("SELECT SUM(b.valor) FROM base b WHERE b.userId = :userId AND b.impactaCaixa = true AND b.conta.tipo = 'Entrada'")
    Double sumEntradas(@Param("userId") String userId);

    @Query("SELECT SUM(b.valor) FROM base b WHERE b.userId = :userId AND b.impactaCaixa = true AND b.conta.tipo = 'Saida'")
    Double sumSaidas(@Param("userId") String userId);

    @Query("SELECT b FROM base b WHERE b.userId = :userId AND b.impactaCaixa = true")
    List<Base> findByUserIdAndImpactaCaixa(@Param("userId") String userId);

    @Query("SELECT b FROM base b WHERE b.userId = :userId AND b.conta.numeroConta LIKE :numeroContaPrefix%")
    List<Base> findByNumeroContaPrefix(@Param("userId") String userId, @Param("numeroContaPrefix") String numeroContaPrefix);

    @Query("SELECT DISTINCT YEAR(b.data) FROM base b WHERE b.userId = :userId ORDER BY YEAR(b.data)")
    List<Integer> findDistinctYearsByUserId(@Param("userId") String userId);

    @Query("SELECT b FROM base b WHERE b.userId = :userId AND FUNCTION('DATE', b.data) = CURRENT_DATE")
    List<Base> findByUserIdAndToday(@Param("userId") String userId);

    @Query("SELECT b FROM base b WHERE YEAR(b.data) = :year AND b.impactaCaixa = :impactaCaixa AND b.userId = :userId")
    List<Base> findByYearAndImpactaCaixaAndUserId(@Param("year") int year, @Param("impactaCaixa") boolean impactaCaixa, @Param("userId") String userId);

    @Query("SELECT b FROM base b WHERE YEAR(b.data) = :year AND b.userId = :userId")
    List<Base> findByYearAndUserId(@Param("year") int year, @Param("userId") String userId);

    @Query("SELECT b FROM base b JOIN FETCH b.conta c JOIN FETCH c.grupo g JOIN FETCH c.subgrupo s WHERE b.userId = :userId")
    List<Base> findByUserIdWithDetails(@Param("userId") String userId);
}