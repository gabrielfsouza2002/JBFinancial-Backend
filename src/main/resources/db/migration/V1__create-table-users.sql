-- V1__create-uuid-extension.sql
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users (
   id TEXT PRIMARY KEY UNIQUE NOT NULL,
   login TEXT NOT NULL UNIQUE,
   password TEXT NOT NULL,
   email TEXT NOT NULL,
   cnpj TEXT NOT NULL,
   name TEXT UNIQUE NOT NULL,
   role TEXT NOT NULL,
   saldo_inicial FLOAT DEFAULT 0.0
);