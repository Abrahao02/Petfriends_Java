package com.petfriends.transporte.repository;

import com.petfriends.transporte.entity.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {
}
