-- Alter table produto to replace categoria with categoria_id
ALTER TABLE produto ADD COLUMN categoria_id UUID;

-- Add foreign key constraint
ALTER TABLE produto ADD CONSTRAINT fk_produto_categoria
    FOREIGN KEY (categoria_id) REFERENCES categoria_produto(id) ON DELETE RESTRICT;

-- Drop old categoria column (after migration of data if needed)
-- Users should migrate their data first, then uncomment this line in a future migration
-- ALTER TABLE produto DROP COLUMN categoria;

