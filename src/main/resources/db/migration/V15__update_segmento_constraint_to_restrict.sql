-- Alterar a constraint fk_cliente_segmento para ON DELETE RESTRICT
-- Isso impedirá a exclusão de segmentos que estão em uso por clientes

-- Primeiro, remover a constraint antiga
ALTER TABLE cliente DROP CONSTRAINT IF EXISTS fk_cliente_segmento;

-- Adicionar a nova constraint com ON DELETE RESTRICT
ALTER TABLE cliente ADD CONSTRAINT fk_cliente_segmento
    FOREIGN KEY (id_segmento) REFERENCES segmento(id) ON DELETE RESTRICT;

