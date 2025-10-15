-- src/main/resources/db/migration/V9__create-table-base.sql

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
    debt_cred BOOLEAN NOT NULL,
    id_produto UUID,
    id_cliente UUID,
    id_fornecedor UUID,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_conta FOREIGN KEY (conta_id) REFERENCES contas(id),
    CONSTRAINT fk_base_produto FOREIGN KEY (id_produto) REFERENCES produto(id),
    CONSTRAINT fk_base_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id),
    CONSTRAINT fk_base_fornecedor FOREIGN KEY (id_fornecedor) REFERENCES fornecedor(id)
);