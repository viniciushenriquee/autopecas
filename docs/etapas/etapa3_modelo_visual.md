# 🧱 Etapa 3 – Modelo Entidade-Relacionamento (DER)

## 🎯 Objetivo
Representar graficamente as entidades e os relacionamentos do sistema de autopeças, com base nas tabelas definidas na Etapa 2.

---

## 🗃 Modelo em linguagem DBML 

```dbml
Table cliente {
  id_cliente int [pk]
  nome varchar(100)
  tipo_cliente varchar(10)
  cpf_cnpj varchar(18)
  telefone varchar(20)
  email varchar(100)
  endereco varchar(200)
  data_cadastro date
}

Table fornecedor {
  id_fornecedor int [pk]
  nome varchar(100)
  cnpj varchar(18)
  inscricao_estadual varchar(20)
  telefone varchar(20)
  email varchar(100)
  endereco varchar(200)
  data_cadastro date
}

Table produto {
  id_produto int [pk]
  nome varchar(100)
  descricao varchar(255)
  categoria varchar(50)
  preco_custo decimal(10,2)
  preco_venda decimal(10,2)
  estoque_atual int
  id_fornecedor int [ref: > fornecedor.id_fornecedor]
  data_cadastro date
}

Table pedido {
  id_pedido int [pk]
  id_cliente int [ref: > cliente.id_cliente]
  id_vendedor int [ref: > usuario.id_usuario]
  data_pedido date
  status varchar(20)
  valor_total decimal(10,2)
}

Table itens_pedido {
  id_item int [pk]
  id_pedido int [ref: > pedido.id_pedido]
  id_produto int [ref: > produto.id_produto]
  quantidade int
  preco_unitario decimal(10,2)
  subtotal decimal(10,2)
}

Table usuario {
  id_usuario int [pk]
  nome varchar(100)
  email varchar(100)
  senha varchar(100)
  cargo varchar(20)
  data_cadastro date
}

Table financeiro {
  id_financeiro int [pk]
  tipo varchar(20)
  descricao varchar(255)
  valor decimal(10,2)
  data_movimento date
  id_pedido int [ref: > pedido.id_pedido, note: 'Opcional']
}

Table compra {
  id_compra int [pk]
  id_fornecedor int [ref: > fornecedor.id_fornecedor]
  id_comprador int [ref: > usuario.id_usuario]
  data_compra date
  valor_total decimal(10,2)
  status varchar(20)
}
