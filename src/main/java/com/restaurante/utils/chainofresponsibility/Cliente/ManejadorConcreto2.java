package com.restaurante.utils.chainofresponsibility.Cliente;

import com.restaurante.constantes.TipoCliente;
import com.restaurante.models.Cliente;
import com.restaurante.services.PedidoServices;
import org.springframework.beans.factory.annotation.Autowired;

public class ManejadorConcreto2 extends ClienteHandlerChainResponsability {


    private final PedidoServices servicesPedido;

    @Autowired
    public ManejadorConcreto2(PedidoServices servicesPedido) {
        this.servicesPedido = servicesPedido;
    }

    @Override
    public void handleRequest(Cliente cliente) {
        if (cliente.getTipoCliente() == TipoCliente.FRECUENTE) {
            servicesPedido.aplicarDescuentoATodosLosPedidosDeCliente(cliente.getId());
        } else if (nextHandler != null) {
            nextHandler.handleRequest(cliente);
        }
    }
}
