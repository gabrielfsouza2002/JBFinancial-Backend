CREATE TABLE grupo (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome TEXT NOT NULL,
    digito_grupo CHAR(1) NOT NULL
);

INSERT INTO grupo (id, nome, digito_grupo) VALUES
    ('3fec2d51-fb31-4bf5-9ab7-9c4cd8542c39', 'Receita Bruta', '1'),
    ('60dff836-0096-4135-82c6-3d6f3886ad4d', 'Deduções de Receita', '2'),
    ('0d8364ef-b7c2-46f0-ab49-3b812dd47449', 'Custo dos Bens e Serviços Vendidos', '3'),
    ('3d6b0d31-7cb5-42aa-ade4-074e2c161f22', 'Despesas Operacionais', '4'),
    ('44fc2c54-2ed2-46d7-a918-ef2e14982f66', 'Despesas e Receitas Financeiras', '5'),
    ('376e7904-87db-4c2d-9051-0357763961c3', 'Impostos Sobre Lucro', '6');