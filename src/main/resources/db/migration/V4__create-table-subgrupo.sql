CREATE TABLE subgrupo (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_user TEXT,
    nome TEXT NOT NULL,
    id_grupo UUID NOT NULL,
    digito_subgrupo CHAR(1) NOT NULL,
    CONSTRAINT fk_grupo FOREIGN KEY (id_grupo) REFERENCES grupo(id),
    CONSTRAINT fk_user FOREIGN KEY (id_user) REFERENCES users(id)
);


INSERT INTO subgrupo (id, id_user, nome, id_grupo, digito_subgrupo) VALUES
(uuid_generate_v4(), NULL, 'Vendas Gerais', '3fec2d51-fb31-4bf5-9ab7-9c4cd8542c39', '1'),
(uuid_generate_v4(), NULL, 'Impostos', '60dff836-0096-4135-82c6-3d6f3886ad4d', '1'),
(uuid_generate_v4(), NULL, 'Devoluções', '60dff836-0096-4135-82c6-3d6f3886ad4d', '2'),
(uuid_generate_v4(), NULL, 'Produtos Gerais', '0d8364ef-b7c2-46f0-ab49-3b812dd47449', '1'),
(uuid_generate_v4(), NULL, 'Folha de Pagamento', '3d6b0d31-7cb5-42aa-ade4-074e2c161f22', '1'),
(uuid_generate_v4(), NULL, 'Propriedade', '3d6b0d31-7cb5-42aa-ade4-074e2c161f22', '2'),
(uuid_generate_v4(), NULL, 'Tecnologia', '3d6b0d31-7cb5-42aa-ade4-074e2c161f22', '3'),
(uuid_generate_v4(), NULL, 'Marketing', '3d6b0d31-7cb5-42aa-ade4-074e2c161f22', '4'),
(uuid_generate_v4(), NULL, 'Finanças e Jurídico', '3d6b0d31-7cb5-42aa-ade4-074e2c161f22', '5'),
(uuid_generate_v4(), NULL, 'Outras Despesas', '3d6b0d31-7cb5-42aa-ade4-074e2c161f22', '6'),
(uuid_generate_v4(), NULL, 'Juros Sobre Aplicação', '44fc2c54-2ed2-46d7-a918-ef2e14982f66', '1'),
(uuid_generate_v4(), NULL, 'Outras Taxas', '44fc2c54-2ed2-46d7-a918-ef2e14982f66', '2'),
(uuid_generate_v4(), NULL, 'Desconto Obtidos', '44fc2c54-2ed2-46d7-a918-ef2e14982f66', '3'),
(uuid_generate_v4(), NULL, 'IR', '376e7904-87db-4c2d-9051-0357763961c3', '1'),
(uuid_generate_v4(), NULL, 'CSLL', '376e7904-87db-4c2d-9051-0357763961c3', '2');