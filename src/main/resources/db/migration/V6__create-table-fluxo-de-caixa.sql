CREATE TABLE fluxo_de_caixa (
                                lancamento_ID UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                usr_ID UUID REFERENCES usuario(usr_ID) NOT NULL,
                                data DATE NOT NULL,
                                conta_ID UUID REFERENCES contas(conta_ID) NOT NULL,
                                valor NUMERIC(10,2) NOT NULL,
                                banco VARCHAR(50),
                                descricao TEXT,
                                created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                status VARCHAR(20) DEFAULT 'PENDENTE'
);

-- Índices para otimização
CREATE INDEX idx_fluxo_usuario_data ON fluxo_de_caixa(usr_ID, data);
CREATE INDEX idx_fluxo_conta ON fluxo_de_caixa(conta_ID);