package com.petfriends.almoxarifado.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Quantidade {

    @Column(name = "quantidade_valor")
    private int valor;

    protected Quantidade() {}

    public Quantidade(int valor) {
        if (valor < 0) throw new IllegalArgumentException("Quantidade não pode ser negativa");
        this.valor = valor;
    }

    public int getValor() { return valor; }
}
