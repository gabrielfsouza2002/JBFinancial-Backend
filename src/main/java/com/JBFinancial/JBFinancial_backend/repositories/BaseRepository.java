package com.JBFinancial.JBFinancial_backend.repositories;

import com.JBFinancial.JBFinancial_backend.domain.base.Base;
import com.JBFinancial.JBFinancial_backend.domain.conta.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BaseRepository extends JpaRepository<Base, UUID> {
    List<Base> findByUserId(String userId);

    @Query("SELECT SUM(b.valor) FROM base b WHERE b.userId = :userId AND b.impactaCaixa = true AND b.contaId IN (SELECT c.id FROM contas c WHERE c.tipo = 'Entrada')")
    Double sumEntradas(String userId);

    @Query("SELECT SUM(b.valor) FROM base b WHERE b.userId = :userId AND b.impactaCaixa = true AND b.contaId IN (SELECT c.id FROM contas c WHERE c.tipo = 'Saida')")
    Double sumSaidas(String userId);

    @Query("SELECT b FROM base b WHERE b.userId = :userId AND b.impactaCaixa = true")
    List<Base> findByUserIdAndImpactaCaixa(String userId);

    @Query("SELECT b FROM base b WHERE b.userId = :userId AND b.contaId IN (SELECT c.id FROM contas c WHERE c.numeroConta LIKE :numeroContaPrefix%)")
    List<Base> findByNumeroContaPrefix(String userId, String numeroContaPrefix);

    @Query("SELECT DISTINCT YEAR(b.data) FROM base b WHERE b.userId = :userId ORDER BY YEAR(b.data)")
    List<Integer> findDistinctYearsByUserId(String userId);

    @Query("SELECT b FROM base b WHERE b.userId = :userId AND FUNCTION('DATE', b.data) = CURRENT_DATE")
    List<Base> findByUserIdAndToday(String userId);

    @Query("SELECT b FROM base b WHERE YEAR(b.data) = :year AND b.impactaCaixa = :impactaCaixa AND b.userId = :userId")
    List<Base> findByYearAndImpactaCaixaAndUserId(@Param("year") int year, @Param("impactaCaixa") boolean impactaCaixa, @Param("userId") String userId);
    // Add other queries as needed
    @Query("SELECT b FROM base b WHERE YEAR(b.data) = :year AND b.userId = :userId")
    List<Base> findByYearAndUserId(@Param("year") int year, @Param("userId") String userId);

    @Query(value = "SELECT b.id, b.user_id, b.data, b.conta_id, b.valor, b.impacta_caixa, b.impacta_dre, b.descricao, b.debt_cred, " +
            "c.nome AS conta_nome, c.tipo AS conta_tipo, c.numero_conta, g.nome AS grupo_nome, s.nome AS subgrupo_nome " +
            "FROM base b " +
            "JOIN contas c ON b.conta_id = c.id " +
            "JOIN grupo g ON c.id_grupo = g.id " +
            "JOIN subgrupo s ON c.id_subgrupo = s.id " +
            "WHERE b.user_id = :userId", nativeQuery = true)
    List<Object[]> findBaseMatrixByUserId(@Param("userId") String userId);

}
