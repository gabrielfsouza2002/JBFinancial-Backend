CREATE TABLE users (
   id TEXT PRIMARY KEY UNIQUE NOT NULL,
   login TEXT NOT NULL UNIQUE,
   password TEXT NOT NULL,
   email TEXT NOT NULL,
   cnpj TEXT NOT NULL,
   name TEXT NOT NULL,
   role TEXT NOT NULL,
   saldo_inicial FLOAT DEFAULT 0.0
);