# PetFriends — Módulo `pedido`

Este repositório contém o módulo `pedido` do projeto PetFriends. Aqui eu implementei um fluxo mínimo usando Axon em modo "minimal" (sem AxonServer) para aprender e demonstrar Command, Aggregate, EventSourcing e projeções persistidas com JPA.

## Resumo rápido
- Command: `CreatePedidoCommand` via `CommandGateway`.
- Aggregate: `PedidoAggregate` com `@CommandHandler` e `@EventSourcingHandler`.
- Evento: `PedidoCreatedEvent` (POJO) aplicado pelo aggregate.
- Projeção: `PedidoEventProjection` persiste JSON do evento em `pedido_events` via `PedidoEventRepository`.
- APIs: endpoints REST para criar pedido e listar eventos (`/api/pedidos` e `/api/pedidos/events`).

## Por que usei "Axon minimal"?
- Não usei AxonServer: tudo roda dentro da aplicação (event store em memória). Isso facilita executar o projeto localmente — não precisei de Docker nem serviços externos.
- A configuração principal está em `src/main/resources/application.properties` e registrei um `EventStore` em memória em `EventStoreConfig`.

## Como executar localmente

1) Build:

```powershell
mvn clean package -DskipTests
```

2) Rodar a aplicação:

```powershell
mvn spring-boot:run
```

3) Swagger UI (documentação das APIs):

```
http://localhost:8080/swagger-ui/index.html
```

4) H2 Console (inspecionar tabela `pedido_events`):

```
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:petdb
```

## Exemplos de uso (curl)

# PetFriends — O que aprendi e como aplicar (módulo `pedido`)

Este repositório contém o módulo `pedido` do PetFriends. Em vez de listar apenas código, aqui explico de forma prática o que implementei, o que aprendi e como você pode usar esse conhecimento em outros projetos.

## O que eu implementei (visão rápida)
- Criei a lógica mínima para criar pedidos usando o padrão CQRS/Events:
  - Um comando que cria um pedido (`CreatePedidoCommand`).
  - Um aggregate (`PedidoAggregate`) que aplica o evento `PedidoCreatedEvent`.
  - Uma projeção que grava os eventos em uma tabela (`pedido_events`) para consulta/auditoria.
- Modelei dois domínios auxiliares como se fossem microserviços separados (dentro do mesmo projeto para fins de estudo):
  - **Almoxarifado**: entidade `Reserva` + `Quantidade` (value object) + `ReservaRepository`.
  - **Transporte**: entidade `Envio` + `Endereco` (value object) + `EnvioRepository`.

## O que realmente aprendi (e como aplicar em outros projetos)
- Conceito: Event Sourcing e Domain Events permitem gravar o fato (evento) e projetar diferentes visões para consumo (read models). Em projetos reais, usar eventos melhora auditabilidade e desacoplamento entre serviços.
- Aplicação prática: quando separar responsabilidades em microserviços, modele agregados claros e eventos capazes de descrever o que aconteceu (ex.: `PedidoCreated` com os itens necessários). Em seguida, crie consumidores que atualizem seus próprios modelos (estoque, logística, etc.).
- Dica operacional: comece em modo "local" (tudo na mesma JVM) para validar o modelo de eventos. Depois, extrai consumidores para serviços separados conectados por um broker (Kafka/RabbitMQ) quando precisar escalar ou implantar separadamente.

## Arquivos principais que ajudam a entender a implementação
- `src/main/java/com/petfriends/pedido/...` (aggregate, commands, events, controllers, projeção)
- `src/main/java/com/petfriends/almoxarifado/entity/Reserva.java` e `Quantidade.java`
- `src/main/java/com/petfriends/almoxarifado/repository/ReservaRepository.java`
- `src/main/java/com/petfriends/transporte/entity/Envio.java` e `Endereco.java`
- `src/main/java/com/petfriends/transporte/repository/EnvioRepository.java`
- `EXERCICIOS_RESPONSES.md` — respostas às perguntas conceituais do exercício

## Rodando localmente (passos rápidos)
1) Build:

```powershell
.
cd C:\Users\Eduardo\Desktop\petfriends-pedido\Petfriends_Java
.\mvnw.cmd package
```

2) Rodar a aplicação:

```powershell
.\mvnw.cmd spring-boot:run
```

3) Criar um pedido (PowerShell):

```powershell
$body = @{ tutorId = 1; petId = 2 } | ConvertTo-Json
Invoke-RestMethod -Uri http://localhost:8080/api/pedidos -Method Post -Body $body -ContentType 'application/json'
```

4) Inspecionar eventos e tabelas (H2 Console):

```
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:petdb
```

## Como aplicar isso em um projeto real
- Primeiro, valide o modelo de eventos localmente (como fiz aqui). Depois:
  - Extraia consumidores para serviços separados que leiam eventos de um broker (Kafka é uma boa escolha para produção).
  - Defina contratos de evento (schema, versão, metadados como `correlationId`).
  - Instrumente observabilidade (tracing, logs centralizados) antes de rodar em produção.

## Observações finais
- Os códigos e modelos aqui são didáticos: o objetivo foi aprender a modelagem DDD/Events e demonstrar como um pedido pode disparar ações em domínios distintos (estoque e transporte).

