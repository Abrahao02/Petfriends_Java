package com.petfriends.almoxarifado.service;

import com.petfriends.almoxarifado.entity.Quantidade;
import com.petfriends.almoxarifado.entity.Reserva;
import com.petfriends.almoxarifado.repository.ReservaRepository;
import com.petfriends.pedido.events.PedidoCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

@Service
public class AlmoxarifadoEventHandler {

    private final ReservaRepository reservaRepository;
    private final Logger logger = LoggerFactory.getLogger(AlmoxarifadoEventHandler.class);

    public AlmoxarifadoEventHandler(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @EventHandler
    public void handlePedidoCreated(PedidoCreatedEvent event) {
        logger.info("Recebido PedidoCreatedEvent para pedidoId={}", event.getPedidoId());

        // Exemplo simples: cria uma reserva de quantidade 1 para o pet
        Reserva reserva = new Reserva(event.getPedidoId(), event.getPetId(), new Quantidade(1), "RESERVADO");
        reservaRepository.save(reserva);

        logger.info("Reserva criada (id={}) para pedidoId={}", reserva.getId(), event.getPedidoId());
    }
}
