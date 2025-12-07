package com.petfriends.transporte.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Endereco {

    @Column(name = "rua")
    private String rua;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "cep")
    private String cep;

    protected Endereco() {}

    public Endereco(String rua, String cidade, String cep) {
        this.rua = rua;
        this.cidade = cidade;
        this.cep = cep;
    }

    public String getRua() { return rua; }
    public String getCidade() { return cidade; }
    public String getCep() { return cep; }
}
