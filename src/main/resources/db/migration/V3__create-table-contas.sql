-- src/main/resources/db/migration/V3__create-table-contas.sql

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE contas (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id TEXT NOT NULL,
    tipo TEXT NOT NULL,
    numero_conta TEXT NOT NULL,
    nome TEXT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT unique_user_conta UNIQUE (user_id, numero_conta)
);