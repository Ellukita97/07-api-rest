package com.restaurante.utils.Converters;

import com.restaurante.dto.pedidosDTO.RecibirPedidosDTO;
import com.restaurante.dto.pedidosDTO.ResponderPedidosDTO;
import com.restaurante.models.Pedido;

import java.util.Optional;

public class PedidoDTOConvertidor {
    public static ResponderPedidosDTO convertirDTO(Pedido pedido) {
        ResponderPedidosDTO dto = new ResponderPedidosDTO();

        dto.setId(pedido.getId());
        dto.setPrecio(pedido.getPrecio());
        dto.setIdCliente(pedido.getCliente().getId());
        return dto;
    }

    public static ResponderPedidosDTO convertirDTO(Optional<Pedido> pedido) {
        ResponderPedidosDTO dto = new ResponderPedidosDTO();

        dto.setId(pedido.get().getId());
        dto.setPrecio(pedido.get().getPrecio());
        dto.setIdCliente(pedido.get().getCliente().getId());
        return dto;
    }

    public static Pedido convertirAEntidad(RecibirPedidosDTO dto) {
        Pedido pedido = new Pedido();

        pedido.setPrecio(dto.getPrecio());
        return pedido;
    }
}
