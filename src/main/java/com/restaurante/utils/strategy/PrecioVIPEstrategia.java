package com.restaurante.utils.strategy;

import com.restaurante.models.Plato;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrecioVIPEstrategia implements PrecioEstrategia {
    private static final Double DESCUENTO_VIP = 0.10; // 10% de descuento

    @Override
    public Double calcularPrecio(List<Plato> platos) {
        double precioTotal = platos.stream()
                .mapToDouble(Plato::getPrecio)
                .sum();
        return precioTotal - (precioTotal * DESCUENTO_VIP);
    }

}
