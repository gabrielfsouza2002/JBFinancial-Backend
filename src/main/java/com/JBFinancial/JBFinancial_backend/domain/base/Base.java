package com.JBFinancial.JBFinancial_backend.domain.base;

import com.JBFinancial.JBFinancial_backend.domain.conta.Conta;
import com.JBFinancial.JBFinancial_backend.domain.produto.Produto;
import com.JBFinancial.JBFinancial_backend.domain.cliente.Cliente;
import com.JBFinancial.JBFinancial_backend.domain.fornecedor.Fornecedor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "base")
@Entity(name = "base")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Base {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "data", nullable = false)
    private LocalDateTime data;

    @Column(name = "conta_id", nullable = false)
    private UUID contaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_id", insertable = false, updatable = false)
    private Conta conta;

    @Column(name = "valor", nullable = false)
    private Double valor;

    @Column(name = "impacta_caixa", nullable = false)
    private Boolean impactaCaixa;

    @Column(name = "impacta_dre", nullable = false)
    private Boolean impactaDre;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "debt_cred", nullable = false)
    private Boolean debtCred;

    @Column(name = "id_produto")
    private UUID idProduto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto", insertable = false, updatable = false)
    private Produto produto;

    @Column(name = "id_cliente")
    private UUID idCliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", insertable = false, updatable = false)
    private Cliente cliente;

    @Column(name = "id_fornecedor")
    private UUID idFornecedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_fornecedor", insertable = false, updatable = false)
    private Fornecedor fornecedor;

    // src/main/java/com/JBFinancial/JBFinancial_backend/domain/base/Base.java

    public UUID getContaId() {
        return conta != null ? conta.getId() : null;
    }

    public Base(BaseRequestDTO data) {
        this.contaId = data.contaId();
        this.conta = data.conta();
        this.valor = data.valor();
        this.impactaCaixa = data.impactaCaixa();
        this.impactaDre = data.impactaDre();
        this.descricao = data.descricao();
        this.debtCred = data.debtCred();
        this.data = data.data() != null ? data.data() : LocalDateTime.now();
        this.idProduto = data.idProduto();
        this.idCliente = data.idCliente();
        this.idFornecedor = data.idFornecedor();
    }

    @Override
    public String toString() {
        return "Base{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", data=" + data +
                ", contaId=" + contaId +
                ", conta=" + conta +
                ", valor=" + valor +
                ", impactaCaixa=" + impactaCaixa +
                ", impactaDre=" + impactaDre +
                ", descricao='" + descricao + '\'' +
                ", debtCred=" + debtCred +
                ", idProduto=" + idProduto +
                ", idCliente=" + idCliente +
                ", idFornecedor=" + idFornecedor +
                '}';
    }
}