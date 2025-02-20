-- src/main/resources/db/migration/V5__uppercase-conta-names.sql

-- Atualiza todos os nomes de conta para caixa alta
UPDATE contas
SET nome = UPPER(nome);