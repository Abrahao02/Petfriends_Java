package com.petfriends.pedido.events;

import java.time.Instant;

public abstract class BaseEvent {
    private final Instant occurredOn = Instant.now();
    public Instant getOccurredOn() { return occurredOn; }
}
