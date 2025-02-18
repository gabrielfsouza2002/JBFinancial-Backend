CREATE TABLE subgrupo (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_user TEXT,
    nome TEXT NOT NULL,
    id_grupo UUID NOT NULL,
    CONSTRAINT fk_grupo FOREIGN KEY (id_grupo) REFERENCES grupo(id),
    CONSTRAINT fk_user FOREIGN KEY (id_user) REFERENCES users(id)
);