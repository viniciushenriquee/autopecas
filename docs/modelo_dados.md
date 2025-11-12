# 🧩 Modelagem Lógica do Banco de Dados — Sistema de Autopeças

## 🎯 Objetivo
Estruturar o modelo de dados do sistema de gestão de uma loja de autopeças, abrangendo **vendas, compras, estoque, financeiro e controle de usuários**.

---

## 🧱 Entidades Principais
- Produto  
- Cliente  
- Pedido  
- Fornecedor  
- Financeiro  
- Compras  
- Usuário  

---

## 🧩 Produto

Armazena as informações sobre as peças disponíveis para venda e estoque.

| Campo | Tipo | Descrição |
|--------|------|------------|
| id_produto | INT (PK) | Identificador único |
| nome | VARCHAR(100) | Nome da peça |
| descricao | TEXT | Descrição detalhada |
| preco | DECIMAL(10,2) | Preço de venda |
| quantidade_estoque | INT | Quantidade atual |
| id_fornecedor | INT (FK) | Relacionado ao fornecedor principal |

---

## 👤 Cliente

Contém os dados dos clientes que realizam compras na loja.

| Campo | Tipo | Descrição |
|--------|------|------------|
| id_cliente | INT (PK) | Identificador único |
| nome | VARCHAR(100) | Nome completo ou razão social |
| tipo_cliente | ENUM('Física', 'Jurídica') | Define se o cliente é pessoa física ou jurídica |
| cpf_cnpj | VARCHAR(18) | CPF ou CNPJ, conforme o tipo do cliente |
| telefone | VARCHAR(20) | Contato |
| email | VARCHAR(100) | E-mail |
| endereco | VARCHAR(200) | Endereço completo |
| data_cadastro | DATE | Data de cadastro do cliente |

---

## 🧾 Pedido

Representa as vendas realizadas pela loja.

| Campo | Tipo | Descrição |
|--------|------|------------|
| id_pedido | INT (PK) | Identificador único |
| id_cliente | INT (FK) | Cliente que realizou o pedido |
| id_usuario | INT (FK) | Vendedor responsável |
| data_pedido | DATE | Data do pedido |
| status | VARCHAR(20) | Situação (Em aberto, Pago, Cancelado) |
| total | DECIMAL(10,2) | Valor total do pedido |

### Itens do Pedido

| Campo | Tipo | Descrição |
|--------|------|------------|
| id_item_pedido | INT (PK) | Identificador do item |
| id_pedido | INT (FK) | Referência ao pedido |
| id_produto | INT (FK) | Produto vendido |
| quantidade | INT | Quantidade vendida |
| preco_unitario | DECIMAL(10,2) | Preço unitário |
| subtotal | DECIMAL(10,2) | Valor do item |

---

## 🚚 Fornecedor

Armazena as informações dos fornecedores que vendem peças para a loja.

| Campo | Tipo | Descrição |
|--------|------|------------|
| id_fornecedor | INT (PK) | Identificador único |
| nome | VARCHAR(100) | Nome fantasia ou razão social |
| cnpj | VARCHAR(18) | Cadastro Nacional de Pessoa Jurídica |
| inscricao_estadual | VARCHAR(20) | Número de inscrição estadual |
| telefone | VARCHAR(20) | Contato |
| email | VARCHAR(100) | E-mail |
| endereco | VARCHAR(200) | Endereço completo |
| data_cadastro | DATE | Data de cadastro do fornecedor |

---

## 💰 Financeiro

Gerencia o controle de contas a pagar e receber, vinculando movimentações a pedidos e compras.

| Campo | Tipo | Descrição |
|--------|------|------------|
| id_movimento | INT (PK) | Identificador da movimentação |
| tipo | VARCHAR(20) | Tipo (Entrada/Saída) |
| descricao | TEXT | Descrição do movimento |
| valor | DECIMAL(10,2) | Valor da movimentação |
| data | DATE | Data da movimentação |
| id_pedido | INT (FK) | Relacionado ao pedido (caso de venda) |
| id_compra | INT (FK) | Relacionado à compra (caso de despesa) |

---

## 🧾 Compras

Controla o processo de compra com fornecedores, incluindo solicitações e relatórios de giro.

| Campo | Tipo | Descrição |
|--------|------|------------|
| id_compra | INT (PK) | Identificador único da compra |
| data_solicitacao | DATE | Data em que a compra foi criada |
| status | VARCHAR(20) | Situação (Pendente, Enviada, Recebida, Cancelada) |
| id_fornecedor | INT (FK) | Fornecedor selecionado |
| id_usuario | INT (FK) | Usuário responsável |

### Itens da Compra

| Campo | Tipo | Descrição |
|--------|------|------------|
| id_item_compra | INT (PK) | Identificador do item |
| id_compra | INT (FK) | Referência da compra |
| id_produto | INT (FK) | Produto solicitado |
| quantidade | INT | Quantidade solicitada |
| valor_unitario | DECIMAL(10,2) | Valor unitário negociado |
| total | DECIMAL(10,2) | Valor total do item |

### Relatório de Giro

| Campo | Tipo | Descrição |
|--------|------|------------|
| id_relatorio | INT (PK) | Identificador do relatório |
| periodo | VARCHAR(20) | Período analisado |
| id_produto | INT (FK) | Produto analisado |
| quantidade_vendida | INT | Quantidade vendida |
| quantidade_comprada | INT | Quantidade comprada |
| saldo_atual | INT | Estoque disponível |
| indice_giro | DECIMAL(10,2) | Índice de giro (vendas / estoque médio) |

---

## 👥 Usuário

Tabela para autenticação e controle de acesso ao sistema.

| Campo | Tipo | Descrição |
|--------|------|------------|
| id_usuario | INT (PK) | Identificador único |
| nome | VARCHAR(100) | Nome completo |
| email | VARCHAR(100) | E-mail de login |
| senha_hash | VARCHAR(255) | Senha criptografada |
| setor | VARCHAR(30) | Setor do usuário (Admin, Vendas, Estoque, Compras, Financeiro) |

---

## 🔗 Relacionamentos Principais

- **Produto ↔ Fornecedor** → Muitos para um  
- **Pedido ↔ Cliente** → Muitos para um  
- **Pedido ↔ ItensPedido** → Um para muitos  
- **Compra ↔ Fornecedor** → Muitos para um  
- **Compra ↔ ItensCompra** → Um para muitos  
- **Financeiro ↔ Pedido/Compra** → Opcional (movimentação pode vir de ambos)

---

## 🧠 Próximos Passos (Etapa 3)

1. Criar o **Diagrama ER (Entidade-Relacionamento)** com as relações definidas acima.  
2. Escolher o **banco de dados relacional** (MySQL recomendado).  
3. Criar os **scripts SQL de criação** das tabelas e chaves estrangeiras.  
4. Iniciar a **estrutura do back-end com Java (Spring Boot)** e conectar ao banco.  
5. Desenvolver **endpoints REST** para os principais módulos.  
6. Depois, evoluir para o **front-end (React)** consumindo a API.

---