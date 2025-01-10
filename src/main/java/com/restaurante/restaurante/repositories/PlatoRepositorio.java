package com.restaurante.restaurante.repositories;

import com.restaurante.restaurante.models.Plato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatoRepositorio extends JpaRepository<Plato, Long> {
}
