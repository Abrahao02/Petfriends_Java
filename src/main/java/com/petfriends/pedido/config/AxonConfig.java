package com.petfriends.pedido.config;

import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    public AxonConfig(EventProcessingConfigurer configurer) {
        configurer.usingSubscribingEventProcessors();
    }
}
