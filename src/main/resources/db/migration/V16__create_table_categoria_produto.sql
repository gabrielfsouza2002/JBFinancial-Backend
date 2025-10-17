-- Create table categoria_produto
CREATE TABLE categoria_produto (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id VARCHAR(255) NOT NULL,
    nome VARCHAR(50) NOT NULL,
    descricao VARCHAR(255),
    CONSTRAINT unique_user_nome_categoria UNIQUE (user_id, nome)
);

