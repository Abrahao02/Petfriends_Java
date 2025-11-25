package com.petfriends.pedido.api;

import com.petfriends.pedido.service.PedidoCommandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoCommandController {

    private final PedidoCommandService service;

    public PedidoCommandController(PedidoCommandService service) {
        this.service = service;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<String>> createPedido(@RequestBody CreatePedidoRequest request) {
        return service.create(request.getTutorId(), request.getPetId())
                .thenApply(id -> ResponseEntity.ok(id));
    }

    public static class CreatePedidoRequest {
        private Long tutorId;
        private Long petId;

        public Long getTutorId() { return tutorId; }
        public void setTutorId(Long tutorId) { this.tutorId = tutorId; }
        public Long getPetId() { return petId; }
        public void setPetId(Long petId) { this.petId = petId; }
    }
}
