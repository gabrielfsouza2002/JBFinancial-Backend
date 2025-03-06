-- src/main/resources/db/migration/V4__create-table-contas.sql

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE contas (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id TEXT NOT NULL,
    tipo TEXT NOT NULL,
    numero_conta TEXT NOT NULL,
    nome TEXT NOT NULL,
    id_grupo UUID NOT NULL,
    id_subgrupo UUID NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_grupo FOREIGN KEY (id_grupo) REFERENCES grupo(id),
    CONSTRAINT fk_subgrupo FOREIGN KEY (id_subgrupo) REFERENCES subgrupo(id),
    CONSTRAINT unique_user_conta UNIQUE (user_id, numero_conta),
    CONSTRAINT unique_user_nome UNIQUE (user_id, nome)
);

UPDATE contas
SET nome = UPPER(nome);