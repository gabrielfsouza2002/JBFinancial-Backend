CREATE TABLE contas (
    id SERIAL PRIMARY KEY,
    user_id TEXT NOT NULL,
    conta_id BIGINT NOT NULL UNIQUE,
    tipo TEXT NOT NULL,
    numero_conta TEXT NOT NULL UNIQUE
);