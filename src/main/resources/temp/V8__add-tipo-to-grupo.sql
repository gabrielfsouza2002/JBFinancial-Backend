-- src/main/resources/db/migration/V5__add-tipo-to-grupo.sql

ALTER TABLE grupo ADD COLUMN tipo TEXT NOT NULL;

-- Atualizar os dados existentes com um valor padrão, se necessário
UPDATE grupo SET tipo = 'Entrada' WHERE nome IN ('Receita Bruta');
UPDATE grupo SET tipo = 'Saida' WHERE nome IN ('Deduções de Receita', 'Custo dos Bens e Serviços Vendidos', 'Despesas Operacionais', 'Despesas e Receitas Financeiras', 'Impostos Sobre Lucro');