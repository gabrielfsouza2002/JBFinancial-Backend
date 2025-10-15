package com.JBFinancial.JBFinancial_backend.repositories;

import com.JBFinancial.JBFinancial_backend.domain.fornecedor.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface FornecedorRepository extends JpaRepository<Fornecedor, UUID> {
    List<Fornecedor> findByUserId(String userId);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM fornecedor f WHERE f.nome_fornecedor = :nome AND f.userId = :userId")
    boolean existsByNomeFornecedorAndUserId(@Param("nome") String nome, @Param("userId") String userId);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM fornecedor f WHERE f.nome_fornecedor = :nome AND f.id <> :id AND f.userId = :userId")
    boolean existsByNomeFornecedorAndIdNotAndUserId(@Param("nome") String nome, @Param("id") UUID id, @Param("userId") String userId);
}

