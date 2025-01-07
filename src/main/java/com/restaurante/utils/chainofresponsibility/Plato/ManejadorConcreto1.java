package com.restaurante.utils.chainofresponsibility.Plato;

import com.restaurante.constantes.TipoPlato;
import com.restaurante.models.Plato;
import com.restaurante.services.PedidoServices;
import com.restaurante.services.PlatoServices;
import org.springframework.beans.factory.annotation.Autowired;

public class ManejadorConcreto1 extends PlatoHandlerChainResponsability {


    private final PedidoServices servicesPedido;
    private final PlatoServices platoServices;

    @Autowired
    public ManejadorConcreto1(PedidoServices servicesPedido, PlatoServices platoServices) {
        this.servicesPedido = servicesPedido;
        this.platoServices = platoServices;
    }

    @Override
    public void handleRequest(Plato plato) {
        if (servicesPedido.verificarPlatoMasDe100Veces(plato.getId())) {
            plato.setTipoPlato(TipoPlato.POPULAR);
            plato.setPrecio(plato.getPrecio() * (1 + 5.73 / 100));
            platoServices.actualizarPlato(plato.getId(), plato);
        } else if (nextHandler != null) {
            nextHandler.handleRequest(plato);
        }
    }
}
