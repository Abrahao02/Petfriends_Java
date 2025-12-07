package com.petfriends.pedido.comands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CreatePedidoCommand {
    @TargetAggregateIdentifier
    private final String pedidoId;
    private final Long tutorId;
    private final Long petId;

    public CreatePedidoCommand(String pedidoId, Long tutorId, Long petId) {
        this.pedidoId = pedidoId;
        this.tutorId = tutorId;
        this.petId = petId;
    }

    public String getPedidoId() { return pedidoId; }
    public Long getTutorId() { return tutorId; }
    public Long getPetId() { return petId; }
}
