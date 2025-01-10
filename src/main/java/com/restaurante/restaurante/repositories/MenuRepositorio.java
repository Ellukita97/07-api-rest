package com.restaurante.restaurante.repositories;

import com.restaurante.restaurante.models.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepositorio extends JpaRepository<Menu, Long> {
}
