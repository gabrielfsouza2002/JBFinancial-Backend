# Guia de Implementação - Categoria de Produto no Frontend

## Objetivo
Mover a aba "Categoria de Produto" para dentro da aba "Produto" como uma subaba, seguindo o mesmo padrão da aba "Clientes" que possui as subabas "Clientes" e "Segmento".

## Estrutura Esperada

### Antes:
```
- Produto
- Categoria de Produto (aba separada)
```

### Depois:
```
- Produto
  ├─ Produto (subaba)
  └─ Categoria (subaba)
```

## Passos para Implementação

### 1. Estrutura de Componentes

Baseado na estrutura de Clientes/Segmento, você deve ter algo assim:

```
/pages ou /components
  /Produto
    ├─ index.tsx (componente principal com as subabas)
    ├─ ProdutoList.tsx (listagem de produtos)
    └─ CategoriaProdutoList.tsx (listagem de categorias)
```

### 2. Componente Principal com Subabas

O arquivo `Produto/index.tsx` deve implementar subabas. Exemplo baseado em como deve estar em Cliente:

```tsx
import React, { useState } from 'react';
import { Tabs, Tab, Box } from '@mui/material'; // ou biblioteca que você usa
import ProdutoList from './ProdutoList';
import CategoriaProdutoList from './CategoriaProdutoList';

export default function ProdutoPage() {
  const [activeTab, setActiveTab] = useState(0);

  const handleTabChange = (event: React.SyntheticEvent, newValue: number) => {
    setActiveTab(newValue);
  };

  return (
    <Box>
      <Tabs value={activeTab} onChange={handleTabChange}>
        <Tab label="Produto" />
        <Tab label="Categoria" />
      </Tabs>
      
      {activeTab === 0 && <ProdutoList />}
      {activeTab === 1 && <CategoriaProdutoList />}
    </Box>
  );
}
```

### 3. Componente de Listagem de Categorias

Crie `CategoriaProdutoList.tsx` seguindo o mesmo padrão de outras listagens:

```tsx
import React, { useState, useEffect } from 'react';
import axios from 'axios';

interface CategoriaProduto {
  id: string;
  nome: string;
  descricao: string;
}

export default function CategoriaProdutoList() {
  const [categorias, setCategorias] = useState<CategoriaProduto[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchCategorias();
  }, []);

  const fetchCategorias = async () => {
    try {
      const response = await axios.get('/categoria-produto/user', {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
      });
      setCategorias(response.data);
    } catch (error) {
      console.error('Erro ao buscar categorias:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id: string) => {
    try {
      await axios.delete(`/categoria-produto/${id}`, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
      });
      // Mostrar mensagem de sucesso
      fetchCategorias(); // Recarregar lista
    } catch (error: any) {
      // Capturar erro do backend
      const errorMessage = error.response?.data?.message || 'Erro ao excluir categoria';
      alert(errorMessage);
    }
  };

  // ... resto do componente com tabela/cards de categorias
}
```

### 4. Atualizar Formulário de Produto

No formulário de criação/edição de produto, adicione um campo para selecionar a categoria:

```tsx
import React, { useState, useEffect } from 'react';
import { Select, MenuItem, FormControl, InputLabel } from '@mui/material';

export default function ProdutoForm() {
  const [categorias, setCategorias] = useState([]);
  const [formData, setFormData] = useState({
    codigo: '',
    nome_produto: '',
    descricao: '',
    categoriaId: '' // Novo campo
  });

  useEffect(() => {
    // Buscar categorias para o select
    fetchCategorias();
  }, []);

  const fetchCategorias = async () => {
    const response = await axios.get('/categoria-produto/user', {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    });
    setCategorias(response.data);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('/produto', formData, {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
      });
      // Sucesso
    } catch (error: any) {
      const errorMessage = error.response?.data?.message || 'Erro ao salvar produto';
      alert(errorMessage);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      {/* ... outros campos ... */}
      
      <FormControl fullWidth>
        <InputLabel>Categoria</InputLabel>
        <Select
          value={formData.categoriaId}
          onChange={(e) => setFormData({...formData, categoriaId: e.target.value})}
        >
          <MenuItem value="">Nenhuma</MenuItem>
          {categorias.map((cat) => (
            <MenuItem key={cat.id} value={cat.id}>
              {cat.nome}
            </MenuItem>
          ))}
        </Select>
      </FormControl>
      
      {/* ... botão submit ... */}
    </form>
  );
}
```

