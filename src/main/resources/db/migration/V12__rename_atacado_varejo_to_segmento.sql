-- Rename atacado_varejo column to segmento and update constraints
-- Drop the old check constraint
ALTER TABLE cliente DROP CONSTRAINT IF EXISTS check_atacado_varejo;

-- Rename the column
ALTER TABLE cliente RENAME COLUMN atacado_varejo TO segmento;

-- Add new constraint to limit to 20 characters
ALTER TABLE cliente ADD CONSTRAINT check_segmento_length
    CHECK (segmento IS NULL OR LENGTH(segmento) <= 20);

