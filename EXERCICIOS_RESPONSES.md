# Respostas - Exercícios PetFriends (resumo)

Parte: Modelagem e Implementação

- **Almoxarifado - Entidade:** `Reserva` (em `com.petfriends.almoxarifado.entity`). Representa reserva de estoque associada a um `pedidoId` e `petId`.
- **Almoxarifado - Value Object:** `Quantidade` (embeddable) que encapsula e valida a quantidade reservada.
- **Transporte - Entidade:** `Envio` (em `com.petfriends.transporte.entity`) que armazena `pedidoId`, `trackingNumber`, `status` e um `Endereco` embeddable.
- **Transporte - Value Object:** `Endereco` (embeddable) com `rua`, `cidade`, `cep`.

Parte: Domain Events (respostas sucintas)

- **Funcionalidade síncrona afetada:** a funcionalidade de checkout/pedido do frontend (`PetFriends_Web`) é afetada — o cliente espera confirmação de reserva/estoque e confirmação de envio (rastreamento). Com eventos, a confirmação imediata pode ser eventual (assíncrona), logo a experiência de checkout pode mudar (mostrar status pendente, por exemplo).

- **Enviar somente ID vs payload completo:**
  - Enviar só o ID é leve e desacopla serviços: consumidores precisam buscar dados adicionais (consistency trade-off). Bom quando consumidores já podem consultar um serviço de leitura ou quando segurança/privacidade impedem envio de dados completos.
  - Enviar payload completo reduz latência e evita chamadas síncronas adicionais (mais resiliente em cenários offline), porém aumenta acoplamento e tamanho da mensagem, pode duplicar dados e causar divergência de versão.

- **Como projetar evento do Pedido → Almoxarifado:**
  - Se o Almoxarifado precisa apenas reservar estoque por produto, envie um evento `PedidoCreated` contendo os itens do pedido (produtoId, quantidade), ou um `PedidoReservarEstoque` com payload:
    {
      pedidoId, tutorId, itens: [{produtoId, quantidade}], enderecoEntrega?
    }
  - Inclua metadados (timestamp, correlação, versão do evento).

- **Como projetar evento do Pedido → Transporte:**
  - Enviar `PedidoCreated` com dados necessários para criar envio: `pedidoId`, `tutorId`, `enderecoEntrega` (ou id do endereço acompanhado de payload se necessário), dimensões/peso se aplicável. Preferir enviar os dados imprescindíveis para iniciar roteirização.

Parte: Comunicação assíncrona (implementação)

- Na pasta foram adicionados handlers de exemplo (`@EventListener`) que escutam `PedidoCreatedEvent` (classe existente em `com.petfriends.pedido.events`). Em arquiteturas reais, prefira usar um broker (Kafka, RabbitMQ) ou um mecanismo de mensagens (Spring Cloud Stream) com contratos bem definidos.

Parte: Observabilidade e Infraestrutura — respostas curtas

- **Gateway de Serviço:** componente que expõe APIs consolidadas para consumidores e encaminha chamadas para microserviços. Vantagens: roteamento, autenticação centralizada, Caching, rate-limiting, adaptação. Desvantagens: ponto adicional de falha/latência e possível acoplamento se regras de negócio forem colocadas no gateway.

- **ID de Correlação:** identificador único que acompanha uma requisição através de serviços distribuídos para rastreamento. Pré-requisitos: propagar o ID em todos os canais (HTTP headers, mensagens), infraestrutura de logging/trace compatível, convênio entre serviços para preservação do header.

- **Spring Cloud Sleuth e Zipkin:** Sleuth instrumenta logs e gera trace/span IDs distribuídos; Zipkin coleta e visualiza traces gerados. Sleuth injeta IDs em logs e envia spans para um collector (Zipkin) para visualização.

- **Agregador de Logs (ELK):** centraliza logs (Logstash/Beats → Elasticsearch → Kibana). Vantagens: pesquisa centralizada, correlação, dashboards. Desvantagens: custo operacional, necessidade de gerenciar ingestão/retenção e potencial vazamento de dados sensíveis se não configurado corretamente.

---

Se quiser, posso:
- Rodar `mvn -q test` ou `mvn -q package` para validar build localmente.
- Transformar os handlers `@EventListener` em listeners baseados em Kafka/Spring Cloud Stream (eu sugiro Kafka se for necessário em produção).
- Gerar um README mais detalhado com instruções de execução e comandos para testes.
