# sistema_estoque_caixa


# 🛒 Sistema de Gestão de Estoque e Caixa

Este projeto é um sistema web moderno desenvolvido com **Angular** (Front-end) e **Spring Boot** (Back-end), seguindo uma arquitetura cliente-servidor baseada em serviços REST e controle de acesso por papéis de usuário (Roles).

## 🚀 Funcionalidades de Gestão (Perfil ADMIN)

O módulo administrativo implementado nesta fase permite o controle central de usuários, produtos e estoque.

### 1. Autenticação e Controle de Acesso

* **Login Simples:** Tela de autenticação usando e-mail e senha.
* **Segurança de Senha:** A senha é comparada e validada usando **BCrypt** no Back-end.
* **Sessão e Perfil:** Após o login, o sistema armazena o nome e o perfil do usuário (`ADMIN` ou `OPERADOR`) no Front-end (`localStorage`).
* **Guarda de Rotas (Guards):** Aplicação de rotas protegidas que exigem a autenticação e a **Role (perfil)** específica do usuário para acesso.
* **Layout Adaptativo:** O cabeçalho da aplicação exibe o nome e o perfil do usuário logado e oferece a opção de **Logout**.

### 2. Gestão de Usuários (CRUD)

Módulo completo de Cadastro, Listagem, Edição e Exclusão de usuários, acessível **somente** pelo perfil `ADMIN`.

* **Campos:** Nome completo, E-mail (único), Senha (criptografada), Perfil (`ADMIN` ou `OPERADOR`) e Status (ativo/inativo).
* **Validações:** O Back-end garante a unicidade do e-mail e aplica uma política de **senha forte** (mínimo 8 caracteres, 1 maiúscula, 1 número).
* **Interface:** Utiliza **Reactive Forms** no Angular para entrada de dados e validações em tempo real.

### 3. Gestão de Produtos e Estoque

Módulo essencial para o controle físico dos itens, acessível **somente** pelo perfil `ADMIN`.

* **Cadastro de Produtos:** CRUD completo para criação e manutenção de produtos (código, nome, categoria, preço unitário).
* **Controle de Saldo:** Exibição do saldo atual de cada produto.
* **Movimentação de Estoque:** Funcionalidade para registrar:
    * **ENTRADA:** Reposição de estoque (aumenta o saldo).
    * **AJUSTE:** Correção de saldo (pode ser positivo ou negativo, com validação para não deixar o saldo abaixo de zero).
* **Histórico:** Cada movimentação é registrada com data, tipo e motivo.

## 🛠️ Detalhes Técnicos

* **Back-end:** **Java** com **Spring Boot** (API REST).
* **Persistência:** **Spring Data JPA** e **H2 Database** (para desenvolvimento).
* **Front-end:** **Angular** (Componentes Autônomos) com **TypeScript**.
* **Interface:** Biblioteca **PrimeNG** para componentes e layout responsivo.
