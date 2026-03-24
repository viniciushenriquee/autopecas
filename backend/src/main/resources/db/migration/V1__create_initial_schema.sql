-- Script de criação inicial para o banco de dados autopecas

-- Tabela de Usuários
CREATE TABLE usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    cargo ENUM('Administrador', 'Vendedor', 'Estoquista', 'Comprador', 'Financeiro') NOT NULL,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Clientes
CREATE TABLE cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    tipo_cliente ENUM('Fisica', 'Juridica') NOT NULL,
    cpf_cnpj VARCHAR(18) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    email VARCHAR(100),
    endereco VARCHAR(200),
    data_cadastro DATE DEFAULT (CURRENT_DATE)
);

-- Tabela de Fornecedores
CREATE TABLE fornecedor (
    id_fornecedor INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cnpj VARCHAR(18) NOT NULL UNIQUE,
    inscricao_estadual VARCHAR(20),
    telefone VARCHAR(20),
    email VARCHAR(100),
    endereco VARCHAR(200),
    data_cadastro DATE DEFAULT (CURRENT_DATE)
);

-- Tabela de Produtos
CREATE TABLE produto (
    id_produto INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    categoria VARCHAR(50),
    preco_custo DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    preco_venda DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    estoque_atual INT NOT NULL DEFAULT 0,
    id_fornecedor INT,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_produto_fornecedor FOREIGN KEY (id_fornecedor) REFERENCES fornecedor(id_fornecedor)
);

-- Tabela de Pedidos (Vendas)
CREATE TABLE pedido (
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT,
    id_vendedor INT,
    data_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('Aberto', 'Pago', 'Cancelado') DEFAULT 'Aberto',
    valor_total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    CONSTRAINT fk_pedido_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
    CONSTRAINT fk_pedido_vendedor FOREIGN KEY (id_vendedor) REFERENCES usuario(id_usuario)
);

-- Itens do Pedido
CREATE TABLE itens_pedido (
    id_item INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT,
    id_produto INT,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_item_pedido FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido),
    CONSTRAINT fk_item_produto FOREIGN KEY (id_produto) REFERENCES produto(id_produto)
);

-- Tabela de Compras (Entrada de Estoque)
CREATE TABLE compra (
    id_compra INT AUTO_INCREMENT PRIMARY KEY,
    id_fornecedor INT,
    id_comprador INT,
    data_compra TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    valor_total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    status ENUM('Aberta', 'Recebida', 'Cancelada') DEFAULT 'Aberta',
    CONSTRAINT fk_compra_fornecedor FOREIGN KEY (id_fornecedor) REFERENCES fornecedor(id_fornecedor),
    CONSTRAINT fk_compra_comprador FOREIGN KEY (id_comprador) REFERENCES usuario(id_usuario)
);

-- Itens da Compra
CREATE TABLE itens_compra (
    id_item_compra INT AUTO_INCREMENT PRIMARY KEY,
    id_compra INT,
    id_produto INT,
    quantidade INT NOT NULL,
    valor_unitario DECIMAL(10,2) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_item_compra_ref FOREIGN KEY (id_compra) REFERENCES compra(id_compra),
    CONSTRAINT fk_item_compra_produto FOREIGN KEY (id_produto) REFERENCES produto(id_produto)
);

-- Controle Financeiro
CREATE TABLE financeiro (
    id_financeiro INT AUTO_INCREMENT PRIMARY KEY,
    tipo ENUM('Receita', 'Despesa') NOT NULL,
    descricao VARCHAR(255),
    valor DECIMAL(10,2) NOT NULL,
    data_movimento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_pedido INT NULL,
    id_compra INT NULL,
    CONSTRAINT fk_financeiro_pedido FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido),
    CONSTRAINT fk_financeiro_compra FOREIGN KEY (id_compra) REFERENCES compra(id_compra)
);
