package com.JBFinancial.JBFinancial_backend.repositories;

import com.JBFinancial.JBFinancial_backend.domain.conta.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContaRepository extends JpaRepository<Conta, UUID> {
    List<Conta> findByUserId(String userId);
    boolean existsByNumeroConta(String numeroConta);
    boolean existsByNumeroContaAndIdNot(String numeroConta, UUID id);
}