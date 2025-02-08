// src/main/java/com/JBFinancial/JBFinancial_backend/repositories/ContaRepository.java

package com.JBFinancial.JBFinancial_backend.repositories;

import com.JBFinancial.JBFinancial_backend.domain.conta.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContaRepository extends JpaRepository<Conta, UUID> {
    List<Conta> findByUserId(String userId);
    boolean existsByNumeroContaAndUserId(String numeroConta, String userId);
    boolean existsByNumeroContaAndIdNotAndUserId(String numeroConta, UUID id, String userId);
    boolean existsByNomeAndUserId(String nome, String userId);
    boolean existsByNomeAndIdNotAndUserId(String nome, UUID id, String userId);
}