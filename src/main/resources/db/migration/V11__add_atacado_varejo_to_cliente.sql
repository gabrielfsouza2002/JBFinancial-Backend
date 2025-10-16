-- Add atacado_varejo column to cliente table
-- Making it nullable to support existing records
ALTER TABLE cliente ADD COLUMN atacado_varejo TEXT;

-- Add check constraint to ensure only 'ATACADO' or 'VAREJO' values are allowed (when not null)
ALTER TABLE cliente ADD CONSTRAINT check_atacado_varejo
    CHECK (atacado_varejo IS NULL OR atacado_varejo IN ('ATACADO', 'VAREJO'));

-- Optionally, update existing records to have a default value
-- Uncomment the line below if you want to set a default for existing records
-- UPDATE cliente SET atacado_varejo = 'VAREJO' WHERE atacado_varejo IS NULL;

