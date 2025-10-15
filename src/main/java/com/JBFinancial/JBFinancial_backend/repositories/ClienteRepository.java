package com.JBFinancial.JBFinancial_backend.repositories;

import com.JBFinancial.JBFinancial_backend.domain.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    List<Cliente> findByUserId(String userId);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM cliente c WHERE c.nome_cliente = :nome AND c.tipo_pessoa = :tipo AND c.descricao = :descricao AND c.userId = :userId")
    boolean existsByNomeClienteAndTipoPessoaAndDescricaoAndUserId(@Param("nome") String nome, @Param("tipo") String tipo, @Param("descricao") String descricao, @Param("userId") String userId);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM cliente c WHERE c.nome_cliente = :nome AND c.tipo_pessoa = :tipo AND c.descricao = :descricao AND c.userId = :userId AND c.id <> :id")
    boolean existsByNomeClienteAndTipoPessoaAndDescricaoAndUserIdAndIdNot(@Param("nome") String nome, @Param("tipo") String tipo, @Param("descricao") String descricao, @Param("userId") String userId, @Param("id") UUID id);
}
