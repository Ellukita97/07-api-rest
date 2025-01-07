package com.restaurante.utils.chainofresponsibility.Cliente;

import com.restaurante.models.Cliente;
import com.restaurante.services.ClienteServices;
import com.restaurante.services.PedidoServices;

public class EjecutarClienteHandlerChainResponsability {

    private final ManejadorConcreto1 manejadorConcreto1;
    private final ManejadorConcreto2 manejadorConcreto2;

    public EjecutarClienteHandlerChainResponsability(PedidoServices servicesPedido, ClienteServices clienteServices){
        manejadorConcreto1 = new ManejadorConcreto1(servicesPedido, clienteServices);
        manejadorConcreto2 = new ManejadorConcreto2(servicesPedido);
    }

    public void Ejecutar(Cliente cliente){
        manejadorConcreto1.setNextHandler(manejadorConcreto2);
        manejadorConcreto1.handleRequest(cliente);
    }
}
