CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE contas (
    id SERIAL PRIMARY KEY,
    user_id TEXT NOT NULL,
    conta_id UUID NOT NULL UNIQUE DEFAULT uuid_generate_v4(),
    tipo TEXT NOT NULL,
    numero_conta TEXT NOT NULL UNIQUE,
    nome TEXT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);