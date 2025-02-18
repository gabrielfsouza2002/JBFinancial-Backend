CREATE TABLE grupo (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome TEXT NOT NULL,
    digito_grupo CHAR(1) NOT NULL
);