package com.JBFinancial.JBFinancial_backend.repositories;

import com.JBFinancial.JBFinancial_backend.domain.base.Base;
import com.JBFinancial.JBFinancial_backend.domain.conta.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BaseRepository extends JpaRepository<Base, UUID> {
    List<Base> findByUserId(String userId);

    @Query("SELECT SUM(b.valor) FROM base b WHERE b.userId = :userId AND b.contaId IN (SELECT c.id FROM contas c WHERE c.tipo = 'Entrada')")
    Double sumEntradas(String userId);

    @Query("SELECT SUM(b.valor) FROM base b WHERE b.userId = :userId AND b.contaId IN (SELECT c.id FROM contas c WHERE c.tipo = 'Saída')")
    Double sumSaidas(String userId);

    @Query("SELECT b FROM base b WHERE b.userId = :userId AND b.contaId IN (SELECT c.id FROM contas c WHERE c.numeroConta LIKE :numeroContaPrefix%)")
    List<Base> findByNumeroContaPrefix(String userId, String numeroContaPrefix);

    // Add other queries as needed
}