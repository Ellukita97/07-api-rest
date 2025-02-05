package com.restaurante.restaurante.patronesDeDisenio.chainofresponsibility.Plato;

import com.restaurante.restaurante.models.Plato;

public abstract class PlatoHandlerChainResponsability {
    protected PlatoHandlerChainResponsability nextHandler;

    public void setNextHandler(PlatoHandlerChainResponsability nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract void handleRequest(Plato plato);
}
