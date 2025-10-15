CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE fornecedor (
     id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
     user_id TEXT NOT NULL,
     nome_fornecedor TEXT NOT NULL,
     descricao TEXT NOT NULL,
     CONSTRAINT fk_user_fornecedor FOREIGN KEY (user_id) REFERENCES users(id),
     CONSTRAINT unique_fornecedor UNIQUE (user_id, nome_fornecedor)
);

UPDATE fornecedor
SET nome_fornecedor = UPPER(nome_fornecedor)