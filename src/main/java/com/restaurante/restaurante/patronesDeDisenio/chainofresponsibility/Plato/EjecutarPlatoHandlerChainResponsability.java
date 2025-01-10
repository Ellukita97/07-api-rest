package com.restaurante.restaurante.patronesDeDisenio.chainofresponsibility.Plato;

import com.restaurante.restaurante.models.Plato;
import com.restaurante.restaurante.services.PedidoServices;
import com.restaurante.restaurante.services.PlatoServices;

public class EjecutarPlatoHandlerChainResponsability {

    private final ManejadorConcreto1 manejadorConcreto1;

    public EjecutarPlatoHandlerChainResponsability(PedidoServices servicesPedido, PlatoServices platoServices){
        manejadorConcreto1 = new ManejadorConcreto1(servicesPedido, platoServices);
    }

    public void Ejecutar(Plato plato){
        manejadorConcreto1.handleRequest(plato);
    }
}
