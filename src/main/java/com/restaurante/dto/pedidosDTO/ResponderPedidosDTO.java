package com.restaurante.dto.pedidosDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponderPedidosDTO {

        private Long id;
        private Double precio;
        private Long idCliente;

        public ResponderPedidosDTO() {
        }

        public ResponderPedidosDTO(Long id, Double precio, Long idCliente) {
                this.id = id;
                this.precio = precio;
                this.idCliente = idCliente;
        }
}