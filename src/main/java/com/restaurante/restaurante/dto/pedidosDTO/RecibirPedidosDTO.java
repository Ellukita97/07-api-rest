package com.restaurante.restaurante.dto.pedidosDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecibirPedidosDTO {

    private Double precio;
    private Long idCliente;

    public RecibirPedidosDTO() {
    }

    public RecibirPedidosDTO(Double precio, Long idCliente) {
        this.precio = precio;
        this.idCliente = idCliente;
    }
}
