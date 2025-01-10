package com.restaurante.restaurante.ulils.converters;

import com.restaurante.restaurante.dto.pedidosDTO.RecibirPedidosDTO;
import com.restaurante.restaurante.dto.pedidosDTO.ResponderPedidosDTO;
import com.restaurante.restaurante.models.Pedido;

public class PedidoDTOConvertidor {
    public static ResponderPedidosDTO convertirDTO(Pedido pedido) {
        ResponderPedidosDTO dto = new ResponderPedidosDTO();

        dto.setId(pedido.getId());
        dto.setPrecio(pedido.getPrecio());
        dto.setIdCliente(pedido.getCliente().getId());
        return dto;
    }


    public static Pedido convertirAEntidad(RecibirPedidosDTO dto) {
        Pedido pedido = new Pedido();

        pedido.setPrecio(dto.getPrecio());
        return pedido;
    }
}
