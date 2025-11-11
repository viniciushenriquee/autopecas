## Modelagem inicial do banco de dados ##
 Entidades principais:
 - Produto
 - Cliente
 - Pedido
 - Fornecedor
 - Financeiro

# 🧩 Modelagem Lógica do Banco de Dados — Sistema de Autopeças

## 🎯 Objetivo
Estruturar o modelo de dados do sistema de gestão de uma loja de autopeças, abrangendo vendas, compras, estoque, financeiro e controle de usuários.

---

## 🧱 1️⃣ Entidades Principais

### 🛒 Produto
Armazena as informações sobre as peças disponíveis para venda e estoque.

| Campo | Tipo | Descrição |
|--------|------|-----------|
| id_produto | INT (PK) | Identificador único do produto |
| nome | VARCHAR(100) | Nome da peça |
| descricao | VARCHAR(255) | Detalhes do produto |
| preco | DECIMAL(10,2) | Preço unitário de venda |
| quantidade | INT | Quantidade disponível em estoque |
| id_fornecedor | INT (FK) | Referência ao fornecedor do produto |

---

### 🧾 Fornecedor
Contém os dados dos fornecedores.

| Campo | Tipo | Descrição |
|--------|------|-----------|
| id_fornecedor | INT (PK) | Identificador único |
| nome | VARCHAR(100) | Nome da empresa fornecedora |
| cnpj | VARCHAR(18) | CNPJ do fornecedor |
| telefone | VARCHAR(20) | Telefone de contato |
| email | VARCHAR(100) | E-mail comercial |

---

### 👤 Cliente
Armazena as informações dos clientes da loja.

| Campo | Tipo | Descrição |
|--------|------|-----------|
| id_cliente | INT (PK) | Identificador único |
| nome | VARCHAR(100) | Nome do cliente |
| cpf_cnpj | VARCHAR(18) | CPF ou CNPJ |
| telefone | VARCHAR(20) | Telefone de contato |
| email | VARCHAR(100) | E-mail do cliente |

---

### 📦 Pedido
Registra as vendas realizadas.

| Campo | Tipo | Descrição |
|--------|------|-----------|
| id_pedido | INT (PK) | Identificador único do pedido |
| id_cliente | INT (FK) | Referência ao cliente |
| data_pedido | DATETIME | Data da venda |
| valor_total | DECIMAL(10,2) | Valor total do pedido |
| status | VARCHAR(50) | Ex: “em andamento”, “finalizado”, “cancelado” |

---

### 🧩 Item_Pedido
Relaciona produtos com pedidos de venda.

| Campo | Tipo | Descrição |
|--------|------|-----------|
| id_item | INT (PK) | Identificador único |
| id_pedido | INT (FK) | Pedido relacionado |
| id_produto | INT (FK) | Produto vendido |
| quantidade | INT | Quantidade vendida |
| valor_unitario | DECIMAL(10,2) | Preço do produto na venda |

---

### 💰 Financeiro
Gerencia entradas (vendas) e saídas (compras).

| Campo | Tipo | Descrição |
|--------|------|-----------|
| id_financeiro | INT (PK) | Identificador único |
| tipo | ENUM('entrada','saida') | Tipo de movimentação |
| valor | DECIMAL(10,2) | Valor do movimento |
| data | DATETIME | Data do lançamento |
| id_pedido | INT (FK, opcional) | Relacionado a uma venda |
| id_compra | INT (FK, opcional) | Relacionado a uma compra |

---

### 🧾 Compra
Responsável por registrar pedidos de compra feitos aos fornecedores.

| Campo | Tipo | Descrição |
|--------|------|-----------|
| id_compra | INT (PK) | Identificador único da compra |
| id_fornecedor | INT (FK) | Referência ao fornecedor |
| data_compra | DATETIME | Data da solicitação |
| status | VARCHAR(50) | “pendente”, “em andamento”, “recebido” |
| valor_total | DECIMAL(10,2) | Valor total da compra |
| id_usuario | INT (FK) | Usuário que realizou a compra |

---

### 📦 Item_Compra
Itens que fazem parte de um pedido de compra.

| Campo | Tipo | Descrição |
|--------|------|-----------|
| id_item_compra | INT (PK) | Identificador único |
| id_compra | INT (FK) | Referência à compra |
| id_produto | INT (FK) | Produto comprado |
| quantidade | INT | Quantidade solicitada |
| valor_unitario | DECIMAL(10,2) | Valor negociado com o fornecedor |

---

### 👨‍💼 Usuário
Controle de acesso e permissões no sistema.

| Campo | Tipo | Descrição |
|--------|------|-----------|
| id_usuario | INT (PK) | Identificador único |
| nome | VARCHAR(100) | Nome completo |
| email | VARCHAR(100) | E-mail para login |
| senha | VARCHAR(255) | Senha criptografada |
| cargo | ENUM('admin','vendedor','estoquista','financeiro','compras') | Função do usuário no sistema |

---

## 🔗 2️⃣ Relacionamentos

| Relacionamento | Tipo |
|----------------|------|
| Fornecedor → Produto | 1:N |
| Fornecedor → Compra | 1:N |
| Produto → Item_Pedido | 1:N |
| Produto → Item_Compra | 1:N |
| Cliente → Pedido | 1:N |
| Pedido → Item_Pedido | 1:N |
| Pedido → Financeiro | 1:1 (opcional) |
| Compra → Item_Compra | 1:N |
| Compra → Financeiro | 1:1 (opcional) |
| Usuário → Compra | 1:N |

---

## 🧮 3️⃣ Visão Geral do Fluxo

[Fornecedor]──<Produto>──<Item_Pedido>──[Pedido]──[Cliente]
│ │
│ └──<Financeiro>
│
└──<Compra>──<Item_Compra>──> (Atualiza Estoque)

---

## 📈 4️⃣ Considerações Finais
- O **setor de compras** é responsável por manter o estoque sempre em níveis ideais.  
- O **financeiro** é atualizado automaticamente ao registrar pedidos e compras.  
- O **estoque** será atualizado tanto por **vendas (saídas)** quanto por **compras (entradas)**.  
- O **controle de usuários** permitirá definir permissões e acessos para cada setor do sistema.

---
