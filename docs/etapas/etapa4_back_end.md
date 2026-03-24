# ⚙️ Etapa 4 – Desenvolvimento do Back-end

## 🎯 Objetivo
Construir a API REST utilizando Spring Boot, JPA e MySQL para gerenciar as operações do sistema de autopeças.

---

## 🛠 Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 3.2.4**
- **Spring Data JPA** (Persistência)
- **Flyway** (Migração de Banco de Dados)
- **MySQL 8.0** (Banco de Dados)
- **Lombok** (Produtividade)
- **Docker** (Infraestrutura)

---

## 📋 Plano de Execução

1.  **Configuração de Ambiente**:
    - [x] Subir containers Docker (MySQL e phpMyAdmin).
    - [ ] Validar conexão no `application.properties`.
2.  **Criação das Entidades (JPA)**:
    - [x] `Usuario`
    - [x] `Cliente`
    - [x] `Fornecedor`
    - [x] `Produto`
    - [x] `Pedido` e `ItemPedido`
    - [x] `Compra` e `ItemCompra`
    - [x] `Financeiro`
3.  **Repositórios (Spring Data JPA)**:
    - [x] Criar Interfaces `Repository` para todas as entidades.
4.  **Serviços (Business Logic)**:
    - [x] Implementar CRUD básico para `Usuario`, `Cliente`, `Fornecedor` e `Produto`.
    - [ ] Implementar regras de negócio para `Pedido`, `Compra` e `Financeiro`.
5.  **Controladores (REST API)**:
    - [x] Expor endpoints CRUD para `Usuario`, `Cliente`, `Fornecedor` e `Produto`.
    - [ ] Expor endpoints para `Pedido`, `Compra` e `Financeiro`.
6.  **Testes e Validação**:
    - [ ] Validar endpoints via Postman/Insomnia.
