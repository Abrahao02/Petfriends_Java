package com.petfriends.pedido.projection;

import jakarta.persistence.*;

@Entity
@Table(name = "pedido_events")
public class PedidoEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pedidoId;

    private String eventType;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private String payload;

    public PedidoEventEntity() {}

    public PedidoEventEntity(String pedidoId, String eventType, String payload) {
        this.pedidoId = pedidoId;
        this.eventType = eventType;
        this.payload = payload;
    }

    public Long getId() { return id; }
    public String getPedidoId() { return pedidoId; }
    public String getEventType() { return eventType; }
    public String getPayload() { return payload; }
}
