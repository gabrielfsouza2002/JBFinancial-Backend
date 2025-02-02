-- src/main/resources/db/migration/V6__create-table-base.sql

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE base (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id TEXT NOT NULL,
    data TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    conta_id UUID NOT NULL,
    valor DOUBLE PRECISION NOT NULL,
    impacta_caixa BOOLEAN NOT NULL,
    impacta_dre BOOLEAN NOT NULL,
    descricao TEXT NOT NULL,
    debt_cred BOOLEAN NOT NULL, -- Nova coluna
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_conta FOREIGN KEY (conta_id) REFERENCES contas(id)
);