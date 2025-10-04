CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE produto (
                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                         user_id TEXT NOT NULL,
                         codigo TEXT NOT NULL,
                         nome TEXT NOT NULL,
                         descricao TEXT NOT NULL,
                         categoria TEXT NOT NULL,
                         CONSTRAINT fk_user_produto FOREIGN KEY (user_id) REFERENCES users(id),
                         CONSTRAINT unique_user_codigo UNIQUE (user_id, codigo),
                         CONSTRAINT unique_user_nome UNIQUE (user_id, nome)
);

UPDATE produto
SET nome = UPPER(nome),
    codigo = UPPER(codigo),
    categoria = UPPER(categoria);