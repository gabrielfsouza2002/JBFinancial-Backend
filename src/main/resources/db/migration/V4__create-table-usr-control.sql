CREATE TABLE usr_control (
     usr_ID UUID REFERENCES usuario(usr_ID),
     receita NUMERIC(10,2) NOT NULL DEFAULT 0.00,
     custo NUMERIC(10,2) NOT NULL DEFAULT 0.00,
     saldo NUMERIC(10,2) NOT NULL DEFAULT 0.00,
     margem NUMERIC(5,2) NOT NULL DEFAULT 0.00,
     periodo_referencia DATE NOT NULL,
     PRIMARY KEY (usr_ID, periodo_referencia)
);