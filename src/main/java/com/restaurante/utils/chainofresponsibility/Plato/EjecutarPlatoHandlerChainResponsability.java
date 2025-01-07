package com.restaurante.utils.chainofresponsibility.Plato;

import com.restaurante.models.Plato;
import com.restaurante.services.PedidoServices;
import com.restaurante.services.PlatoServices;

public class EjecutarPlatoHandlerChainResponsability {

    private final ManejadorConcreto1 manejadorConcreto1;

    public EjecutarPlatoHandlerChainResponsability(PedidoServices servicesPedido, PlatoServices platoServices){
        manejadorConcreto1 = new ManejadorConcreto1(servicesPedido, platoServices);
    }

    public void Ejecutar(Plato plato){
        manejadorConcreto1.handleRequest(plato);
    }
}
