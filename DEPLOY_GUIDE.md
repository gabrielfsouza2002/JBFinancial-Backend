# Guia de Deploy no Render - JBFinancial Backend

## Problema Identificado
O erro "Network is unreachable" ocorre porque o Render não consegue acessar o banco de dados do Supabase. Isso pode acontecer por:
1. Variáveis de ambiente não configuradas no Render
2. Configurações de rede do Supabase bloqueando conexões externas

## Solução: Configurar Variáveis de Ambiente no Render

### Passo 1: Acessar o Dashboard do Render
1. Acesse https://dashboard.render.com
2. Selecione seu serviço "jbfinancial-backend"
3. Clique em "Environment" no menu lateral

### Passo 2: Adicionar Variáveis de Ambiente
Adicione as seguintes variáveis:

**DATABASE_URL**
```
jdbc:postgresql://db.cjuhbbwfitdmpjlqlmvo.supabase.co:5432/postgres
```

**DATABASE_USERNAME**
```
postgres
```

**DATABASE_PASSWORD**
```
paraquedas123
```

**JWT_SECRET**
```
my-secret-key-change-in-production
```

### Passo 3: Verificar Configurações do Supabase

1. Acesse o painel do Supabase: https://app.supabase.com
2. Selecione seu projeto
3. Vá em "Project Settings" > "Database"
4. Role até "Connection Pooling"
5. **IMPORTANTE**: Use a connection string do **Connection Pooling** (não a direta)
6. A URL deve ser algo como:
```
jdbc:postgresql://aws-0-us-east-1.pooler.supabase.com:6543/postgres
```

### Passo 4: Atualizar a variável DATABASE_URL no Render

Se o Supabase estiver usando Connection Pooling, atualize a variável `DATABASE_URL` no Render para:
```
jdbc:postgresql://aws-0-us-east-1.pooler.supabase.com:6543/postgres
```
(Substitua pela URL correta do seu projeto)

### Passo 5: Configurar o Supabase para aceitar conexões externas

1. No painel do Supabase, vá em "Project Settings" > "Database"
2. Em "Connection Info", certifique-se de que a opção "Use connection pooling" está habilitada
3. Copie a connection string de "Connection pooling" (modo Transaction)

### Passo 6: Redesploy

Após configurar as variáveis:
1. No Render, clique em "Manual Deploy" > "Deploy latest commit"
2. Aguarde o build e deploy
3. Verifique os logs

## Checklist de Troubleshooting

- [ ] Variáveis de ambiente configuradas no Render
- [ ] Connection string do Supabase está correta (usar pooler, não direto)
- [ ] Porta correta (6543 para pooling, 5432 para direta)
- [ ] Supabase permite conexões externas
- [ ] Senha está correta
- [ ] Timezone do banco está configurado (UTC recomendado)

## Alternativa: Usar banco de dados do Render

Se continuar com problemas, você pode criar um banco PostgreSQL diretamente no Render:

1. No Render Dashboard, clique em "New +" > "PostgreSQL"
2. Dê um nome ao banco
3. Aguarde a criação
4. No serviço do backend, vá em "Environment"
5. Use a variável `DATABASE_URL` que o Render cria automaticamente
6. Remova as variáveis DATABASE_USERNAME e DATABASE_PASSWORD

## Logs Úteis

Para ver os logs de erro:
```bash
# No painel do Render, vá em "Logs"
# Procure por erros de conexão ou Flyway
```

## Contato

Se o erro persistir, verifique:
- Os logs completos do deploy no Render
- As configurações de firewall/network no Supabase
- Se a senha contém caracteres especiais que precisam ser escapados

