package com.petfriends.pedido.service;

import com.petfriends.pedido.projection.PedidoEventEntity;
import com.petfriends.pedido.projection.PedidoEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoQueryService {

    private final PedidoEventRepository repo;

    public PedidoQueryService(PedidoEventRepository repo) {
        this.repo = repo;
    }

    public List<PedidoEventEntity> getAllEvents() {
        return repo.findAll();
    }

    public Optional<PedidoEventEntity> getEventById(Long id) {
        return repo.findById(id);
    }
}
