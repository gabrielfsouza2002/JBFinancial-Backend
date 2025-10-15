CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE cliente (
     id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
     user_id TEXT NOT NULL,
     nome_cliente TEXT NOT NULL,
     descricao TEXT NOT NULL,
     tipo_pessoa TEXT NOT NULL,
     CONSTRAINT fk_user_cliente FOREIGN KEY (user_id) REFERENCES users(id),
     CONSTRAINT unique_cliente UNIQUE (user_id, nome_cliente, tipo_pessoa, descricao)
);

UPDATE cliente
SET nome_cliente = UPPER(nome_cliente),
    tipo_pessoa = UPPER(tipo_pessoa);