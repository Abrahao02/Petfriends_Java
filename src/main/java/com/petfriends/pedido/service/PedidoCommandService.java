package com.petfriends.pedido.service;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import com.petfriends.pedido.comands.CreatePedidoCommand;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class PedidoCommandService {

    private final CommandGateway gateway;

    public PedidoCommandService(CommandGateway gateway) {
        this.gateway = gateway;
    }

    public CompletableFuture<String> create(Long tutorId, Long petId) {
        String pedidoId = UUID.randomUUID().toString();
        return gateway.send(new CreatePedidoCommand(pedidoId, tutorId, petId))
                .thenApply(r -> pedidoId);
    }
}

