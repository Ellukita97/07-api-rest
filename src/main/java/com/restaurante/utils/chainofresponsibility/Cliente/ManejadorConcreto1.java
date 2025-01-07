package com.restaurante.utils.chainofresponsibility.Cliente;

import com.restaurante.constantes.TipoCliente;
import com.restaurante.models.Cliente;
import com.restaurante.services.ClienteServices;
import com.restaurante.services.PedidoServices;
import com.restaurante.utils.Observer.EjecutarObserver;
import org.springframework.beans.factory.annotation.Autowired;

public class ManejadorConcreto1 extends ClienteHandlerChainResponsability {


    private final PedidoServices servicesPedido;
    private final ClienteServices clienteServices;
    private final Long CANTIDAD_PEDIDOS_REALIZADOS_CLIENTE = 3l; //Es 10 por la letra pero fue cambiado por testing

    @Autowired
    public ManejadorConcreto1(PedidoServices servicesPedido, ClienteServices clienteServices) {
        this.servicesPedido = servicesPedido;
        this.clienteServices = clienteServices;
    }

    @Override
    public void handleRequest(Cliente cliente) {
        if (servicesPedido.contarPedidosPorCliente(cliente.getId()) >= CANTIDAD_PEDIDOS_REALIZADOS_CLIENTE) {
            cliente.setTipoCliente(TipoCliente.FRECUENTE);
            EjecutarObserver.clienteSubject.setState("Tipo de cliente cambiado");
            clienteServices.actualizarCliente(cliente.getId(), cliente);
        } else if (nextHandler != null) {
            nextHandler.handleRequest(cliente);
        }
    }
}
