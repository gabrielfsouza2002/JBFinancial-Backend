CREATE TABLE usuario (
     usr_ID UUID PRIMARY KEY DEFAULT gen_random_uuid(),
     login VARCHAR(50) UNIQUE NOT NULL,
     email VARCHAR(100) UNIQUE NOT NULL,
     password VARCHAR(60) NOT NULL, -- Tamanho específico para bcrypt hash
     nome VARCHAR(100) NOT NULL,
     cnpj VARCHAR(14) UNIQUE NOT NULL,
     pacote_name VARCHAR(30) REFERENCES pacote(pacote_name),
     created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);