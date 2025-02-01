CREATE TABLE pacote (
    pacote_name VARCHAR(30) PRIMARY KEY,
    recursos JSONB NOT NULL,
    valor NUMERIC(10,2) NOT NULL,
    duracao_dias INTEGER NOT NULL DEFAULT 30,
    ativo BOOLEAN NOT NULL DEFAULT true
);
