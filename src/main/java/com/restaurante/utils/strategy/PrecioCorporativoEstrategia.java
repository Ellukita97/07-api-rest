package com.restaurante.utils.strategy;

import com.restaurante.models.Plato;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrecioCorporativoEstrategia implements PrecioEstrategia {
    @Override
    public Double calcularPrecio(List<Plato> platos) {
        if (platos.size() > 5) {
            double precioTotal = platos.stream()
                    .mapToDouble(Plato::getPrecio)
                    .sum();
            return precioTotal - (precioTotal * 0.15); // 15% de descuento si hay más de 5 platos
        }
        return platos.stream()
                .mapToDouble(Plato::getPrecio)
                .sum();
    }
}
