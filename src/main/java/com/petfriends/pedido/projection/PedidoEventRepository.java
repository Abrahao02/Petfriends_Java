package com.petfriends.pedido.projection;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoEventRepository extends JpaRepository<PedidoEventEntity, Long> {
    List<PedidoEventEntity> findByPedidoId(String pedidoId);
}