### 5. Padronização dos Botões Import/Export

Certifique-se de que os botões de Import e Export sigam o mesmo padrão das outras abas. Exemplo:

```tsx
// Localizar onde estão os botões de Import/Export em outras abas (ex: Cliente)
// Copiar o estilo/estrutura exata para a aba de Produto

// Exemplo típico:
<Box display="flex" gap={2} mb={2}>
  <Button
    variant="contained"
    startIcon={<UploadIcon />}
    onClick={handleImport}
  >
    Importar
  </Button>
  
  <Button
    variant="outlined"
    startIcon={<DownloadIcon />}
    onClick={handleExport}
  >
    Exportar
  </Button>
</Box>
```

### 6. Atualizar Roteamento

Se você usa React Router, atualize as rotas:

```tsx
// Antes
<Route path="/produto" element={<ProdutoList />} />
<Route path="/categoria-produto" element={<CategoriaProdutoList />} />

// Depois (remover a rota separada de categoria)
<Route path="/produto" element={<ProdutoPage />} />
```

### 7. Atualizar Menu de Navegação

Remova o item "Categoria de Produto" do menu principal, pois agora é uma subaba.

## Endpoints do Backend Disponíveis

### Categoria de Produto
- `GET /categoria-produto/user` - Listar categorias do usuário
- `POST /categoria-produto` - Criar categoria
- `PUT /categoria-produto/{id}` - Atualizar categoria
- `DELETE /categoria-produto/{id}` - Deletar categoria

### Produto (atualizado)
- `GET /produto/user` - Listar produtos (agora retorna `categoriaId` e `categoriaNome`)
- `POST /produto` - Criar produto (aceita `categoriaId`)
- `PUT /produto/{id}` - Atualizar produto (aceita `categoriaId`)
- `DELETE /produto/{id}` - Deletar produto

## Formato dos Dados

### CategoriaProduto Response:
```json
{
  "id": "uuid",
  "userId": "string",
  "nome": "ELETRÔNICOS",
  "descricao": "Produtos eletrônicos e tecnologia"
}
```

### Produto Response (atualizado):
```json
{
  "id": "uuid",
  "userId": "string",
  "codigo": "PROD001",
  "nome_produto": "NOTEBOOK",
  "descricao": "Notebook Dell",
  "categoria": "ELETRÔNICOS", // campo antigo (ainda existe)
  "categoriaId": "uuid", // novo campo
  "categoriaNome": "ELETRÔNICOS" // novo campo
}
```

## Checklist de Implementação

- [ ] Criar componente `CategoriaProdutoList.tsx`
- [ ] Modificar `Produto/index.tsx` para incluir sistema de abas
- [ ] Mover conteúdo atual de produtos para `ProdutoList.tsx`
- [ ] Adicionar select de categoria no formulário de produto
- [ ] Padronizar botões Import/Export com outras abas
- [ ] Atualizar rotas (remover rota separada de categoria)
- [ ] Atualizar menu de navegação
- [ ] Testar criação de categoria
- [ ] Testar vinculação de produto com categoria
- [ ] Testar exclusão de categoria com produto vinculado (deve mostrar erro)

## Tratamento de Erros

O backend já retorna mensagens apropriadas:
- Ao tentar deletar categoria em uso: "A categoria não pode ser excluída pois há uso em produtos. Se desejar, é possível atualizar os dados."
- Certifique-se de capturar `error.response.data.message` e exibir ao usuário

## Dúvidas?

Se precisar de mais detalhes sobre algum passo específico ou tiver dúvidas sobre a implementação, consulte como está implementado na aba de Clientes/Segmento e replique o padrão.

