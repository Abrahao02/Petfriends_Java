package com.petfriends.pedido.events;

public class PedidoCreatedEvent {
    private final String pedidoId;
    private final Long tutorId;
    private final Long petId;

    public PedidoCreatedEvent(String pedidoId, Long tutorId, Long petId) {
        this.pedidoId = pedidoId;
        this.tutorId = tutorId;
        this.petId = petId;
    }

    public String getPedidoId() { return pedidoId; }
    public Long getTutorId() { return tutorId; }
    public Long getPetId() { return petId; }
}
