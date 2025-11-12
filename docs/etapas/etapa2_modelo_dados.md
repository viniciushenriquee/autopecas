# 🧱 Etapa 2 – Modelo de Dados

Nesta etapa, definimos as tabelas principais do banco de dados que dará suporte ao sistema de autopeças.

---

### 👤 Cliente

| Campo | Tipo | Descrição |
|--------|------|------------|
| id_cliente | INT (PK) | Identificador único |
| nome | VARCHAR(100) | Nome completo ou razão social |
| tipo_cliente | ENUM('Física', 'Jurídica') | Define se o cliente é pessoa física ou jurídica |
| cpf_cnpj | VARCHAR(18) | CPF ou CNPJ |
| telefone | VARCHAR(20) | Contato |
| email | VARCHAR(100) | E-mail |
| endereco | VARCHAR(200) | Endereço completo |
| data_cadastro | DATE | Data de cadastro do cliente |

---

### 🚚 Fornecedor

| Campo | Tipo | Descrição |
|--------|------|------------|
| id_fornecedor | INT (PK) | Identificador único |
| nome | VARCHAR(100) | Nome fantasia ou razão social |
| cnpj | VARCHAR(18) | Cadastro Nacional de Pessoa Jurídica |
| inscricao_estadual | VARCHAR(20) | Inscrição estadual |
| telefone | VARCHAR(20) | Contato |
| email | VARCHAR(100) | E-mail |
| endereco | VARCHAR(200) | Endereço completo |
| data_cadastro | DATE | Data de cadastro do fornecedor |

---

### ⚙️ Produto

| Campo | Tipo | Descrição |
|--------|------|------------|
| id_produto | INT (PK) | Identificador único |
| nome | VARCHAR(100) | Nome do produto |
| descricao | VARCHAR(255) | Descrição detalhada |
| categoria | VARCHAR(50) | Categoria ou tipo |
| preco_custo | DECIMAL(10,2) | Custo de compra |
| preco_venda | DECIMAL(10,2) | Preço de venda |
| estoque_atual | INT | Quantidade disponível |
| id_fornecedor | INT (FK) | Fornecedor principal |
| data_cadastro | DATE | Data de inclusão do produto |

---

### 📦 Pedido

| Campo | Tipo | Descrição |
|--------|------|------------|
| id_pedido | INT (PK) | Identificador único |
| id_cliente | INT (FK) | Cliente associado |
| id_vendedor | INT (FK) | Vendedor responsável |
| data_pedido | DATE | Data da compra |
| status | ENUM('Aberto', 'Pago', 'Cancelado') | Situação atual |
| valor_total | DECIMAL(10,2) | Valor total da venda |

---

### 🧾 Itens do Pedido

| Campo | Tipo | Descrição |
|--------|------|------------|
| id_item | INT (PK) | Identificador único |
| id_pedido | INT (FK) | Pedido associado |
| id_produto | INT (FK) | Produto vendido |
| quantidade | INT | Quantidade vendida |
| preco_unitario | DECIMAL(10,2) | Preço unitário do item |
| subtotal | DECIMAL(10,2) | Total do item (quantidade × preço) |

---

### 🏢 Usuário

| Campo | Tipo | Descrição |
|--------|------|------------|
| id_usuario | INT (PK) | Identificador único |
| nome | VARCHAR(100) | Nome do usuário |
| email | VARCHAR(100) | E-mail de login |
| senha | VARCHAR(100) | Senha criptografada |
| cargo | ENUM('Administrador', 'Vendedor', 'Estoquista', 'Comprador', 'Financeiro') | Tipo de acesso |
| data_cadastro | DATE | Data de criação |

---

### 💰 Financeiro

| Campo | Tipo | Descrição |
|--------|------|------------|
| id_financeiro | INT (PK) | Identificador único |
| tipo | ENUM('Receita', 'Despesa') | Tipo de movimentação |
| descricao | VARCHAR(255) | Descrição do lançamento |
| valor | DECIMAL(10,2) | Valor da transação |
| data_movimento | DATE | Data do lançamento |
| id_pedido | INT (FK, opcional) | Pedido relacionado (se aplicável) |

---

### 📋 Compras

| Campo | Tipo | Descrição |
|--------|------|------------|
| id_compra | INT (PK) | Identificador único |
| id_fornecedor | INT (FK) | Fornecedor da compra |
| id_comprador | INT (FK) | Usuário responsável pela compra |
| data_compra | DATE | Data da compra |
| valor_total | DECIMAL(10,2) | Valor total da compra |
| status | ENUM('Aberta', 'Recebida', 'Cancelada') | Situação da compra |

---

### 🧱 Resumo das Relações

- **Cliente** 1:N **Pedido**  
- **Pedido** 1:N **ItensPedido**  
- **Produto** 1:N **ItensPedido**  
- **Fornecedor** 1:N **Produto**  
- **Compra** 1:N **Produto** (entrada no estoque)  
- **Usuário** 1:N **Pedido** (vendedor)  
- **Usuário** 1:N **Compra** (comprador)  
- **Pedido** 1:1 **Financeiro** (venda registrada)
