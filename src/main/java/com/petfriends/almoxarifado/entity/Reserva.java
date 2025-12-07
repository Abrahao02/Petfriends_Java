package com.petfriends.almoxarifado.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "reservas_estoque")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pedidoId;

    private Long petId;

    @Embedded
    private Quantidade quantidade;

    private String status;

    private Instant createdAt;

    protected Reserva() {}

    public Reserva(String pedidoId, Long petId, Quantidade quantidade, String status) {
        this.pedidoId = pedidoId;
        this.petId = petId;
        this.quantidade = quantidade;
        this.status = status;
        this.createdAt = Instant.now();
    }

    public Long getId() { return id; }
    public String getPedidoId() { return pedidoId; }
    public Long getPetId() { return petId; }
    public Quantidade getQuantidade() { return quantidade; }
    public String getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public void setStatus(String status) { this.status = status; }
}
