package com.petfriends.pedido.aggregate;

import com.petfriends.pedido.comands.CreatePedidoCommand;
import com.petfriends.pedido.events.PedidoCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class PedidoAggregate {

    @AggregateIdentifier
    private String pedidoId;

    public PedidoAggregate() { }

    @CommandHandler
    public PedidoAggregate(CreatePedidoCommand cmd) {
        AggregateLifecycle.apply(new PedidoCreatedEvent(cmd.getPedidoId(), cmd.getTutorId(), cmd.getPetId()));
    }

    @EventSourcingHandler
    public void on(PedidoCreatedEvent evt) {
        this.pedidoId = evt.getPedidoId();
    }
}
