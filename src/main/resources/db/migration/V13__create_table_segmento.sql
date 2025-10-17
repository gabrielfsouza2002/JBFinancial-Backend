-- Create segmento table
CREATE TABLE segmento (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome VARCHAR(20) NOT NULL,
    user_id TEXT NOT NULL,
    CONSTRAINT fk_user_segmento FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT unique_segmento_user UNIQUE (nome, user_id)
);

