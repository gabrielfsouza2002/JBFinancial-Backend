-- Script para corrigir o historico do Flyway
-- Execute este script no seu banco de dados PostgreSQL

-- Verificar o estado atual das migrations
SELECT * FROM flyway_schema_history ORDER BY installed_rank;

-- Remover a migration V14 com erro (se existir)
DELETE FROM flyway_schema_history WHERE version = '14' AND success = false;

-- Se a V14 foi marcada como sucesso mas falhou, remova tambem:
DELETE FROM flyway_schema_history WHERE version = '14';

