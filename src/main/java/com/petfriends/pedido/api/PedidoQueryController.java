package com.petfriends.pedido.api;

import com.petfriends.pedido.projection.PedidoEventEntity;
import com.petfriends.pedido.service.PedidoQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoQueryController {

    private final PedidoQueryService service;

    public PedidoQueryController(PedidoQueryService service) {
        this.service = service;
    }

    @GetMapping("/events")
    public ResponseEntity<List<PedidoEventEntity>> getAllEvents() {
        return ResponseEntity.ok(service.getAllEvents());
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<PedidoEventEntity> getEventById(@PathVariable Long id) {
        return service.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
