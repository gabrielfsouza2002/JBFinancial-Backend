package com.JBFinancial.JBFinancial_backend.repositories;

import com.JBFinancial.JBFinancial_backend.domain.categoriaProduto.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, UUID> {
    List<CategoriaProduto> findByUserId(String userId);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM categoria_produto c WHERE c.nome = :nome AND c.userId = :userId")
    boolean existsByNomeAndUserId(@Param("nome") String nome, @Param("userId") String userId);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM categoria_produto c WHERE c.nome = :nome AND c.id <> :id AND c.userId = :userId")
    boolean existsByNomeAndUserIdAndIdNot(@Param("nome") String nome, @Param("userId") String userId, @Param("id") UUID id);
}

