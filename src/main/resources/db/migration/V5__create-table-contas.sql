CREATE TABLE contas (
    conta_ID UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usr_ID UUID REFERENCES usuario(usr_ID) NOT NULL,
    nome_conta VARCHAR(100) NOT NULL,
    tipo_conta VARCHAR(20) NOT NULL CHECK (tipo_conta IN ('ENTRADA', 'SAIDA')),
    valor_padrao NUMERIC(10,2) NOT NULL DEFAULT 0.00,
    categoria VARCHAR(50),
    ativo BOOLEAN NOT NULL DEFAULT true,
    UNIQUE (usr_ID, nome_conta)
);