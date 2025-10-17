-- Rename segmento column to id_segmento and change to UUID type
-- Add the new column only if it doesn't exist
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns
                   WHERE table_name = 'cliente' AND column_name = 'id_segmento') THEN
        ALTER TABLE cliente ADD COLUMN id_segmento UUID;
    END IF;
END $$;

-- Add foreign key constraint only if it doesn't exist
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.table_constraints
                   WHERE constraint_name = 'fk_cliente_segmento') THEN
        ALTER TABLE cliente ADD CONSTRAINT fk_cliente_segmento
            FOREIGN KEY (id_segmento) REFERENCES segmento(id) ON DELETE SET NULL;
    END IF;
END $$;

-- Drop the old segmento column
ALTER TABLE cliente DROP COLUMN IF EXISTS segmento;

-- Drop the old constraint if exists
ALTER TABLE cliente DROP CONSTRAINT IF EXISTS check_segmento_length;
