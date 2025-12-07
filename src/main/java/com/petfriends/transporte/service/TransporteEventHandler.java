package com.petfriends.transporte.service;

import com.petfriends.transporte.entity.Endereco;
import com.petfriends.transporte.entity.Envio;
import com.petfriends.transporte.repository.EnvioRepository;
import com.petfriends.pedido.events.PedidoCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransporteEventHandler {

    private final EnvioRepository envioRepository;
    private final Logger logger = LoggerFactory.getLogger(TransporteEventHandler.class);

    public TransporteEventHandler(EnvioRepository envioRepository) {
        this.envioRepository = envioRepository;
    }

    @EventHandler
    public void handlePedidoCreated(PedidoCreatedEvent event) {
        logger.info("Transporte: recebido PedidoCreatedEvent pedidoId={}", event.getPedidoId());

        // Exemplo: cria um envio com trackingNumber gerado e endereço dummy
        Endereco endereco = new Endereco("Rua Exemplo, 123", "Cidade", "00000-000");
        String tracking = UUID.randomUUID().toString();

        Envio envio = new Envio(event.getPedidoId(), tracking, "CRIADO", endereco);
        envioRepository.save(envio);

        logger.info("Envio criado (id={}, tracking={}) para pedidoId={}", envio.getId(), envio.getTrackingNumber(), event.getPedidoId());
    }
}
