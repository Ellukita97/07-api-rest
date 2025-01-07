package com.restaurante.utils.strategy;

import com.restaurante.models.Plato;

import java.util.List;

public interface PrecioEstrategia {
    Double calcularPrecio(List<Plato> platos);
}
