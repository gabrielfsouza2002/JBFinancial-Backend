package com.JBFinancial.JBFinancial_backend.repositories;

import com.JBFinancial.JBFinancial_backend.domain.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
    List<Produto> findByUserId(String userId);
    boolean existsByNomeAndUserId(String nome, String userId);
    boolean existsByCodigoAndUserId(String codigo, String userId);
    boolean existsByNomeAndIdNotAndUserId(String nome, UUID id, String userId);
    boolean existsByCodigoAndIdNotAndUserId(String codigo, UUID id, String userId);
}