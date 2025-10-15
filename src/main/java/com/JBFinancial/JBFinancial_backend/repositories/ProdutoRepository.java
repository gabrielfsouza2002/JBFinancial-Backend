package com.JBFinancial.JBFinancial_backend.repositories;

import com.JBFinancial.JBFinancial_backend.domain.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
    List<Produto> findByUserId(String userId);
    
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM produto p WHERE p.nome_produto = :nome AND p.userId = :userId")
    boolean existsByNomeProdutoAndUserId(@Param("nome") String nome, @Param("userId") String userId);
    
    boolean existsByCodigoAndUserId(String codigo, String userId);
    
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM produto p WHERE p.nome_produto = :nome AND p.id <> :id AND p.userId = :userId")
    boolean existsByNomeProdutoAndIdNotAndUserId(@Param("nome") String nome, @Param("id") UUID id, @Param("userId") String userId);
    
    boolean existsByCodigoAndIdNotAndUserId(String codigo, UUID id, String userId);
}