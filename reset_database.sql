-- Script para limpar e recriar o banco de dados
-- Execute este script no PostgreSQL

-- Desconectar todas as conexões ativas
SELECT pg_terminate_backend(pid)
FROM pg_stat_activity
WHERE datname = 'jbfinancial' AND pid <> pg_backend_pid();

-- Dropar e recriar o banco
DROP DATABASE IF EXISTS jbfinancial;
CREATE DATABASE jbfinancial;

