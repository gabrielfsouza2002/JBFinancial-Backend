-- Alterar o tipo de dado das colunas login e password para TEXT
ALTER TABLE usuario
ALTER COLUMN login TYPE TEXT,
ALTER COLUMN password TYPE TEXT;

-- Adicionar a nova coluna role com validação de valores permitidos
ALTER TABLE usuario
ADD COLUMN role TEXT NOT NULL