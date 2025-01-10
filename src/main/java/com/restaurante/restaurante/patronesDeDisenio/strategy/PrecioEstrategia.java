package com.restaurante.restaurante.patronesDeDisenio.strategy;

import com.restaurante.restaurante.models.Plato;

import java.util.List;

public interface PrecioEstrategia {
    Double calcularPrecio(List<Plato> platos);
}
