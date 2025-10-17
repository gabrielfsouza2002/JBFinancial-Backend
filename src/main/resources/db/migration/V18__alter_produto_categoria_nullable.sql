-- Tornar a coluna categoria nullable, já que agora usamos categoria_id
ALTER TABLE produto ALTER COLUMN categoria DROP NOT NULL;

