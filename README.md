
# **Gerenciamento de Pedidos e Produtos**

Este projeto é uma aplicação de gerenciamento de **pedidos** e **produtos** desenvolvida com **Java 21** e **Spring Boot 4**. A aplicação oferece funcionalidades para criar, listar, atualizar e deletar produtos, bem como criar e consultar pedidos, com persistência em um banco de dados **MySQL**. Além disso, conta com **testes unitários** utilizando **JUnit 5** e **testes automatizados** com **Cucumber** para garantir a qualidade do sistema.

## **Tecnologias Utilizadas**

- **Java 21**: Linguagem de programação usada para o desenvolvimento.
- **Spring Boot 4**: Framework para facilitar a criação de aplicações Java baseadas em Spring.
- **MySQL**: Banco de dados relacional utilizado para persistência de dados.
- **JUnit 5**: Framework de testes unitários utilizado para validar a lógica da aplicação.
- **Cucumber**: Framework para testes automatizados que utiliza a metodologia BDD (Behavior Driven Development) para validar os requisitos funcionais da aplicação.
- **Maven**: Ferramenta de gerenciamento de dependências e construção do projeto.

## **Funcionalidades**

### **Gerenciamento de Produtos**

- **Cadastrar Produto**: Permite o cadastro de um novo produto com nome, preço e quantidade.
- **Listar Produtos**: Exibe uma lista paginada de todos os produtos cadastrados.
- **Buscar Produto por ID**: Realiza a consulta de um produto específico utilizando seu identificador único (UUID).
- **Atualizar Produto**: Altera os detalhes de um produto existente.
- **Excluir Produto**: Permite a remoção de um produto do sistema.

### **Gerenciamento de Pedidos**

- **Criar Pedido**: Permite a criação de um novo pedido, associando produtos a ele.
- **Listar Pedidos**: Exibe todos os pedidos realizados.
- **Buscar Pedido por ID**: Realiza a consulta de um pedido específico utilizando seu identificador único (UUID).
- **Atualizar Status do Pedido**: Atualiza o status de um pedido (ex: de "Pendente" para "Em Processamento").

## **Estrutura do Projeto**

A estrutura do projeto está organizada da seguinte maneira:

```
src/
├── main/
│   ├── java/
│   │   ├── com/
│   │   │   └── dealership/
│   │   │       └── janczakcars/
│   │   │           ├── adapter/
│   │   │           │   ├── input/
│   │   │           │   │   ├── order/
│   │   │           │   │   │   └── dto/
│   │   │           │   │   │   └── OrderControllerImpl.java
│   │   │           │   │   └── product/
│   │   │           │   │       └── dto/
│   │   │           │   │       └── ProductControllerImpl.java
│   │   │           ├── output/
│   │   │           │   └── models/
│   │   │           ├── domain/
│   │   │           │   ├── enums/
│   │   │           │   ├── mappers/
│   │   │           │   ├── port/
│   │   │           │   └── services/
│   │   │           └── port/
│   │   │               ├── input/
│   │   │               │   ├── order/
│   │   │               │   │   └── OrderControllerPort.java
│   │   │               │   └── product/
│   │   │               │       └── ProductControllerPort.java
│   │   │               └── output/
│   └── resources/
│       ├── application.properties
│       └── static/
├── test/
│   ├── java/
│   │   ├── com/
│   │   │   └── dealership/
│   │   │       └── janczakcars/
│   │   │           ├── cucumber/
│   │   │           	└── config/
│   │   │           	└── runners/
│   │   │           	└── steps/
│   │   │           ├── domain/
│   └── resources/
│       └── features/
```

## **Configuração do Banco de Dados**

A aplicação utiliza o **MySQL** para persistência de dados. Certifique-se de ter o MySQL configurado corretamente e de criar o banco de dados `janczakcars`. Você pode configurar as credenciais de acesso no arquivo `application.properties`.

Exemplo de configuração no `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/janczakcars?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=senha_do_banco
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

## **Como Executar**

1. **Clone o Repositório**:

   Clone este repositório para a sua máquina local:

   ```bash
   git clone https://github.com/seu_usuario/janczakcars.git
   ```

2. **Instale as Dependências**:

   Execute o seguinte comando para instalar as dependências do projeto:

   ```bash
   mvn clean install
   ```

3. **Configuração do Banco de Dados**:

   Certifique-se de que o MySQL está em execução e que o banco de dados `janczakcars` foi criado. Caso contrário, crie-o manualmente.

4. **Execute a Aplicação**:

   Para iniciar a aplicação, execute o seguinte comando:

   ```bash
   mvn spring-boot:run
   ```

   O servidor estará disponível em `http://localhost:8080`.

## **Testes**

### **Testes Unitários**

A aplicação utiliza **JUnit 5** para testes unitários. Você pode rodar os testes unitários com o comando:

```bash
mvn test
```

### **Testes Automatizados (Cucumber)**

Os testes automatizados são baseados no **Cucumber** e seguem a abordagem de **Behavior-Driven Development (BDD)**. Os cenários de teste estão localizados na pasta `src/test/resources/features`.

Para rodar os testes do Cucumber, use o seguinte comando:

```bash
mvn test -Dcucumber.options="classpath:features"
```

## **Exemplo de Testes Cucumber**

### **Exemplo de Feature para Produto**

```gherkin
Feature: Gerenciamento de Produtos

  Scenario: Criar um novo produto
    Given um novo produto válido
    When faço uma requisição POST para "/api/product"
    Then a resposta deve ter status 200
    And deve conter um ID de produto válido

  Scenario: Buscar um produto existente pelo ID
    Given um produto com ID "550e8400-e29b-41d4-a716-446655440000" existe no sistema
    When faço uma requisição GET para "/api/product/550e8400-e29b-41d4-a716-446655440000"
    Then a resposta deve ter status 200
    And deve conter os detalhes do produto
```

### **Exemplo de Feature para Pedido**

```gherkin
Feature: Gerenciamento de Pedidos

  Scenario: Criar um novo pedido
    Given um novo pedido válido
    When faço uma requisição POST para "/api/order"
    Then a resposta deve ter status 200
    And deve conter um ID de pedido válido

  Scenario: Atualizar o status de um pedido existente
    Given um pedido com ID "123e4567-e89b-12d3-a456-426614174000" existe no sistema
    And o novo status é "2"
    When faço uma requisição PATCH para "/api/order/123e4567-e89b-12d3-a456-426614174000/status"
    Then a resposta deve ter status 200
```

## **Contribuição**

Se você deseja contribuir com este projeto, sinta-se à vontade para abrir uma **issue** ou um **pull request**.

## **Licença**

Este projeto está licenciado sob a **MIT License** - veja o arquivo [LICENSE](LICENSE) para mais detalhes.

