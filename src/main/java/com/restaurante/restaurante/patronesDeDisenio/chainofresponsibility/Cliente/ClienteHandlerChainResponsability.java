package com.restaurante.restaurante.patronesDeDisenio.chainofresponsibility.Cliente;

import com.restaurante.restaurante.models.Cliente;

public abstract class ClienteHandlerChainResponsability {
    protected ClienteHandlerChainResponsability nextHandler;

    public void setNextHandler(ClienteHandlerChainResponsability nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract void handleRequest(Cliente cliente);
}
