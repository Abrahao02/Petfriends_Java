package com.petfriends.pedido.projection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petfriends.pedido.events.PedidoCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class PedidoEventProjection {

    private final PedidoEventRepository repo;
    private final ObjectMapper mapper = new ObjectMapper();

    public PedidoEventProjection(PedidoEventRepository repo) {
        this.repo = repo;
    }

    @EventHandler
    public void on(PedidoCreatedEvent evt) {
        try {
            String payload = mapper.writeValueAsString(evt);
            PedidoEventEntity entity = new PedidoEventEntity(evt.getPedidoId(), "PedidoCreatedEvent", payload);
            repo.save(entity);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao persistir projeção do evento", e);
        }
    }
}
