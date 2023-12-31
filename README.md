# CLIENTES-API

Projeto Teste para candidatar a uma vaga na AG CAPITAL

API simples para gerenciamento de Clientes

# Ambiente Para Desenvolvimento

## Pré requisito

- [IntelliJ](https://www.jetbrains.com/idea/download/)
- Maven 3.6+
- Java 17
- Docker 18+
- docker-compose

### Subindo API localmente para desenvolvimento

Ao clonar o Projeto e fazer o build para baixar as dependências do maven, basta seguir os pré-requisitos abaixo para
execução da API

Existem dois pré requisitos para execução da API:

- Servidor Banco de dados
    - Caso for executar API pela IDE, se faz necessário subir o banco via docker antes (a porta 1433 do host deve estar
      liberada)
        - Para subir o Servidor de Banco de Dados via docker basta estar na raiz do projeto, e executar o comando abaixo
        - ```docker-compose up -d postgres pgadmin```
        - Obs: Será preciso criar as bases de dados manualmente no pgadmin, pois o mesmo não está configurado para
          criar automaticamente
            - Nome da base de dados para customer-api: customer
            - Nome da base de dados para project-api: project
            - Link para acesso ao pgadmin: [pgadmin](http://localhost:5050/browser/) (senha: "password")
- Zipkin (monitoramento e tracing)
    - Zipkin é uma ferramenta poderosa para monitorar e solucionar problemas em aplicativos distribuídos,
      permitindo que os desenvolvedores acompanhem o fluxo das solicitações e identifiquem possíveis problemas
      de desempenho em ambientes complexos
        - Para subir o Zipkin via docker basta estar na raiz do projeto, e executar o comando abaixo
        - ```docker-compose up -d zipkin```
- Service Discovery (Eureka) e Gateway
    - Importante salientar que para a comunicação das APIs, foi utilizado o Service Discovery (Eureka) e Gateway,
      para que as APIs se comuniquem entre si, e o Gateway para que as APIs se comuniquem com o mundo externo
        - Para subir o Service Discovery (Eureka) e Gateway via docker basta estar na raiz do projeto, e executar o
          comando abaixo
        - ```docker-compose up -d discovery gateway-api```

### Subindo API localmente para desenvolvimento via docker-compose

É possível subir as APIs sem precisar clonar os repositorios, basta executar o comando abaixo na raiz do projeto

```docker-compose up -d```

### Links para acesso aos componentes (localmente)

- APIs
    - [API - Customer-api](http://localhost:6060/swagger-ui.html)
    - [API - Customer-api Health Check](http://localhost:6060/actuator/health)
    - [API - Project-api](http://localhost:6070/swagger-ui/index.html)
    - [API - Project-api Health Check](http://localhost:6070/actuator/health)

- Outros Componentes
    - [Eureka](http://localhost:8761/)
    - [Zipkin](http://localhost:9411/zipkin/)
    - [Postgres](http://localhost:5050/browser/) (senha: "password")

## Principais Tecnologias Utilizadas na API

- Java 17
- Spring Boot 2.7
- Spring Data
- Spring Actuator
- Spring Validation
- Lombok
- Swagger v3
- Postgres
- Slueth (para tracing)
- Zipkin (para monitoramento e tracing)
- Docker
- Service Discovery (Eureka)
- Gateway

## Principais Regras Utilizadas

#### Cadastro de Clientes

- Utilizado apenas 2 propriedades para cadastro de clientes (nome e e-mail), entretanto todos são
  obrigatórios
- Campo e-mail, foi utilizado com único
- Ao incluir um Cliente o sistema registra data e hora de inclusão, e seta o mesmo como ativo

#### Alteração de Clientes

- Sistema valida apenas o e-mail ao efetuar alteração, onde não deve ser alterado para um e-mail já cadastrado
- Ao alterar, o sistema registar data e hora da alteração do Cliente

#### Listar Clientes

- Implementado filtro dinâmico para listagem de Clientes Paginados por parametrização
- Campos utilizados no filtro: nome e e-mail, onde serão ordenados por data de inclusão
- Os parâmetros podem ser combinados para uma filtragem mais exata
- Caso não for informado nenhum filtro, será retornado todos os Clientes

#### Outros

- A API é independente da PROJECT-API

## Testando API

A principal documentação da API foi exportada via Swagger (links acima), e é a maneira mais simples (e eficiente) para
efetuar testes

A segunda opção para fácil utilização da API, é através do Postman, onde pode ser importado por
este [arquivo](https://github.com/dpaula/customer-api/blob/main/Projeto%20AG%20Capital.postman_collection.json)

## Acessando API Externamente

- O deploy da última versão (1.0.0) API foi realizada na plataforma do Google Cloud e pode ser acessada nos
  endereços
    - APIs
        - [API - Customer-api](http://34.95.169.37:6060/swagger-ui.html)
        - [API - Customer-api Health Check](http://34.95.169.37:6060/actuator/health)
        - [API - Project-api](http://34.95.169.37:6070/swagger-ui/index.html)
        - [API - Project-api Health Check](http://34.95.169.37:6070/actuator/health)

- Outros Componentes
    - [Eureka](http://34.95.169.37:8761/)
    - [Zipkin](http://34.95.169.37:9411/zipkin/)
    - [Postgres](http://34.95.169.37:5050/browser/) (senha: "password")

- API estará disponível até 20/09/2023, caso esteja indisponível ou expirou a data, basta solicitar para ser
  disponibilizada novamente


