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

- Criar pedido (retorna `pedidoId`):

```bash
curl -X POST "http://localhost:8080/api/pedidos" \
  -H "Content-Type: application/json" \
  -d '{"tutorId":1,"petId":2}'
```

- Listar eventos:

```bash
curl -X GET "http://localhost:8080/api/pedidos/events"
```

- Obter evento por id:

```bash
curl -X GET "http://localhost:8080/api/pedidos/events/1"
```

## Arquivos principais implementados

- `src/main/java/com/petfriends/pedido/commands/CreatePedidoCommand.java`
- `src/main/java/com/petfriends/pedido/events/PedidoCreatedEvent.java`
- `src/main/java/com/petfriends/pedido/aggregate/PedidoAggregate.java`
- `src/main/java/com/petfriends/pedido/service/PedidoCommandService.java`
- `src/main/java/com/petfriends/pedido/projection/PedidoEventProjection.java`
- `src/main/java/com/petfriends/pedido/projection/PedidoEventEntity.java`
- `src/main/java/com/petfriends/pedido/projection/PedidoEventRepository.java`
- `src/main/java/com/petfriends/pedido/service/PedidoQueryService.java`
- `src/main/java/com/petfriends/pedido/api/PedidoCommandController.java`
- `src/main/java/com/petfriends/pedido/api/PedidoQueryController.java`
- `src/main/java/com/petfriends/pedido/config/EventStoreConfig.java`
- `src/main/resources/application.properties`

## O que eu aprendi

- Axon pode rodar em modo minimal (event store em memória), o que facilita o desenvolvimento e entrega de trabalhos.
- `CommandGateway` facilita o envio de comandos de forma assíncrona.
- `@Aggregate`, `@CommandHandler` e `@EventSourcingHandler` são os pontos centrais do fluxo de Event Sourcing; o aggregate aplica eventos com `AggregateLifecycle.apply()`.
- Projeções (EventHandlers) permitem persistir uma representação dos eventos em uma tabela JPA separada para consultas e auditoria.
- Evitei serializar `Instant` nos eventos para não ter problemas com o `ObjectMapper`.
- Foi necessário registrar um `EventStorageEngine` em memória e configurar processadores em modo subscribing via `AxonConfig`.
- Adicionei `hibernate-validator` para evitar warnings/erros de inicialização do Spring.

