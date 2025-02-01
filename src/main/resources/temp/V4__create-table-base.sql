CREATE TABLE base (
    id SERIAL PRIMARY KEY,
    user_id TEXT NOT NULL,
    data TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    conta_id BIGINT NOT NULL,
    valor DOUBLE PRECISION NOT NULL,
    impacta_caixa BOOLEAN NOT NULL,
    impacta_dre BOOLEAN NOT NULL,
    descricao TEXT NOT NULL,
    CONSTRAINT fk_conta FOREIGN KEY (conta_id) REFERENCES contas(id)
);