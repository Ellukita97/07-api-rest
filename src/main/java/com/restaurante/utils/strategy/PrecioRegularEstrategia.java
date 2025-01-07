package com.restaurante.utils.strategy;

import com.restaurante.models.Plato;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrecioRegularEstrategia implements PrecioEstrategia {
    @Override
    public Double calcularPrecio(List<Plato> platos) {
        return platos.stream()
                .mapToDouble(Plato::getPrecio)
                .sum();
    }
}