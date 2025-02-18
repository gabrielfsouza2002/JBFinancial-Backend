-- src/main/resources/db/migration/V7__create-table-dre.sql

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE dre (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id TEXT NOT NULL,
    year INT NOT NULL,
    month INT NOT NULL,
    custo_operacional DECIMAL(15, 2),
    propriedades DECIMAL(15, 2),
    bustech DECIMAL(15, 2),
    marketing DECIMAL(15, 2),
    financas_e_juridico DECIMAL(15, 2),
    custos_operacionais_diretos DECIMAL(15, 2),
    impostos_simples_nacional DECIMAL(15, 2),
    outros_impostos_e_taxas DECIMAL(15, 2),
    receita_liquida DECIMAL(15, 2),
    custo_dos_bens_servicos_vendidos DECIMAL(15, 2),
    despesas_receitas_operacionais DECIMAL(15, 2),
    despesas_financeiras DECIMAL(15, 2),
    resultado_bruto DECIMAL(15, 2),
    ebitda DECIMAL(15, 2),
    lucro_liquido_do_exercicio DECIMAL(15, 2),
    margem_bruta DECIMAL(5, 2),
    margem_ebitda DECIMAL(5, 2),
    margem_liquida DECIMAL(5, 2),
    FOREIGN KEY (user_id) REFERENCES users(id)
);