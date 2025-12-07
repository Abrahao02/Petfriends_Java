package com.petfriends.transporte.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "envios")
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pedidoId;

    private String trackingNumber;

    private String status;

    @Embedded
    private Endereco endereco;

    private Instant createdAt;

    protected Envio() {}

    public Envio(String pedidoId, String trackingNumber, String status, Endereco endereco) {
        this.pedidoId = pedidoId;
        this.trackingNumber = trackingNumber;
        this.status = status;
        this.endereco = endereco;
        this.createdAt = Instant.now();
    }

    public Long getId() { return id; }
    public String getPedidoId() { return pedidoId; }
    public String getTrackingNumber() { return trackingNumber; }
    public String getStatus() { return status; }
    public Endereco getEndereco() { return endereco; }
}
